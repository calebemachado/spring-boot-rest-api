package br.com.calebe.buildersrestapi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

import static java.util.Objects.nonNull;

@Entity
@Table(
        name = "clients",
        uniqueConstraints = {
                @UniqueConstraint(name = "clients_cpf_unique", columnNames = "cpf")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Client extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_seq_gen")
    @SequenceGenerator(name = "clients_seq_gen", sequenceName = "clients_id_seq", allocationSize = 1)
    @Column(name="id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Transient
    private Integer age;

    public Client(String name, String cpf, LocalDate birthDate) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        if (nonNull(this.birthDate)) {
            return Period.between(birthDate, LocalDate.now()).getYears();
        }

        return age;
    }

    public void setCpf(String cpf) {
        if (nonNull(cpf)) {
            this.cpf = cpf.replaceAll("[^a-zA-Z0-9]", "");
        } else {
            this.cpf = cpf;
        }
    }
}
