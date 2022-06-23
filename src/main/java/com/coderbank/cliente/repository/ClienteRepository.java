package com.coderbank.cliente.repository;

import com.coderbank.cliente.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
// já possui vários métodos prontos para transações com banco de dados
// Como estamos extendendo de JPaRepository, essa interface já será
// um bean gerenciada pelo spring boot, mas para quem quiser pode anotar aqui
// com @repository que é um steriotipo do spring para transacoes com banco de dados

 public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
}
