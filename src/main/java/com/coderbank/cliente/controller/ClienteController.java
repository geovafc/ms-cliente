package com.coderbank.cliente.controller;

import com.coderbank.cliente.dto.ClienteDTO;
import com.coderbank.cliente.service.ClienteService;
import com.coderbank.cliente.controller.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado para o id: ";

    @PostMapping
//    Com response entity montamos uma resposta tanto com o status, quanto com o corpo da
//    resposta e usamos object porque podemos mandar diversos tipos de retorno dependendo
//    das verificações que formos usar, não fico preso a retornar clienteDTO
    public ResponseEntity<Object> saveCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
        log.info("Requisição REST para salvar cliente : {}", clienteDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(clienteDTO));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        log.info("Requisição REST para obter todos os clientes");

        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCliente(@PathVariable(value = "id") UUID id) {
        log.info("Requisição REST para obter um cliente pelo id : {}", id);

        Optional<ClienteDTO> clienteDTOOptional = clienteService.findById(id);

        return clienteDTOOptional.<ResponseEntity<Object>>map(
                        clienteDTO -> ResponseEntity.status(HttpStatus.OK).body(clienteDTO))
                .orElseThrow(
                        () -> {

                            log.error(OBJETO_NAO_ENCONTRADO+ " {}", id);

                            throw new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO + id);

                        }
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCliente(@PathVariable(value = "id") UUID id) {
        log.info("Requisição REST para deletar um cliente : {}", id);

        Optional<ClienteDTO> clienteDTOOptional = clienteService.findById(id);

        return clienteDTOOptional.<ResponseEntity<Object>>map(
                clienteDTO -> {

                    clienteService.delete(clienteDTO);

                    return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado com sucesso");

                }).orElseThrow(() -> {

            log.error("Falha ao deletar um cliente, objeto não encontrado para o id: {}", id);

            throw new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO + id);
        });

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCliente(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ClienteDTO clienteDTORequest) {
        log.info("Requisição REST para atualizar um cliente : {}", clienteDTORequest);

        Optional<ClienteDTO> clienteDTOOptional = clienteService.findById(id);

        if (clienteDTOOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(clienteService.save(clienteDTORequest));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente not found.");

    }
}
