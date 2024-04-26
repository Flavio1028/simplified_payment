package br.com.codeup.payment.service;

import br.com.codeup.payment.model.ClientDTO;
import br.com.codeup.payment.model.ClientSaveDTO;
import br.com.codeup.payment.model.SuccessDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    SuccessDTO save(@Valid ClientSaveDTO request);

    Page<ClientDTO> getAll(Pageable page);

    ClientDTO getById(Long id);

    SuccessDTO update(Long id, ClientSaveDTO request);

    SuccessDTO delete(Long id);

}