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
import ru.vmsystems.template.infrastructure.persistence.ReceptionOfOrderRepository;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.OrderDto;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Controller
@RequestMapping("glass")
public class OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @NotNull
    private final OrderService orderService;
    @NotNull
    private final ReceptionOfOrderRepository receptionOfOrderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderController(@NotNull OrderService orderService,
                           @NotNull ReceptionOfOrderRepository receptionOfOrderRepository,
                           @NotNull UserRepository userRepository) {
        this.orderService = orderService;
        this.receptionOfOrderRepository = receptionOfOrderRepository;
        this.userRepository = userRepository;
    }

    //http://localhost:8080/glass/orders/receptionOfOrder/1
    @RequestMapping(value="/orders/receptionOfOrder/{receptionOfOrder}", method= RequestMethod.GET)
    public String getPageOrders(Model model, Principal principal,
                                @PathVariable(value = "receptionOfOrder") Long receptionOfOrder) {

        Optional<UserEntity> user = userRepository.getByLogin(principal.getName());
        if (!user.isPresent())  return "glass/order/orders";

        Optional<ReceptionOfOrderEntity> receptionOfOrderEntity = ofNullable(receptionOfOrderRepository
                .findOne(receptionOfOrder))
                .filter(e -> e.getCompany().getId().equals(user.get().getCompany().getId()));

        List<OrderDto> result;
        if (!receptionOfOrderEntity.isPresent()) {
            result = Collections.emptyList();
        } else {
            result = orderService.getOrdersByReceptionOfOrder(receptionOfOrder);
        }


        model.addAttribute("orders", result);

        if (false) {
            WebContext context = new WebContext(null, null, null);
            context.setVariable("orders", result);
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
