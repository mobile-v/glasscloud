package ru.vmsystems.template.interfaces.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/glass")
public class GlassController {
    private static final Logger LOG = LoggerFactory.getLogger(GlassController.class);
    @Autowired
    private HttpServletRequest httpServletRequest;

    //http://localhost:8080/glass/index.html
    @RequestMapping(value="/index.html", method= RequestMethod.GET)
    public String glassIndex() {
        return "glass/index";
    }

    //http://localhost:8080/glass/order.html
    @RequestMapping(value="/order.html", method= RequestMethod.GET)
    public String glassOrder() {
        return "glass/order";
    }

    //http://localhost:8080/glass/orders.html
    @RequestMapping(value="/orders.html", method= RequestMethod.GET)
    public String glassOrders() {
        return "glass/orders";
    }
}
