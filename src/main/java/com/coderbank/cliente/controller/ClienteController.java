package com.coderbank.cliente.controller;

import com.coderbank.cliente.dto.ClienteDTO;
import com.coderbank.cliente.service.ClienteService;
import com.coderbank.cliente.controller.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//bean do spring
@RestController
//endpoint pode ser acessado de qualquer fonte
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
//    Com response entity montamos uma resposta tanto com o status, quanto com o corpo da
//    resposta e usamos object porque podemos mandar diversos tipos de retorno dependendo
//    das verificações que formos usar, não fico preso a retornar clienteDTO
    public ResponseEntity<Object> saveCliente(@RequestBody @Valid ClienteDTO clienteDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(clienteDTO));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCliente(@PathVariable(value = "id") UUID id){
        Optional<ClienteDTO> clienteDTOOptional = clienteService.findById(id);

        return clienteDTOOptional.<ResponseEntity<Object>>map(
                clienteDTO -> ResponseEntity.status(HttpStatus.OK).body(clienteDTO))
                .orElseThrow(
                        () -> new ObjectNotFoundException("Objeto não encontrado para o id: "+ id)
                        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCliente(@PathVariable(value = "id") UUID id){
        Optional<ClienteDTO> clienteDTOOptional = clienteService.findById(id);

        if (clienteDTOOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente not found.");
        }
        clienteService.delete(clienteDTOOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Cliente deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCliente(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ClienteDTO clienteDTORequest){
        Optional<ClienteDTO> clienteDTOOptional = clienteService.findById(id);

        if (clienteDTOOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(clienteService.save(clienteDTORequest));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente not found.");

    }
}
