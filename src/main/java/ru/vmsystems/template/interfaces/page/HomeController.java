package ru.vmsystems.template.interfaces.page;

/**
 * <p>Краткое описание</p>
 * <p>Подробное описание</p>
 *
 * @author Vladimir Minikh
 * Created on 13.04.2015.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.context.WebContext;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.domain.service.ReceptionOfOrderService;
import ru.vmsystems.template.domain.service.SessionService;
import ru.vmsystems.template.domain.shared.Role;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.ReceptionOfOrderDto;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static ru.vmsystems.template.interfaces.page.URLS.*;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private HttpServletRequest httpRequest;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private ReceptionOfOrderService receptionOfOrderService;

    @RequestMapping({"/"})
    public String redirectToIndex() {
        Optional<Long> reception = sessionService.getReceptionOfOrder();
        if (reception.isPresent()) {
            return PAGE_REDIRECT_TO_ORDERS;
        } else {
            return PAGE_REDIRECT_TO_SELECT_RECEPTION;
        }
    }

    @RequestMapping({"/glass/login-reception-of-order"})
    public String getReceptionOfOrder(Model model, Principal principal) {

        Optional<Long> reception = sessionService.getReceptionOfOrder();
        if (reception.isPresent()) {
            return PAGE_REDIRECT_TO_ORDERS;
        }

        List<ReceptionOfOrderDto> result = receptionOfOrderService.getByUserName(principal.getName());

        model.addAttribute("receptions", result);
        ReceptionOfOrderDto receptionOfOrderDto = new ReceptionOfOrderDto();
        model.addAttribute("receptionOfOrder", receptionOfOrderDto);

        if (false) {
            WebContext context = new WebContext(null, null, null);
            context.setVariable("receptions", result);
            context.setVariable("receptionOfOrder", receptionOfOrderDto);
        }

        return PAGE_SELECT_RECEPTION;
    }

    @RequestMapping(value = "/glass/login-reception-of-order", method = RequestMethod.POST)
    public String selectReceptionOfOrder(@ModelAttribute ReceptionOfOrderDto receptionOfOrder) {

        sessionService.setReceptionOfOrder(receptionOfOrder.getId());

        return PAGE_REDIRECT_TO_ORDERS;
    }

    @RequestMapping({"/admin"})
    public String redirectToAdmin() {
        String redirect = PAGE_REDIRECT_TO_LOGOUT;

        String login = httpRequest.getRemoteUser();
        Optional<UserEntity> userEntity = userRepository.getByLogin(login);
        if (!userEntity.isPresent()) return redirect;

        if (LOG.isDebugEnabled()) {
            LOG.debug("{}. {}, userRole {}");
        }

        switch (Role.valueOf(userEntity.get().getRole())) {
            case ROLE_ADMIN:
                redirect = PAGE_REDIRECT_TO_ADMIN;
                break;
        }
        return redirect;
    }
}
