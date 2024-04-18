package br.com.codeup.payment.controller;

import br.com.codeup.payment.model.ClientDTO;
import br.com.codeup.payment.model.ClientSaveDTO;
import br.com.codeup.payment.model.SuccessDTO;
import br.com.codeup.payment.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<SuccessDTO> save(@RequestBody ClientSaveDTO clientSaveDTO) {
        return ResponseEntity.status(201).body(this.clientService.save(clientSaveDTO));
    }

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> getAll(Pageable page) {
        return ResponseEntity.ok(this.clientService.getAll(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(this.clientService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessDTO> update(@PathVariable Long id, @RequestBody ClientSaveDTO clientSaveDTO) {
        return ResponseEntity.ok(this.clientService.update(id, clientSaveDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(this.clientService.delete(id));
    }

}