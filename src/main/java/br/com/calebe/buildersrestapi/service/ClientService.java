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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

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
            var optionalClient = repository.findById(id)
                    .orElseThrow(() -> new ApiRequestException("Client with id: " + id + " not found."));

            return modelMapper.map(optionalClient, ClientDTO.class);
        }

        throw new ApiRequestException("ID not informed");
    }

    public Long create(ClientRequest clientRequest) {
        var clientEntity = modelMapper.map(clientRequest, Client.class);

        validateFields(clientEntity);

        Optional<Client> optionalClient = repository.findByCpf(clientEntity.getCpf());
        if (optionalClient.isPresent())
            throw new ApiRequestException("Client with CPF already created.");

        return repository.save(clientEntity).getId();
    }

    private void validateFields(Client clientEntity) {
        if (isNull(clientEntity.getCpf()))
            throw new ApiRequestException("CPF field is required");

        if (isNull(clientEntity.getBirthDate()))
            throw new ApiRequestException("Birth date field is required");

        if (isNull(clientEntity.getName()))
            throw new ApiRequestException("Name field is required");
    }

    public void deleteById(Long id) {
        if (isNull(id))
            throw new ApiRequestException("ID not informed");

        repository.deleteById(id);
    }

    public void update(Long id, ClientDTO clientDTO) {
        var client = repository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Client with id: " + id + " not found."));

        if (nonNull(clientDTO.getCpf())){
            Optional<Client> optionalClient = repository.findByCpf(clientDTO.getCpf());

            if (optionalClient.isPresent() && !Objects.equals(optionalClient.get().getId(), id))
                throw new ApiRequestException("Client with CPF already created.");
        }

        modelMapper.map(clientDTO, client);
        validateFields(client);

        client.setId(id);
        repository.save(client);
    }

    public void patch(Long id, Map<String, Object> changes) {
        var client = repository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Client with id: " + id + " not found."));

        var mappedClientWithChanges = mapPatchingChanges(changes, client);

        if (nonNull(mappedClientWithChanges.getCpf())){
            Optional<Client> optionalClient = repository.findByCpf(mappedClientWithChanges.getCpf());

            if (optionalClient.isPresent() && !Objects.equals(optionalClient.get().getId(), id))
                throw new ApiRequestException("Client with CPF already created.");
        }


        repository.save(mappedClientWithChanges);
    }

    private Client mapPatchingChanges(Map<String, Object> changes, Client client) {
        var clientDTO = new ClientDTO();
        modelMapper.map(client, clientDTO);

        changes.forEach(
                (change, value) -> {
                    switch (change){
                        case "name": clientDTO.setName((String) value); break;
                        case "cpf": clientDTO.setCpf((String) value); break;
                        case "birthDate": clientDTO.setBirthDate(handleLocalDate((String) value)); break;
                        default: break;
                    }
                }
        );

        modelMapper.map(clientDTO, client);
        return client;
    }

    private LocalDate handleLocalDate(String birthDate) {
        if (isEmpty(birthDate)) {
            return null;
        } else {
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(birthDate, formatter);
        }
    }
}
