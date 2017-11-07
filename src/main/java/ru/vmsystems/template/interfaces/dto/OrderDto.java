package ru.vmsystems.template.interfaces.dto;

import java.util.List;

public class OrderDto {
    private Long id;
    private String number;
    private String desc;
    private String accountNumber;
    private String discount;
    private String discountSum;
    private Short count;
    private String summa;
    private Double area;
    private Double perimeter;
    private Short clientId;
    private Short receptionOfOrderId;
    private Short clientTypeId;
    private String creationDate;
    private String updateDate;

    private List<OrderItemDto> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountSum() {
        return discountSum;
    }

    public void setDiscountSum(String discountSum) {
        this.discountSum = discountSum;
    }

    public Short getCount() {
        return count;
    }

    public void setCount(Short count) {
        this.count = count;
    }

    public String getSumma() {
        return summa;
    }

    public void setSumma(String summa) {
        this.summa = summa;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(Double perimeter) {
        this.perimeter = perimeter;
    }

    public Short getClientId() {
        return clientId;
    }

    public void setClientId(Short clientId) {
        this.clientId = clientId;
    }

    public Short getReceptionOfOrderId() {
        return receptionOfOrderId;
    }

    public void setReceptionOfOrderId(Short receptionOfOrderId) {
        this.receptionOfOrderId = receptionOfOrderId;
    }

    public Short getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(Short clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
