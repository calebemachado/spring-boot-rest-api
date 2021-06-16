package br.com.calebe.buildersrestapi.repository;

import br.com.calebe.buildersrestapi.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor {

    @Query("FROM Client c WHERE LOWER(c.name) like %:name% OR c.cpf like %:cpf% ")
    Page<Client> findByNameOrCpf(@Param("name") String name, @Param("cpf") String cpf, Pageable pageable);

    Optional<Client> findByCpf(String cpf);
}
