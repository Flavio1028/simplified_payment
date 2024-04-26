package br.com.codeup.payment.controller;

import br.com.codeup.payment.model.ErrorDTO;
import br.com.codeup.payment.model.SuccessDTO;
import br.com.codeup.payment.model.TransferDTO;
import br.com.codeup.payment.service.TransferService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SuccessDTO.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))})})
    @PostMapping
    public ResponseEntity<SuccessDTO> transfer(@RequestBody TransferDTO request) {
        return ResponseEntity.ok(this.transferService.transfer(request));
    }

}