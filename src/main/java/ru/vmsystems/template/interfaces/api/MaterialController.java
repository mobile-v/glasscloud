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
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.infrastructure.persistence.MaterialRepository;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.MaterialDto;
import ru.vmsystems.template.interfaces.dto.Result;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/material")
public class MaterialController {
    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(MaterialController.class);

    @NotNull
    private final MaterialRepository repository;
    @NotNull
    private final DozerBeanMapper mapper;
    @NotNull
    private final UserRepository userRepository;
    @NotNull
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public MaterialController(@NotNull MaterialRepository repository,
                              @NotNull DozerBeanMapper mapper,
                              @NotNull UserRepository userRepository,
                              @NotNull HttpServletRequest httpServletRequest) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.httpServletRequest = httpServletRequest;
    }

    //http://localhost:8080/api/material
    @NotNull
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<MaterialDto>> get() {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        List<MaterialDto> result = repository.getByCompany(user.get().getCompany()).stream()
                .map(e -> mapper.map(e, MaterialDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/material/type/1
    @NotNull
    @RequestMapping(value = "/{typeId}", method = RequestMethod.GET)
    public ResponseEntity<MaterialDto> get(@PathVariable(value = "typeId") Long typeId) {

        MaterialDto color = mapper.map(repository.findOne(typeId), MaterialDto.class);
        return new ResponseEntity<>(color, HttpStatus.OK);
    }

    //http://localhost:8080/api/material/
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<MaterialDto> save(@RequestBody MaterialDto type) {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        MaterialEntity entity = mapper.map(type, MaterialEntity.class);
        entity.setCompany(user.get().getCompany());
        entity.setLastUpdate(new Timestamp(new Date().getTime()));
        repository.save(entity);
        type.setId(entity.getId());
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    //http://localhost:8080/api/material/1
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<MaterialDto> update(@PathVariable(value = "id") Long id,
                                              @RequestBody MaterialDto type) {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        MaterialEntity entity = mapper.map(type, MaterialEntity.class);
        entity.setCompany(user.get().getCompany());
        entity.setLastUpdate(new Timestamp(new Date().getTime()));
        entity.setId(id);
        repository.save(entity);
        type.setId(entity.getId());
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    //http://localhost:8080/api/material/1/
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Result> delete(@PathVariable(value = "id") Long id) {
        repository.delete(id);
        return new ResponseEntity<>(new Result("OK"), HttpStatus.OK);
    }
}
