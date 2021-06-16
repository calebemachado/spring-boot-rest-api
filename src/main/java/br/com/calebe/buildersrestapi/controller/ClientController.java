package br.com.calebe.buildersrestapi.controller;

import br.com.calebe.buildersrestapi.controller.request.ClientRequest;
import br.com.calebe.buildersrestapi.controller.response.ClientResponse;
import br.com.calebe.buildersrestapi.domain.dto.ClientDTO;
import br.com.calebe.buildersrestapi.service.ClientService;
import br.com.calebe.buildersrestapi.specification.IClientSpec;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;

import static java.util.Objects.isNull;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/clients", produces = "application/json")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @ApiOperation(value = "Return a paginated list of clients.")
    @GetMapping
    public ClientResponse findAll(IClientSpec clientSpec,
        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return clientService.findAll(clientSpec, Pageable.ofSize(size).withPage(page));
    }

    @ApiOperation(value = "Return a single client based on ID informed.")
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        ClientDTO client = clientService.findById(id);

        if (isNull(client))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(client);
    }

    @ApiOperation(value = "Creates a client.")
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ClientRequest clientRequest) {
        Long clientId = clientService.create(clientRequest);

        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(clientId).toUri();

        return ResponseEntity.created(location).build();

    }

    @ApiOperation(value = "Delete logicaly the client based on ID informed.")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        clientService.deleteById(id);
    }

    @ApiOperation(value = "Update entire client information.")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody ClientDTO client, @PathVariable Long id) {
        clientService.update(id, client);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Update only informed data of client.")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> patch(@PathVariable Long id, @RequestBody Map<String, Object> changes) {
        clientService.patch(id, changes);

        return ResponseEntity.noContent().build();
    }
}
