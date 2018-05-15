package ru.vmsystems.template.interfaces.api;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/is_auth")
public class AuthController {
    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    //http://localhost:8080/api/is_auth
    @NotNull
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Boolean> isAuth() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
