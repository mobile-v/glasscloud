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
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.infrastructure.persistence.ColorRepository;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.MaterialColorDto;
import ru.vmsystems.template.interfaces.dto.ReceptionOfOrderDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/material/color")
public final class ColorController {
    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(ColorController.class);

    @NotNull
    private final ColorRepository repository;
    @NotNull
    private final DozerBeanMapper mapper;
    @NotNull
    private final UserRepository userRepository;
    @NotNull
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public ColorController(@NotNull ColorRepository repository,
                           @NotNull DozerBeanMapper mapper,
                           @NotNull UserRepository userRepository,
                           @NotNull HttpServletRequest httpServletRequest) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.httpServletRequest = httpServletRequest;
    }

    //http://localhost:8080/api/material/color
    @NotNull
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<MaterialColorDto>> get() {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        List<MaterialColorDto> result = repository.getByCompany(user.get().getCompany()).stream()
                .map(e -> mapper.map(e, MaterialColorDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/material/color/1
    @NotNull
    @RequestMapping(value = "/{colorId}", method = RequestMethod.GET)
    public ResponseEntity<MaterialColorDto> get(@PathVariable(value = "colorId") Long colorId) {

        MaterialColorDto color = mapper.map(repository.findOne(colorId), MaterialColorDto.class);
        return new ResponseEntity<>(color, HttpStatus.NO_CONTENT);
    }

    //http://localhost:8080/api/material/color/
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<MaterialColorDto> save(@RequestBody MaterialColorDto color) {
        Optional<UserEntity> user = userRepository.getByLogin(httpServletRequest.getRemoteUser());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        MaterialColorEntity entity = mapper.map(color, MaterialColorEntity.class);
        entity.setCompany(user.get().getCompany());
        repository.save(entity);
        color.setId(entity.getId());
        return new ResponseEntity<>(color, HttpStatus.OK);
    }

    //http://localhost:8080/api/material/color/1/
    @RequestMapping(value = "/{colorId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "colorId") Long colorId) {
        repository.delete(colorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
