package ru.vmsystems.template.interfaces.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class OrderDto {
    private Long id;
    @ApiModelProperty(notes = "Номер заказа", required = true)
    private String number;
    @ApiModelProperty(notes = "Описание")
    private String desc;
    @ApiModelProperty(notes = "---- Номер счета. скорее всего не нужен ---")
    private String accountNumber;
    @ApiModelProperty(notes = "Скидка", required = true)
    private String discount;
    @ApiModelProperty(notes = "Сумма скидки", required = true)
    private String discountSum;
    @ApiModelProperty(notes = "Количество", required = true)
    private Short count;
    @ApiModelProperty(notes = "Сумма", required = true)
    private String summa;
    @ApiModelProperty(notes = "Площадь", required = true)
    private Double area;
    @ApiModelProperty(notes = "Периметр", required = true)
    private Double perimeter;
    @ApiModelProperty(notes = "Заказчик", required = true)
    private ClientDto client;
    @ApiModelProperty(notes = "Точка приема заказов", required = true)
    private ReceptionOfOrderDto receptionOfOrder;
    @ApiModelProperty(notes = "Дата создания")
    private String creationDate;
    @ApiModelProperty(notes = "Дата обновления")
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

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public ReceptionOfOrderDto getReceptionOfOrder() {
        return receptionOfOrder;
    }

    public void setReceptionOfOrder(ReceptionOfOrderDto receptionOfOrder) {
        this.receptionOfOrder = receptionOfOrder;
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
