package br.com.codeup.payment.service;

import br.com.codeup.payment.entity.Client;
import br.com.codeup.payment.model.MessageDTO;
import br.com.codeup.payment.model.SuccessDTO;
import br.com.codeup.payment.model.TransferDTO;
import br.com.codeup.payment.repository.ClientRepository;
import br.com.codeup.payment.util.enums.ErrorMessageEnum;
import br.com.codeup.payment.util.enums.UserTypeEnum;
import br.com.codeup.payment.util.exception.ValidationErrorException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

@Service
@Validated
public class TransferService {

    private final ClientRepository clientRepository;

    @Autowired
    public TransferService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public SuccessDTO transfer(@Valid TransferDTO request) {

        // Recupera quam vai fazer a transferencia
        Client payer = this.searchCustomer(request.getPayer());

        // Recupera quam vai receber a transferencia
        Client payee = this.searchCustomer(request.getPayee());

        if (!UserTypeEnum.COMMON.getCode().equalsIgnoreCase(payer.getClientType())) {
            throw new ValidationErrorException(ErrorMessageEnum.CANNOT_MAKE_TRANSFER);
        }

        if (request.getValue().compareTo(payer.getBalance()) > 0) {
            throw new ValidationErrorException(ErrorMessageEnum.INSUFFICIENT_BALANCE);
        }

        // Altera o saldo do pagador
        payer.setBalance(payer.getBalance().subtract(request.getValue()));
        this.clientRepository.save(payer);

        // Altera o saldo do beneficiário
        payee.setBalance(payee.getBalance().add(request.getValue()));
        this.clientRepository.save(payee);

        // Chama API do autorizador
        this.callAuthorizerAPI();

        // Envia a notificação
        this.sendNotification();

        return SuccessDTO.builder()
                .message("Transferência realizada com sucesso.")
                .build();
    }

    private Client searchCustomer(Long id) {
        return this.clientRepository.findById(id)
                .orElseThrow(() -> new ValidationErrorException(ErrorMessageEnum.UNREGISTERED_CUSTOMER));
    }

    private void callAuthorizerAPI() {

        String url = "https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc";
        RestTemplate restTemplate = new RestTemplate();
        MessageDTO messageDTO = restTemplate.getForObject(url, MessageDTO.class);

        if (ObjectUtils.isEmpty(messageDTO) || !"Autorizado".equalsIgnoreCase(messageDTO.getMessage())) {
            throw new ValidationErrorException(ErrorMessageEnum.AUTHORIZED_ERROR);
        }

    }

    private void sendNotification() {

        String url = "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6";
        RestTemplate restTemplate = new RestTemplate();
        MessageDTO messageDTO = restTemplate.getForObject(url, MessageDTO.class);

        if (ObjectUtils.isEmpty(messageDTO) || !"true".equalsIgnoreCase(messageDTO.getMessage())) {
            throw new ValidationErrorException(ErrorMessageEnum.NOTIFICATION_ERROR);
        }

    }

}