package br.com.codeup.payment.controller;

import br.com.codeup.payment.model.SuccessDTO;
import br.com.codeup.payment.model.TransferDTO;
import br.com.codeup.payment.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransferService transferService;

    @Test
    void transferSuccessTest() throws Exception {

        // Mock
        when(transferService.transfer(any())).thenReturn(SuccessDTO.builder()
                .message("Transferencia realizada com sucesso.")
                .build());

        MvcResult result = this.mockMvc.perform(post("/transfer")
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(new TransferDTO())))
                .andReturn();

        SuccessDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), SuccessDTO.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals("Transferencia realizada com sucesso.", response.getMessage()),
                () -> assertNull(response.getData())
        );
    }

}