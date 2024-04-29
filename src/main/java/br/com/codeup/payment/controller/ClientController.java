package br.com.codeup.payment.controller;

import br.com.codeup.payment.model.ClientDTO;
import br.com.codeup.payment.model.ClientSaveDTO;
import br.com.codeup.payment.model.ErrorDTO;
import br.com.codeup.payment.model.SuccessDTO;
import br.com.codeup.payment.service.ClientService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SuccessDTO.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))})})
    @PostMapping
    public ResponseEntity<SuccessDTO> save(@RequestBody ClientSaveDTO clientSaveDTO) {
        return ResponseEntity.status(201).body(this.clientService.save(clientSaveDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))})})
    @GetMapping
    public ResponseEntity<Page<ClientDTO>> getAll(Pageable page) {
        return ResponseEntity.ok(this.clientService.getAll(page));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ClientDTO.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(this.clientService.getById(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SuccessDTO.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<SuccessDTO> update(@PathVariable Long id, @RequestBody ClientSaveDTO clientSaveDTO) {
        return ResponseEntity.ok(this.clientService.update(id, clientSaveDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SuccessDTO.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(this.clientService.delete(id));
    }

}