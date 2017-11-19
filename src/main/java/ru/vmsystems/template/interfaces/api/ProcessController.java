package ru.vmsystems.template.interfaces.api;

import org.dozer.DozerBeanMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vmsystems.template.domain.model.ProcessEntity;
import ru.vmsystems.template.domain.model.ProcessTypeEntity;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.infrastructure.persistence.ProcessRepository;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.MaterialTypeDto;
import ru.vmsystems.template.interfaces.dto.ProcessDto;
import ru.vmsystems.template.interfaces.dto.ProcessTypeDto;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/process")
public final class ProcessController {
    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(ProcessController.class);

    @NotNull
    private final ProcessRepository repository;
    @NotNull
    private final DozerBeanMapper mapper;
    @NotNull
    private final UserRepository userRepository;
    @NotNull
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public ProcessController(@NotNull ProcessRepository repository,
                             @NotNull DozerBeanMapper mapper,
                             @NotNull UserRepository userRepository,
                             @NotNull HttpServletRequest httpServletRequest) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.httpServletRequest = httpServletRequest;
    }

    //http://localhost:8080/api/process
    @NotNull
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProcessDto>> get() {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        List<ProcessDto> result = repository.getByCompany(user.get().getCompany()).stream()
                .map(e -> mapper.map(e, ProcessDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/process/1
    @NotNull
    @RequestMapping(value = "/{processId}", method = RequestMethod.GET)
    public ResponseEntity<ProcessDto> get(@PathVariable(value = "processId") Long processId) {

        ProcessDto type = mapper.map(repository.findOne(processId), ProcessDto.class);
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    //http://localhost:8080/api/process/
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<MaterialTypeDto> save(@RequestBody MaterialTypeDto type) {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        ProcessEntity entity = mapper.map(type, ProcessEntity.class);
        entity.setCompany(user.get().getCompany());
        entity.setLastUpdate(new Timestamp(new Date().getTime()));
        repository.save(entity);
        type.setId(entity.getId());
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    //http://localhost:8080/api/process/1/
    @RequestMapping(value = "/{processId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "processId") Long processId) {
        repository.delete(processId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
