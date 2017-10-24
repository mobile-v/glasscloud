package ru.vmsystems.template.interfaces.api;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.vmsystems.template.domain.model.OrderOrderEntity;
import ru.vmsystems.template.infrastructure.persistence.OrderRepository;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public final class OrderController {
    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @NotNull
    @Autowired
    private OrderRepository orderRepository;

    //http://localhost:8080/order/
    @NotNull
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<OrderOrderEntity>> getProducts() {

        List<OrderOrderEntity> result = Lists.newArrayList(orderRepository.findAll());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
