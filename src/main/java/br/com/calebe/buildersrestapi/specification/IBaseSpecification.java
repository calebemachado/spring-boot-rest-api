package br.com.calebe.buildersrestapi.specification;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = "active", spec = Equal.class, constVal = "true")
public interface IBaseSpecification<T> extends Specification<T> {
}