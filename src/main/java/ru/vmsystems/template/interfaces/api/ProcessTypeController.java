package ru.vmsystems.template.interfaces.api;

import org.dozer.DozerBeanMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vmsystems.template.domain.model.ProcessTypeEntity;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.infrastructure.persistence.ProcessTypeRepository;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.MaterialTypeDto;
import ru.vmsystems.template.interfaces.dto.ProcessTypeDto;
import ru.vmsystems.template.interfaces.dto.Result;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController("ProcessTypeControllerApi")
@RequestMapping("/api/process/type")
public class ProcessTypeController {
    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(ProcessTypeController.class);

    @NotNull
    private final ProcessTypeRepository repository;
    @NotNull
    private final DozerBeanMapper mapper;
    @NotNull
    private final UserRepository userRepository;
    @NotNull
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public ProcessTypeController(@NotNull ProcessTypeRepository repository,
                                 @NotNull DozerBeanMapper mapper,
                                 @NotNull UserRepository userRepository,
                                 @NotNull HttpServletRequest httpServletRequest) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.httpServletRequest = httpServletRequest;
    }

    //http://localhost:8080/api/process/type
    @NotNull
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProcessTypeDto>> get() {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        List<ProcessTypeDto> result = repository.getByCompany(user.get().getCompany()).stream()
                .map(e -> mapper.map(e, ProcessTypeDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/process/type/1
    @NotNull
    @RequestMapping(value = "/{typeId}", method = RequestMethod.GET)
    public ResponseEntity<ProcessTypeDto> get(@PathVariable(value = "typeId") Long typeId) {

        ProcessTypeDto type = mapper.map(repository.findOne(typeId), ProcessTypeDto.class);
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    //http://localhost:8080/api/process/type/
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<MaterialTypeDto> save(@RequestBody MaterialTypeDto type) {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        ProcessTypeEntity entity = mapper.map(type, ProcessTypeEntity.class);
        entity.setCompany(user.get().getCompany());
        entity.setLastUpdate(new Timestamp(new Date().getTime()));
        repository.save(entity);
        type.setId(entity.getId());
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    //http://localhost:8080/api/process/type/1/
    @RequestMapping(value = "/{typeId}", method = RequestMethod.DELETE)
    public ResponseEntity<Result> delete(@PathVariable(value = "typeId") Long typeId) {
        repository.delete(typeId);
        return new ResponseEntity<>(new Result("OK"), HttpStatus.OK);
    }
}
