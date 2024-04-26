package br.com.codeup.payment.service;

import br.com.codeup.payment.model.SuccessDTO;
import br.com.codeup.payment.model.TransferDTO;
import jakarta.validation.Valid;

public interface TransferService {

    SuccessDTO transfer(@Valid TransferDTO request);

}