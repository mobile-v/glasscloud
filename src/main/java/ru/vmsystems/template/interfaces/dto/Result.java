package ru.vmsystems.template.interfaces.dto;

public class Result {
    private String status;

    public Result() {
    }

    public Result(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
