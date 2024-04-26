package br.com.codeup.payment.service.impl;

import br.com.codeup.payment.entity.Client;
import br.com.codeup.payment.model.ClientDTO;
import br.com.codeup.payment.model.ClientSaveDTO;
import br.com.codeup.payment.model.SuccessDTO;
import br.com.codeup.payment.repository.ClientRepository;
import br.com.codeup.payment.util.enums.ErrorMessageEnum;
import br.com.codeup.payment.util.exception.ValidationErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    @Spy
    private ModelMapper modelMapper;

    private static ClientSaveDTO client;

    private static ClientSaveDTO store;

    @BeforeEach
    void setUp() {

        client = ClientSaveDTO.builder()
                .name("Test")
                .email("test@test.com")
                .documentNumber("12345678909")
                .password("ABC123")
                .balance(BigDecimal.valueOf(100L))
                .clientType("C")
                .build();

        store = ClientSaveDTO.builder()
                .name("Store")
                .email("store@test.com")
                .documentNumber("00000000000191")
                .password("ABC123")
                .balance(BigDecimal.valueOf(25000L))
                .clientType("L")
                .build();

    }

    private static Stream<Arguments> storeArguments() {
        return Stream.of(
                Arguments.of(store),
                Arguments.of(new ClientDTO())
        );
    }

    @Test
    void deleteSuccessTest() {

        Client clientEntity = modelMapper.map(client, Client.class);
        clientEntity.setId(1L);

        // Mock
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientEntity));
        doNothing().when(clientRepository).deleteById(anyLong());

        SuccessDTO successDTO = this.clientService.delete(1L);

        assertEquals("Cliente detelado com sucesso.", successDTO.getMessage());
    }

    @Test
    void deleteClientNotFoundExceptionTest() {

        // Mock
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        ValidationErrorException exception = assertThrows(ValidationErrorException.class,
                () -> this.clientService.delete(100L));

        assertEquals(ErrorMessageEnum.UNREGISTERED_CUSTOMER.getMessage(), exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("storeArguments")
    void updateSuccessTest(ClientSaveDTO request) {

        Client clientEntity = modelMapper.map(client, Client.class);
        clientEntity.setId(1L);
        clientEntity.setClientType("L");

        // Mock
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientEntity));
        when(clientRepository.save(any(Client.class))).thenReturn(clientEntity);

        SuccessDTO successDTO = this.clientService.update(1L, request);

        assertAll(
                () -> assertEquals("Cliente atualizado com sucesso.", successDTO.getMessage()),
                () -> assertEquals(1L, ((ClientDTO) successDTO.getData()).getId()),
                () -> assertEquals("L", ((ClientDTO) successDTO.getData()).getClientType())
        );
    }

    @Test
    void updateClientNotFoundExceptionTest() {

        // Mock
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        ValidationErrorException exception = assertThrows(ValidationErrorException.class,
                () -> this.clientService.update(100L, store));

        assertEquals(ErrorMessageEnum.UNREGISTERED_CUSTOMER.getMessage(), exception.getMessage());
    }

    @Test
    void getByIdSuccessTest() {

        Client clientEntity = modelMapper.map(client, Client.class);
        clientEntity.setId(1L);

        // Mock
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientEntity));

        ClientDTO response = this.clientService.getById(1L);

        assertAll(
                () -> assertEquals(1L, response.getId()),
                () -> assertEquals("Test", response.getName())
        );
    }

    @Test
    void getByIdClientNotFoundExceptionTest() {

        // Mock
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        ValidationErrorException exception = assertThrows(ValidationErrorException.class,
                () -> this.clientService.getById(100L));

        assertEquals(ErrorMessageEnum.UNREGISTERED_CUSTOMER.getMessage(), exception.getMessage());
    }

    @Test
    void getAllSuccessTest() {

        Client clientEntity = modelMapper.map(client, Client.class);
        clientEntity.setId(1L);

        // Mock
        when(clientRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(clientEntity)));

        Page<ClientDTO> response = this.clientService.getAll(Pageable.ofSize(10));

        assertAll(
                () -> assertEquals(1L, response.getContent().get(0).getId()),
                () -> assertEquals("Test", response.getContent().get(0).getName())
        );
    }

    @Test
    void saveSuccessTest() {

        ClientSaveDTO request = client;

        Client clientEntity = modelMapper.map(request, Client.class);
        clientEntity.setId(1L);

        // Mock
        when(clientRepository.save(any(Client.class))).thenReturn(clientEntity);

        SuccessDTO successDTO = this.clientService.save(request);

        assertAll(
                () -> assertEquals("Cliente cadastrado com sucesso.", successDTO.getMessage()),
                () -> assertEquals(1L, ((ClientDTO) successDTO.getData()).getId()),
                () -> assertEquals("C", ((ClientDTO) successDTO.getData()).getClientType())
        );
    }

    @Test
    void saveValidateClientTypeExceptionTest() {

        ClientSaveDTO request = client;
        request.setClientType("J");

        ValidationErrorException exception = assertThrows(ValidationErrorException.class,
                () -> this.clientService.save(request));

        assertEquals(ErrorMessageEnum.USER_TYPE_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    void saveInvalidDocumentExceptionTest() {

        ClientSaveDTO request = client;
        request.setDocumentNumber("1234");

        ValidationErrorException exception = assertThrows(ValidationErrorException.class,
                () -> this.clientService.save(request));

        assertEquals(ErrorMessageEnum.INVALID_DOCUMENT.getMessage(), exception.getMessage());
    }

}