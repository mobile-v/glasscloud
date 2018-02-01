package ru.vmsystems.template.interfaces.page;

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
import ru.vmsystems.template.domain.service.MaterialTypeService;
import ru.vmsystems.template.interfaces.dto.MaterialTypeDto;

import static ru.vmsystems.template.interfaces.page.URLS.PAGE_REDIRECT_TO_MATERIAL_TYPES;

@Controller
@RequestMapping("glass/material/type")
public class MaterialTypeController extends BackService {

    private static final Logger LOG = LoggerFactory.getLogger(MaterialTypeController.class);

    private static final String PAGE = "glass/material/type/types";
    private static final String PAGE_REDIRECT = PAGE_REDIRECT_TO_MATERIAL_TYPES;

    @Autowired
    private MaterialTypeService service;

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

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String create(@ModelAttribute(value = "type") MaterialTypeDto dto) {
        service.create(dto);
        return PAGE_REDIRECT;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String update(@PathVariable(value = "id") Long id,
                         @ModelAttribute(value = "type") MaterialTypeDto dto) {
        service.update(id, dto);
        return PAGE_REDIRECT;
    }

    @RequestMapping(value = "/{colorId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(value = "colorId") Long id) {
        service.delete(id);
        return PAGE_REDIRECT;
    }
}
