package com.coderbank.cliente.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CB_CLIENTE")
public class Cliente implements Serializable {
    //controller das conversões feitas pela JVM
    private static final long serialVersionUID = 1L;

    @Id
//    AUTO - gerado de forma automatica
    @GeneratedValue(strategy = GenerationType.AUTO)
//Identificador Único Universal

//UUId são identificadores únicos que podem ser gerados em qualquer lugar
// são próprios para arquiteturas distribuídas, evitando conflitos que gerariam
// se fossem sequenciais

//    Permite trabalhar com UUID no mysql e h2
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column
    private Integer idade;

    @Column
    private String email;

    @Column
    private String endereco;

}
