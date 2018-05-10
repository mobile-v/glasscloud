package ru.vmsystems.template.interfaces.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("glass")
public class GlassController {
    private static final Logger LOG = LoggerFactory.getLogger(GlassController.class);
    @Autowired
    private HttpServletRequest httpServletRequest;

    //http://localhost:8080/glass/orders/index.html
    //todo для реакта раскоментировать
//    @RequestMapping(value={"", "/", "/auth", "/order", "/orders"}, method= RequestMethod.GET)
    @RequestMapping(value={"/order"}, method= RequestMethod.GET)
    public String glassOrders() {
        return "glass/index";
    }
}
