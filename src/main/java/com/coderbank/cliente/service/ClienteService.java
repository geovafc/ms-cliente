package com.coderbank.cliente.service;

import com.coderbank.cliente.domain.Cliente;
import com.coderbank.cliente.dto.ClienteDTO;
import com.coderbank.cliente.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class ClienteService {
    //    Ponto de unjeção onde digo para o spring criar instâncias dessa classe quando necessário
//    e remover quando nao tiver mais sendo usado
    @Autowired
    private ClienteRepository clienteRepository;

    //Caso algo de errado, a transação com o banco de dados e'desfeita e os dados não são salvos
    @Transactional
    public ClienteDTO save(ClienteDTO clienteDTO) {
        //        explicar uso do var
        var cliente = new Cliente();

        BeanUtils.copyProperties(clienteDTO, cliente);

        cliente = clienteRepository.save(cliente);

        clienteDTO.setId(cliente.getId());

        return clienteDTO;
    }

    public List<ClienteDTO> findAll() {
        var clientesDTO = new ArrayList<ClienteDTO>();

        clienteRepository.findAll().stream().forEach(cliente -> {

            var clienteDTO = new ClienteDTO();

            BeanUtils.copyProperties(cliente, clienteDTO);

            clientesDTO.add(clienteDTO);
        });

        return clientesDTO;
    }

    public Optional<ClienteDTO> findById(UUID id) {
        var clienteDTO = new ClienteDTO();

        var cliente = clienteRepository.findById(id);

        if(cliente.isPresent()){
            BeanUtils.copyProperties(cliente.get(), clienteDTO);
            return Optional.of(clienteDTO);
        }

        return Optional.empty();
    }

    @Transactional
    public void delete(ClienteDTO clienteDTO) {
        var cliente = new Cliente();

        BeanUtils.copyProperties(clienteDTO, cliente);

        clienteRepository.delete(cliente);
    }
}
