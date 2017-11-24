package ru.vmsystems.template.application;

import org.springframework.stereotype.Component;

@Component
public class ModuleSecurity {

    public boolean isSwaggerPermitted() {
        return true;
    }

    public boolean isOrderPermitted() {
        return true;
    }
}
