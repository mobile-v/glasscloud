package ru.vmsystems.template.interfaces.api;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vmsystems.template.domain.service.ReceptionOfOrderService;
import ru.vmsystems.template.interfaces.dto.ReceptionOfOrderDto;

import java.util.List;
import java.util.Optional;

@RestController("ReceptionOfOrderControllerApi")
@RequestMapping("/api/receptionOfOrder")
public class ReceptionOfOrderController {
    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(ReceptionOfOrderController.class);

    @NotNull
    private final ReceptionOfOrderService receptionOfOrderService;

    @Autowired
    public ReceptionOfOrderController(@NotNull ReceptionOfOrderService receptionOfOrderService) {
        this.receptionOfOrderService = receptionOfOrderService;
    }

    //http://localhost:8080/api/receptionOfOrder
    @NotNull
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ReceptionOfOrderDto>> get() {
        List<ReceptionOfOrderDto> result = receptionOfOrderService.get();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/receptionOfOrder/1
    @NotNull
    @RequestMapping(value = "/{receptionOfOrderId}", method = RequestMethod.GET)
    public ResponseEntity<ReceptionOfOrderDto> get(@PathVariable(value = "receptionOfOrderId") Long receptionOfOrderId) {

        Optional<ReceptionOfOrderDto> order = receptionOfOrderService.get(receptionOfOrderId);
        return order.map(dto ->
                new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() ->
                        new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    //http://localhost:8080/api/receptionOfOrder/
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ReceptionOfOrderDto> save(@RequestBody ReceptionOfOrderDto receptionOfOrder) {
        receptionOfOrderService.save(receptionOfOrder);
        return new ResponseEntity<>(receptionOfOrder, HttpStatus.OK);
    }

//    //http://localhost:8080/api/receptionOfOrder/1/
//    @RequestMapping(value = "/{receptionOfOrderId}", method = RequestMethod.DELETE)
//    public ResponseEntity<Result> delete(@PathVariable(value = "receptionOfOrderId") Long receptionOfOrderId) {
//        receptionOfOrderService.delete(receptionOfOrderId);
//        return new ResponseEntity<>(new Result("OK"), HttpStatus.OK);
//    }
}
