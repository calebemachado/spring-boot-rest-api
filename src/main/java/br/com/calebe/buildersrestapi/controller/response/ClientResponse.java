package br.com.calebe.buildersrestapi.controller.response;

import br.com.calebe.buildersrestapi.domain.dto.ClientDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ClientResponse {

    private List<ClientDTO> clients;
    private Integer totalPages;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
}
