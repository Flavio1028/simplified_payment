package br.com.codeup.payment.controller;

import br.com.codeup.payment.model.SuccessDTO;
import br.com.codeup.payment.model.TransferDTO;
import br.com.codeup.payment.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<SuccessDTO> transfer(@RequestBody TransferDTO request) {
        return ResponseEntity.ok(this.transferService.transfer(request));
    }

}