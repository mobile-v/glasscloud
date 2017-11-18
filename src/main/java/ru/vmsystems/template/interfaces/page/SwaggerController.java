package ru.vmsystems.template.interfaces.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
//@RequestMapping(value="/admin")
public class SwaggerController {
    private static final Logger LOG = LoggerFactory.getLogger(SwaggerController.class);
    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value="/swagger-ui.html", method= RequestMethod.GET)
    public String getPageAdmin() {
        return "swagger/swagger-ui";
    }
}
