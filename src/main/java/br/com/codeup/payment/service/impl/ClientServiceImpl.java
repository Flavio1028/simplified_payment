package br.com.codeup.payment.service.impl;

import br.com.codeup.payment.entity.Client;
import br.com.codeup.payment.model.ClientDTO;
import br.com.codeup.payment.model.ClientSaveDTO;
import br.com.codeup.payment.model.SuccessDTO;
import br.com.codeup.payment.repository.ClientRepository;
import br.com.codeup.payment.service.ClientService;
import br.com.codeup.payment.util.enums.ErrorMessageEnum;
import br.com.codeup.payment.util.enums.UserTypeEnum;
import br.com.codeup.payment.util.exception.ValidationErrorException;
import br.com.codeup.payment.util.utils.FormatterUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;

@Service
@Validated
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SuccessDTO save(@Valid ClientSaveDTO request) {

        // Valida o tipo de documento informado
        String documentType = this.validateDocument(request.getDocumentNumber());
        // Valida o tipo de usuário informado
        this.validateClientType(request.getClientType());

        // Cria o cliente
        Client client = this.modelMapper.map(request, Client.class);
        client.setDocumentType(documentType);
        client.setPassword(FormatterUtil.generatePassword(request.getPassword()));

        client = this.clientRepository.save(client);

        return SuccessDTO.builder()
                .message("Cliente cadastrado com sucesso.")
                .data(this.modelMapper.map(client, ClientDTO.class))
                .build();
    }

    @Override
    public Page<ClientDTO> getAll(Pageable page) {
        Page<Client> clientEntity = this.clientRepository.findAll(page);
        return clientEntity.map(value -> this.modelMapper.map(value, ClientDTO.class));
    }

    @Override
    public ClientDTO getById(Long id) {
        return this.clientRepository.findById(id)
                .map(value -> this.modelMapper.map(value, ClientDTO.class))
                .orElseThrow(() -> new ValidationErrorException(ErrorMessageEnum.UNREGISTERED_CUSTOMER));
    }

    @Override
    public SuccessDTO update(Long id, ClientSaveDTO request) {

        // Valida se o cliente existe
        Client clientRecovery = this.clientRepository.findById(id)
                .orElseThrow(() -> new ValidationErrorException(ErrorMessageEnum.UNREGISTERED_CUSTOMER));

        if (request.getDocumentNumber() != null) {
            // Valida o tipo de documento informado
            String documentType = this.validateDocument(request.getDocumentNumber());

            clientRecovery.setDocumentType(documentType);
            clientRecovery.setDocumentNumber(request.getDocumentNumber());
        }

        if (StringUtils.isNotBlank(request.getPassword())) {
            clientRecovery.setPassword(FormatterUtil.generatePassword(request.getPassword()));
        }

        if (request.getBalance() != null) {
            clientRecovery.setBalance(request.getBalance());
        }

        if (StringUtils.isNotBlank(request.getClientType())) {
            // Valida o tipo de usuário informado
            this.validateClientType(request.getClientType());

            clientRecovery.setClientType(request.getClientType());
        }

        clientRecovery = this.clientRepository.save(clientRecovery);

        return SuccessDTO.builder()
                .message("Cliente atualizado com sucesso.")
                .data(this.modelMapper.map(clientRecovery, ClientDTO.class))
                .build();
    }

    @Override
    public SuccessDTO delete(Long id) {

        // Valida se o cliente existe
        Client clientRecovery = this.clientRepository.findById(id)
                .orElseThrow(() -> new ValidationErrorException(ErrorMessageEnum.UNREGISTERED_CUSTOMER));

        this.clientRepository.deleteById(clientRecovery.getId());

        return SuccessDTO.builder()
                .message("Cliente detelado com sucesso.")
                .build();
    }

    private String validateDocument(String documentNumber) {

        if (documentNumber.length() != 11 && documentNumber.length() != 14) {
            throw new ValidationErrorException(ErrorMessageEnum.INVALID_DOCUMENT);
        }

        return documentNumber.length() == 11 ? "CPF" : "CNPJ";
    }

    private void validateClientType(String clientType) {
        List<UserTypeEnum> userType = Arrays.stream(UserTypeEnum.values()).filter(value -> value.getCode().equalsIgnoreCase(clientType)).toList();
        if (userType.isEmpty()) {
            throw new ValidationErrorException(ErrorMessageEnum.USER_TYPE_ERROR);
        }
    }

}