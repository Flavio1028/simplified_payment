package br.com.codeup.payment.service.impl;

import br.com.codeup.payment.entity.Client;
import br.com.codeup.payment.model.MessageDTO;
import br.com.codeup.payment.model.SuccessDTO;
import br.com.codeup.payment.model.TransferDTO;
import br.com.codeup.payment.repository.ClientRepository;
import br.com.codeup.payment.util.enums.ErrorMessageEnum;
import br.com.codeup.payment.util.exception.ValidationErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private RestTemplate restTemplate;

    private static Client client;

    private static Client store;

    @BeforeEach
    void setUp() {

        client = new Client();
        client.setId(1L);
        client.setBalance(BigDecimal.valueOf(200L));
        client.setClientType("C");

        store = new Client();
        store.setId(2L);
        store.setBalance(BigDecimal.valueOf(400L));
        store.setClientType("L");

    }

    private TransferDTO getRequest() {
        return TransferDTO.builder()
                .payer(1L)
                .payee(2L)
                .value(BigDecimal.valueOf(100L))
                .build();
    }

    @Test
    void transferSuccess() {

        TransferDTO request = this.getRequest();

        // Mock
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client)).thenReturn(Optional.of(store));
        when(clientRepository.save(any(Client.class))).thenReturn(null);
        when(restTemplate.getForObject(anyString(), any()))
                .thenReturn(MessageDTO.builder().message("Autorizado").build())
                .thenReturn(MessageDTO.builder().message("true").build());


        SuccessDTO response = transferService.transfer(request);

        assertEquals("Transferência realizada com sucesso.", response.getMessage());
    }

    @Test
    void transferSendNotificationExceptionTest() {

        TransferDTO request = this.getRequest();

        // Mock
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client)).thenReturn(Optional.of(store));
        when(clientRepository.save(any(Client.class))).thenReturn(null);
        when(restTemplate.getForObject(anyString(), any()))
                .thenReturn(MessageDTO.builder().message("Autorizado").build())
                .thenReturn(MessageDTO.builder().message("false").build());


        ValidationErrorException exception = assertThrows(ValidationErrorException.class, () -> transferService.transfer(request));

        assertEquals(ErrorMessageEnum.NOTIFICATION_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    void transferAuthorizerAPIExceptionTest() {

        TransferDTO request = this.getRequest();

        // Mock
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client)).thenReturn(Optional.of(store));
        when(clientRepository.save(any(Client.class))).thenReturn(null);
        when(restTemplate.getForObject(anyString(), any()))
                .thenReturn(MessageDTO.builder().message("Error").build());


        ValidationErrorException exception = assertThrows(ValidationErrorException.class, () -> transferService.transfer(request));

        assertEquals(ErrorMessageEnum.AUTHORIZED_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    void transferInsufficientBalanceExceptionTest() {

        TransferDTO request = this.getRequest();

        Client clientTemp = new Client();
        clientTemp.setClientType("C");
        clientTemp.setBalance(BigDecimal.ZERO);

        // Mock
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientTemp)).thenReturn(Optional.of(store));

        ValidationErrorException exception = assertThrows(ValidationErrorException.class, () -> transferService.transfer(request));

        assertEquals(ErrorMessageEnum.INSUFFICIENT_BALANCE.getMessage(), exception.getMessage());
    }

    @Test
    void transferStoreTransferExceptionTest() {

        TransferDTO request = this.getRequest();

        // Mock
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(store));

        ValidationErrorException exception = assertThrows(ValidationErrorException.class, () -> transferService.transfer(request));

        assertEquals(ErrorMessageEnum.CANNOT_MAKE_TRANSFER.getMessage(), exception.getMessage());
    }

    @Test
    void transferPayerNotFoundExceptionTest() {

        TransferDTO request = this.getRequest();

        // Mock
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        ValidationErrorException exception = assertThrows(ValidationErrorException.class, () -> transferService.transfer(request));

        assertEquals(ErrorMessageEnum.UNREGISTERED_CUSTOMER.getMessage(), exception.getMessage());
    }

}