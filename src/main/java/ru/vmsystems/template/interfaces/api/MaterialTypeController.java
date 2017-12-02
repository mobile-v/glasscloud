package ru.vmsystems.template.interfaces.api;

import org.dozer.DozerBeanMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vmsystems.template.domain.model.MaterialColorEntity;
import ru.vmsystems.template.domain.model.MaterialTypeEntity;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.infrastructure.persistence.MaterialColorRepository;
import ru.vmsystems.template.infrastructure.persistence.MaterialTypeRepository;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.MaterialColorDto;
import ru.vmsystems.template.interfaces.dto.MaterialTypeDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/material/type")
public class MaterialTypeController {
    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(MaterialTypeController.class);

    @NotNull
    private final MaterialTypeRepository repository;
    @NotNull
    private final DozerBeanMapper mapper;
    @NotNull
    private final UserRepository userRepository;
    @NotNull
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public MaterialTypeController(@NotNull MaterialTypeRepository repository,
                                  @NotNull DozerBeanMapper mapper,
                                  @NotNull UserRepository userRepository,
                                  @NotNull HttpServletRequest httpServletRequest) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.httpServletRequest = httpServletRequest;
    }

    //http://localhost:8080/api/material/type
    @NotNull
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<MaterialTypeDto>> get() {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        List<MaterialTypeDto> result = repository.getByCompany(user.get().getCompany()).stream()
                .map(e -> mapper.map(e, MaterialTypeDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/material/type/1
    @NotNull
    @RequestMapping(value = "/{typeId}", method = RequestMethod.GET)
    public ResponseEntity<MaterialTypeDto> get(@PathVariable(value = "typeId") Long typeId) {

        MaterialTypeDto color = mapper.map(repository.findOne(typeId), MaterialTypeDto.class);
        return new ResponseEntity<>(color, HttpStatus.OK);
    }

    //http://localhost:8080/api/material/type/
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<MaterialTypeDto> save(@RequestBody MaterialTypeDto type) {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        MaterialTypeEntity entity = mapper.map(type, MaterialTypeEntity.class);
        entity.setCompany(user.get().getCompany());
        repository.save(entity);
        type.setId(entity.getId());
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    //http://localhost:8080/api/material/type/1/
    @RequestMapping(value = "/{typeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "typeId") Long typeId) {
        repository.delete(typeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
