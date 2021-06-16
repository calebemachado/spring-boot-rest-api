package br.com.calebe.buildersrestapi.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDTO {

    private Long id;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private Integer age;
}
