package ru.vmsystems.template.interfaces.page;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.context.WebContext;
import ru.vmsystems.template.domain.model.ReceptionOfOrderEntity;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.domain.service.OrderService;
import ru.vmsystems.template.domain.service.ReceptionOfOrderService;
import ru.vmsystems.template.infrastructure.persistence.ReceptionOfOrderRepository;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.OrderDto;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static ru.vmsystems.template.interfaces.page.URLS.URI_REDIRECT_TO_LOGIN;
import static ru.vmsystems.template.interfaces.page.URLS.URI_REDIRECT_TO_SELECT_RECEPTION;

@Controller
@RequestMapping("glass")
public class OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private HttpServletRequest httpServletRequest;

    @NotNull
    private final OrderService orderService;
    @NotNull
    private final ReceptionOfOrderRepository receptionOfOrderRepository;
    private final UserRepository userRepository;
    private final ReceptionOfOrderService receptionOfOrderService;

    @Autowired
    public OrderController(@NotNull OrderService orderService,
                           @NotNull ReceptionOfOrderRepository receptionOfOrderRepository,
                           @NotNull UserRepository userRepository,
                           ReceptionOfOrderService receptionOfOrderService) {
        this.orderService = orderService;
        this.receptionOfOrderRepository = receptionOfOrderRepository;
        this.userRepository = userRepository;
        this.receptionOfOrderService = receptionOfOrderService;
    }

    //http://localhost:8080/glass/orders
    @RequestMapping(value="/orders", method= RequestMethod.GET)
    public String getPageOrdersBy(Model model, Principal principal) {

        Optional<Long> receptionOfOrder = receptionOfOrderService.getReceptionOfOrder(httpServletRequest.getSession().getId());
        if (!receptionOfOrder.isPresent()) return URI_REDIRECT_TO_SELECT_RECEPTION;

        Optional<UserEntity> user = userRepository.getByLogin(principal.getName());
        if (!user.isPresent())  return URI_REDIRECT_TO_LOGIN;

        Optional<ReceptionOfOrderEntity> receptionOfOrderEntity = ofNullable(receptionOfOrderRepository
                .findOne(receptionOfOrder.get()))
                .filter(e -> e.getCompany().getId().equals(user.get().getCompany().getId()));

        List<OrderDto> result;
        if (!receptionOfOrderEntity.isPresent()) {
            result = Collections.emptyList();
        } else {
            model.addAttribute("receptionName", receptionOfOrderEntity.get().getName());
            result = orderService.getOrdersByReceptionOfOrder(receptionOfOrder.get());
        }

        model.addAttribute("orders", result);

        if (false) {
            WebContext context = new WebContext(null, null, null);
            context.setVariable("orders", "");
            context.setVariable("receptionName", "");
        }
        return "glass/order/orders";
    }

    //http://localhost:8080/glass/order/3
    @RequestMapping(value="order/{orderId}", method= RequestMethod.GET)
    public String getPageOrder(Model model, @PathVariable(value = "orderId") Long orderId) {

        Optional<OrderDto> order = orderService.getOrder(orderId);

        model.addAttribute("order", order.get());

        if (false) {
            WebContext context = new WebContext(null, null, null);
            context.setVariable("order", order.get());
        }
        return "glass/order/order";
    }

}
