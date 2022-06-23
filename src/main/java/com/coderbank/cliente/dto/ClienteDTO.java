package com.coderbank.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {
    private UUID id;

    //  verifica se o campo é null ou vazio
    @NotBlank
    private String nome;

    @NotBlank
    @Size(min = 11, max = 11)
    @CPF
    private String cpf;

    private Integer idade;

    private String email;

    private String endereco;
}
