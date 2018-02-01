package ru.vmsystems.template.interfaces.page;

import org.dozer.DozerBeanMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.context.WebContext;
import ru.vmsystems.template.domain.model.MaterialColorEntity;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.domain.service.BackService;
import ru.vmsystems.template.domain.service.ColorService;
import ru.vmsystems.template.domain.service.ReceptionOfOrderService;
import ru.vmsystems.template.infrastructure.persistence.MaterialColorRepository;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.MaterialColorDto;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ru.vmsystems.template.interfaces.page.URLS.URI_REDIRECT_TO_COLORS;
import static ru.vmsystems.template.interfaces.page.URLS.URI_REDIRECT_TO_LOGIN;

@Controller
@RequestMapping("glass")
public class ColorController extends BackService {

    private static final Logger LOG = LoggerFactory.getLogger(ColorController.class);

    @NotNull
    private final ColorService service;
    private final UserRepository userRepository;
    private final ReceptionOfOrderService receptionOfOrderService;

    @Autowired
    public ColorController(@NotNull ColorService service,
                           @NotNull UserRepository userRepository,
                           ReceptionOfOrderService receptionOfOrderService) {
        this.service = service;
        this.userRepository = userRepository;
        this.receptionOfOrderService = receptionOfOrderService;
    }

    //http://localhost:8080/glass/colors
    @RequestMapping(value = "/colors", method = RequestMethod.GET)
    public String getPage(Model model, Principal principal) {

        Optional<UserEntity> user = userRepository.getByLogin(principal.getName());
        if (!user.isPresent()) return URI_REDIRECT_TO_LOGIN;

        Optional<String> receptionOfOrderName = receptionOfOrderService.getReceptionOfOrderName();
        List<MaterialColorDto> result;
        if (!receptionOfOrderName.isPresent()) {
            result = Collections.emptyList();
        } else {
            model.addAttribute("receptionName", receptionOfOrderName);
            result = service.get();
        }
        model.addAttribute("colors", result);

        if (false) {
            WebContext context = new WebContext(null, null, null);
            context.setVariable("colors", "");
            context.setVariable("receptionName", "");
        }
        return "glass/color/colors";
    }

    @Autowired
    private MaterialColorRepository repository;
    @Autowired
    private DozerBeanMapper mapper;

    //http://localhost:8080/glass/color
    @RequestMapping(value = "/color", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String create(@ModelAttribute(value = "color") MaterialColorDto color) {

        MaterialColorEntity entity = mapper.map(color, MaterialColorEntity.class);
        entity.setCompany(getCompany());
        repository.save(entity);
        color.setId(entity.getId());
        return URI_REDIRECT_TO_COLORS;
    }

    //http://localhost:8080/glass/color/3
    @RequestMapping(value = "/color/{colorId}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String update(@PathVariable(value = "colorId") Long colorId,
                         @ModelAttribute(value = "color") MaterialColorDto color) {

        MaterialColorEntity entity = mapper.map(color, MaterialColorEntity.class);
        entity.setCompany(getCompany());
        entity.setId(colorId);
        repository.save(entity);
        color.setId(entity.getId());
        return URI_REDIRECT_TO_COLORS;
    }

    //http://localhost:8080/glass/color/3/delete
    @RequestMapping(value = "/color/{colorId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(value = "colorId") Long colorId) {

        repository.delete(colorId);
        return URI_REDIRECT_TO_COLORS;
    }

    //http://localhost:8080/glass/color/3
    @RequestMapping(value = "color/{id}", method = RequestMethod.GET)
    public String getPage(Model model, @PathVariable(value = "id") Long id) {

        Optional<MaterialColorDto> dto = service.get(id);

        model.addAttribute("color", dto.get());

        if (false) {
            WebContext context = new WebContext(null, null, null);
            context.setVariable("color", "");
        }
        return "glass/color/color";
    }

}
