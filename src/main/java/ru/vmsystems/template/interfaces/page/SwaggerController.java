package ru.vmsystems.template.interfaces.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
//@RequestMapping(value="/admin")
public class SwaggerController {
    private static final Logger LOG = LoggerFactory.getLogger(SwaggerController.class);
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    AuthenticationManager authenticationManager;
//    @Autowired
//    AccessDecisionManager accessDecisionManager;

    //http://localhost:8080/swagger-ui.html
    //http://82.202.226.239:8080/swagger-ui.html
    @RequestMapping(value="/swagger-ui.html", method= RequestMethod.GET)
//    @Secured("ROLE_ADMIN")
//    @RolesAllowed("manager")
//    @PreAuthorize("hasRole('ROLE_USER')")
    @PreAuthorize("@moduleSecurity.swaggerPermitted")
    public String getPageSwagger(Principal user) {
        return "swagger/swagger-ui";
    }
}


//UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
