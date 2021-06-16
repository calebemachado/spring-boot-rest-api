package br.com.calebe.buildersrestapi.specification;

import br.com.calebe.buildersrestapi.domain.Client;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Or({
        @Spec(path = "name", params = "name", spec = LikeIgnoreCase.class),
        @Spec(path = "cpf", params = "cpf", spec = LikeIgnoreCase.class),
        @Spec(path = "birthDate", params = "birthDate", spec = Equal.class)
})
public interface IClientSpec extends Specification<Client> {
}
