package br.com.calebe.buildersrestapi.controller.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientRequest {
    private String name;
    private String cpf;
    private LocalDate birthDate;
}
