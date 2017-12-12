package ru.vmsystems.template.interfaces.page;

/**
 * <p>Краткое описание</p>
 * <p>Подробное описание</p>
 *
 * @author Vladimir Minikh
 *         Created on 13.04.2015.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.domain.shared.Role;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserRepository userRepository;

//    @RequestMapping({"/"})
//    public String redirectToIndex() {
//        return "glass/index";
//    }

    @RequestMapping({"/admin"})
    public String redirectToAdmin() {
        String redirect = "redirect:/logout";

        String login = httpServletRequest.getRemoteUser();
        Optional<UserEntity> userEntity = userRepository.getByLogin(login);
        if (!userEntity.isPresent()) return redirect;

        if (LOG.isDebugEnabled()) {
            LOG.debug("{}. {}, userRole {}");
        }

        switch (Role.valueOf(userEntity.get().getRole())){
            case ROLE_ADMIN:
                redirect = "redirect:/admin";
                break;
        }
        return redirect;
    }
}
