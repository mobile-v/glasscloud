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
import ru.vmsystems.template.domain.shared.Role;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.ReceptionOfOrderDto;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
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
    @Autowired
    private ReceptionOfOrderService receptionOfOrderService;

    @RequestMapping({"/"})
    public String redirectToIndex() {
        return "glass/order/orders";
    }

    @RequestMapping({"/glass/login-reception-of-order"})
    public String getReceptionOfOrder(Model model, Principal principal) {

        List<ReceptionOfOrderDto> result = receptionOfOrderService.getByUserName(principal.getName());

        model.addAttribute("receptions", result);
        ReceptionOfOrderDto receptionOfOrderDto = new ReceptionOfOrderDto();
        model.addAttribute("receptionOfOrder", receptionOfOrderDto);

        if (false) {
            WebContext context = new WebContext(null, null, null);
            context.setVariable("receptions", result);
            context.setVariable("receptionOfOrder", receptionOfOrderDto);
        }

        return "glass/login-reception-of-order";
    }

    @RequestMapping(value = "/glass/login-reception-of-order", method = RequestMethod.POST)
    public String selectReceptionOfOrder(@ModelAttribute ReceptionOfOrderDto receptionOfOrder, Principal principal) {

        return "redirect:/glass/orders/receptionOfOrder/" + receptionOfOrder.getId();
    }

    @RequestMapping({"/admin"})
    public String redirectToAdmin() {
        String redirect = "redirect:/logout";

        String login = httpServletRequest.getRemoteUser();
        Optional<UserEntity> userEntity = userRepository.getByLogin(login);
        if (!userEntity.isPresent()) return redirect;

        if (LOG.isDebugEnabled()) {
            LOG.debug("{}. {}, userRole {}");
        }

        switch (Role.valueOf(userEntity.get().getRole())) {
            case ROLE_ADMIN:
                redirect = "redirect:/admin";
                break;
        }
        return redirect;
    }
}
