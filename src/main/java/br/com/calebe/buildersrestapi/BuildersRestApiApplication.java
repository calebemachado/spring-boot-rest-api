package br.com.calebe.buildersrestapi;

import br.com.calebe.buildersrestapi.domain.Client;
import br.com.calebe.buildersrestapi.repository.IClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class BuildersRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuildersRestApiApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(IClientRepository clientRepository) {
        return args -> {
            var maria = new Client(
                    "Maria",
                    "85522227010",
                    LocalDate.of(2001, 2, 21)
            );
            clientRepository.save(maria);
        };
    }

}
