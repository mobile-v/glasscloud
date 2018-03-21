package ru.vmsystems.template.interfaces.api;

import org.dozer.DozerBeanMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vmsystems.template.domain.model.MaterialEntity;
import ru.vmsystems.template.domain.model.ProcessEntity;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.infrastructure.persistence.ProcessRepository;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.ProcessDto;
import ru.vmsystems.template.interfaces.dto.Result;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController("ProcessControllerApi")
@RequestMapping("/api/process")
public class ProcessController {
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

    //http://localhost:8080/api/process?depth=2&typeId=1&materialId=1
    @NotNull
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProcessDto>> get(
            @RequestParam(value = "depth",  required = false) Integer depth,
            @RequestParam(value = "typeId",  required = false) Long typeId,
            @RequestParam(value = "materialId",  required = false) Long materialId
    ) {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Stream<ProcessEntity> stream = repository.getByCompany(user.get().getCompany()).stream();
        if (depth != null) {
            stream = stream.filter(dto -> dto.getDepth().equals(depth));
        }
        if (typeId != null) {
            stream = stream.filter(dto -> dto.getType().getId().equals(typeId));
        }
        if (materialId != null) {
            stream = stream
                    .filter(dto -> {
                        for (MaterialEntity material : dto.getMaterial()) {
                            if (material.getId().equals(materialId)){
                                return true;
                            }
                        }
                        return false;
                    });
        }

        List<ProcessDto> result = stream
                .map(e -> mapper.map(e, ProcessDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/process/1
    @NotNull
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProcessDto> get(@PathVariable(value = "id") Long processId) {

        ProcessDto type = mapper.map(repository.findOne(processId), ProcessDto.class);
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    //http://localhost:8080/api/process/
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ProcessDto> save(@RequestBody ProcessDto type) {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        ProcessEntity entity = mapper.map(type, ProcessEntity.class);
        entity.setCompany(user.get().getCompany());
        entity.setLastUpdate(new Timestamp(new Date().getTime()));
        repository.save(entity);
        type.setId(entity.getId());
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    //http://localhost:8080/api/process/1
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ProcessDto> update(@PathVariable(value = "id") Long id,
                                              @RequestBody ProcessDto type) {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        ProcessEntity entity = mapper.map(type, ProcessEntity.class);
        entity.setCompany(user.get().getCompany());
        entity.setLastUpdate(new Timestamp(new Date().getTime()));
        entity.setId(id);
        repository.save(entity);
        type.setId(entity.getId());
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    //http://localhost:8080/api/process/1/
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Result> delete(@PathVariable(value = "id") Long processId) {
        repository.delete(processId);
        return new ResponseEntity<>(new Result("OK"), HttpStatus.OK);
    }
}
