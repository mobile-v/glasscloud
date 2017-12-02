package ru.vmsystems.template.interfaces.api;

import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vmsystems.template.domain.service.ClientService;
import ru.vmsystems.template.interfaces.dto.ClientDto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

    @NotNull
    private final ClientService clientService;

    @Autowired
    public ClientController(@NotNull ClientService clientService) {
        this.clientService = clientService;
    }

    //http://localhost:8080/api/client
    @NotNull
    @ApiOperation(value = "Получить список всех клиентов")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClientDto>> get() {
        List<ClientDto> result = clientService.get();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/client/1
    @NotNull
    @ApiOperation(value = "Получить клиента по id клиента")
    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET)
    public ResponseEntity<ClientDto> get(@PathVariable(value = "clientId") Long clientId) {

        Optional<ClientDto> order = clientService.get(clientId);
        return order.map(dto ->
                new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() ->
                        new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    //http://localhost:8080/api/client/
    @ApiOperation(value = "Создать нового клиента")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ClientDto> save(@RequestBody ClientDto client) {
        clientService.save(client);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

//    //http://localhost:8080/api/client/1/
//    @ApiOperation(value = "Удалить клиента по id клиента")
//    @RequestMapping(value = "/{clientId}", method = RequestMethod.DELETE)
//    public ResponseEntity<?> delete(@PathVariable(value = "clientId") Long clientId) {
//        clientService.delete(clientId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
