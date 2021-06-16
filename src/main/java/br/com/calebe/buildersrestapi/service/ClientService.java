package br.com.calebe.buildersrestapi.service;

import br.com.calebe.buildersrestapi.controller.request.ClientRequest;
import br.com.calebe.buildersrestapi.controller.response.ClientResponse;
import br.com.calebe.buildersrestapi.domain.Client;
import br.com.calebe.buildersrestapi.domain.dto.ClientDTO;
import br.com.calebe.buildersrestapi.exception.ApiRequestException;
import br.com.calebe.buildersrestapi.repository.IClientRepository;
import br.com.calebe.buildersrestapi.specification.IClientSpec;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class ClientService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IClientRepository repository;

    public ClientResponse findAll(IClientSpec clientSpec, Pageable pageable) {
        Page<Client> page = repository.findAll(clientSpec, pageable);

        List<ClientDTO> collection = page.stream().map(item -> modelMapper.map(item, ClientDTO.class))
                .collect(Collectors.toList());

        return buildResponse(page, collection);
    }

    private ClientResponse buildResponse(Page<Client> page, List<ClientDTO> clients) {
        return ClientResponse.builder()
                .clients(clients)
                .totalPages(page.getTotalPages())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .build();
    }

    public ClientDTO findById(Long id) {
        if (nonNull(id)) {
            Client optionalClient = repository.findById(id)
                    .orElseThrow(() -> new ApiRequestException("Client with id: " + id + " not found."));

            return modelMapper.map(optionalClient, ClientDTO.class);
        }

        throw new ApiRequestException("ID not informed");
    }

    public Long create(ClientRequest clientRequest) {
        Client clientEntity = modelMapper.map(clientRequest, Client.class);

        return repository.save(clientEntity).getId();
    }

    public void deleteById(Long id) {
        if (nonNull(id)) {
            repository.deleteById(id);
        }

        throw new ApiRequestException("ID not informed");
    }

    public void update(Long id, ClientDTO clientDTO) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Client with id: " + id + " not found."));

        modelMapper.map(clientDTO, client);
        client.setId(id);
        repository.save(client);
    }

    public void patch(Long id, Map<String, Object> changes) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Client with id: " + id + " not found."));

        repository.save(mapPatchingChanges(changes, client));
    }

    private Client mapPatchingChanges(Map<String, Object> changes, Client client) {
        ClientDTO clientDTO = new ClientDTO();
        modelMapper.map(client, clientDTO);

        changes.forEach(
                (change, value) -> {
                    switch (change){
                        case "name": clientDTO.setName((String) value); break;
                        case "cpf": clientDTO.setCpf((String) value); break;
                        case "birthDate": clientDTO.setBirthDate((LocalDate) value); break;
                        default: break;
                    }
                }
        );

        modelMapper.map(clientDTO, client);
        return client;
    }
}
