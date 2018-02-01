package ru.vmsystems.template.interfaces.page;

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
import ru.vmsystems.template.domain.service.BackService;
import ru.vmsystems.template.domain.service.MaterialColorService;
import ru.vmsystems.template.interfaces.dto.MaterialColorDto;

import static ru.vmsystems.template.interfaces.page.URLS.PAGE_REDIRECT_TO_MATERIAL_COLORS;

@Controller
@RequestMapping("glass/material/color")
public class MaterialColorController extends BackService {

    private static final Logger LOG = LoggerFactory.getLogger(MaterialColorController.class);

    private static final String PAGE = "glass/material/color/colors";
    private static final String PAGE_REDIRECT = PAGE_REDIRECT_TO_MATERIAL_COLORS;

    @NotNull
    private final MaterialColorService service;

    @Autowired
    public MaterialColorController(@NotNull MaterialColorService service) {
        this.service = service;
    }

    //http://localhost:8080/glass/color
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model) {

        model.addAttribute("receptionName", getReceptionOfOrderName());
        model.addAttribute("dtos", service.get());

        if (false) {
            WebContext context = new WebContext(null, null, null);
            context.setVariable("dtos", "");
            context.setVariable("receptionName", "");
        }
        return PAGE;
    }

    //http://localhost:8080/glass/color
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String create(@ModelAttribute(value = "color") MaterialColorDto color) {
        service.create(color);
        return PAGE_REDIRECT;
    }

    //http://localhost:8080/glass/color/3
    @RequestMapping(value = "/{colorId}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String update(@PathVariable(value = "colorId") Long id,
                         @ModelAttribute(value = "color") MaterialColorDto dto) {
        service.update(id, dto);
        return PAGE_REDIRECT;
    }

    //http://localhost:8080/glass/color/3/delete
    @RequestMapping(value = "/{colorId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(value = "colorId") Long id) {
        service.delete(id);
        return PAGE_REDIRECT;
    }

//    //http://localhost:8080/glass/color/3
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public String getPage(Model model, @PathVariable(value = "id") Long id) {
//
//        Optional<MaterialColorDto> dto = service.get(id);
//
//        model.addAttribute("color", dto.get());
//
//        if (false) {
//            WebContext context = new WebContext(null, null, null);
//            context.setVariable("color", "");
//        }
//        return "glass/color/color";
//    }
}
