package com.coderbank.cliente.service;

import com.coderbank.cliente.domain.Cliente;
import com.coderbank.cliente.dto.ClienteDTO;
import com.coderbank.cliente.events.producer.ClienteProducer;
import com.coderbank.cliente.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Bean gerenciado pelo spring
// Poderiamos criar uma interface e está classe ser a implementação dela
// Caso agente precisasse criar uma outra classe para mudar essas implementacoes
// nao teria uma grande mudanco nos controllers
@Service
@Slf4j
public class ClienteService {
    //    Ponto de unjeção onde digo para o spring criar instâncias dessa classe quando necessário
//    e remover quando nao tiver mais sendo usado
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteProducer clienteProducer;

    //Caso algo de errado, a transação com o banco de dados e'desfeita e os dados não são salvos
    @Transactional
    public ClienteDTO save(ClienteDTO clienteDTO) {
        log.info("Requisição para salvar cliente : {}", clienteDTO);

        salvarClienteBancoDados(clienteDTO);

        clienteProducer.adicionarEvento(clienteDTO);


        return clienteDTO;
    }

    private void salvarClienteBancoDados(ClienteDTO clienteDTO) {
        var cliente = new Cliente();

        BeanUtils.copyProperties(clienteDTO, cliente);

        cliente = clienteRepository.save(cliente);

        clienteDTO.setId(cliente.getId());
    }

    public List<ClienteDTO> findAll() {
        log.info("Requisição para obter todos os clientes");

        var clientesDTO = new ArrayList<ClienteDTO>();

        clienteRepository.findAll().stream().forEach(cliente -> {

            var clienteDTO = new ClienteDTO();

            BeanUtils.copyProperties(cliente, clienteDTO);

            clientesDTO.add(clienteDTO);
        });

        return clientesDTO;
    }

    public Optional<ClienteDTO> findById(UUID id) {
        log.info("Requisição para obter um cliente pelo id : {}", id);

        var clienteDTO = new ClienteDTO();

        var cliente = clienteRepository.findById(id);

        if (cliente.isPresent()) {
            BeanUtils.copyProperties(cliente.get(), clienteDTO);
            return Optional.of(clienteDTO);
        }

        return Optional.empty();
    }

    @Transactional
    public void delete(ClienteDTO clienteDTO) {
        log.info("Requisição para deletar um cliente : {}", clienteDTO);

        var cliente = new Cliente();

        BeanUtils.copyProperties(clienteDTO, cliente);

        clienteRepository.delete(cliente);
    }
}
