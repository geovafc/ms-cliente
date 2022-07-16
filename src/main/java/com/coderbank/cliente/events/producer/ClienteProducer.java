package com.coderbank.cliente.events.producer;

import com.coderbank.cliente.dto.ClienteDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

//Bean gerenciado pelo próprio spring boot
@Component
//Adc producer no construtor
@RequiredArgsConstructor
@Slf4j

public class ClienteProducer {

    private final KafkaTemplate<String, Object> producer;

    public void adicionarEvento(ClienteDTO clienteDTO) {
        log.info("Requisição para adicionar cliente no broker: {}", clienteDTO);

        var cliente = builderCliente(clienteDTO);
        
//        Objeto a ser enviado para o kafka com a informação do tópico e objeto
        ProducerRecord<String, Object> record = new ProducerRecord<>("cliente_cadastrado", clienteDTO);
        
        producer.send(record).addCallback(
                s -> {
                    log.info("Evento produzido -> {}", cliente);
                },
                f -> {
                    log.error("Erro ao produzir evento -> {}", f.getMessage());
                }
        );
        
//        producer.send("", cliente);
    }

    private ClienteDTO builderCliente(ClienteDTO clienteDTO) {

        return ClienteDTO.builder()
                .id(clienteDTO.getId())
                .cpf(clienteDTO.getCpf())
                .nome(clienteDTO.getNome())
                .build();
    }
}
