package ru.vmsystems.template.interfaces.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value="admin", method= RequestMethod.GET)
    public String getPageAdmin(Model model) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("{}. {}", "getPageAdmin()");
        }

        return "admin/admin";
    }
}
