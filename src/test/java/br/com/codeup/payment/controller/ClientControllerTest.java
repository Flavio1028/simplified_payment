package br.com.codeup.payment.controller;

import br.com.codeup.payment.model.ClientDTO;
import br.com.codeup.payment.model.ClientSaveDTO;
import br.com.codeup.payment.model.SuccessDTO;
import br.com.codeup.payment.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @Test
    void saveSuccessTest() throws Exception {

        // Mock
        when(clientService.save(any(ClientSaveDTO.class))).thenReturn(SuccessDTO.builder()
                .message("Teste sucesso JUnit.")
                .build());

        MvcResult result = this.mockMvc.perform(post("/client")
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(new ClientSaveDTO())))
                .andReturn();

        SuccessDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), SuccessDTO.class);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus()),
                () -> assertEquals("Teste sucesso JUnit.", response.getMessage()),
                () -> assertNull(response.getData())
        );

    }

    @Test
    void getAllSuccessTest() throws Exception {

        Page<ClientDTO> page = new PageImpl<>(List.of(ClientDTO.builder()
                .id(1L)
                .name("JUnit")
                .build()));

        when(clientService.getAll(any(Pageable.class))).thenReturn(page);

        MvcResult result = this.mockMvc.perform(get("/client")
                        .contentType("application/json")
                        .param("page", String.valueOf(Pageable.ofSize(10))))
                .andReturn();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()));
    }

    @Test
    void getSuccessTest() throws Exception {

        ClientDTO clientDTO = ClientDTO.builder()
                .id(1L)
                .name("JUnit")
                .build();

        when(clientService.getById(anyLong())).thenReturn(clientDTO);

        MvcResult result = this.mockMvc.perform(get("/client/1")
                        .contentType("application/json"))
                .andReturn();

        ClientDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), ClientDTO.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(clientDTO.getId(), response.getId()),
                () -> assertEquals(clientDTO.getName(), response.getName())
        );
    }

    @Test
    void updateSuccessTest() throws Exception {

        // Mock
        when(clientService.update(anyLong(), any(ClientSaveDTO.class))).thenReturn(SuccessDTO.builder()
                .message("Teste sucesso JUnit.")
                .build());

        MvcResult result = this.mockMvc.perform(put("/client/1")
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(new ClientSaveDTO())))
                .andReturn();

        SuccessDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), SuccessDTO.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals("Teste sucesso JUnit.", response.getMessage()),
                () -> assertNull(response.getData())
        );

    }

    @Test
    void deleteSuccessTest() throws Exception {

        // Mock
        when(clientService.delete(anyLong())).thenReturn(SuccessDTO.builder()
                .message("Teste sucesso JUnit.")
                .build());

        MvcResult result = this.mockMvc.perform(delete("/client/1")
                        .contentType("application/json"))
                .andReturn();

        SuccessDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), SuccessDTO.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals("Teste sucesso JUnit.", response.getMessage()),
                () -> assertNull(response.getData())
        );
    }

}