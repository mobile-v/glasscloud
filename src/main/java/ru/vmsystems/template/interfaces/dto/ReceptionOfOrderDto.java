package ru.vmsystems.template.interfaces.dto;

public class ReceptionOfOrderDto {
    private Long id;
    private String name;
    private String orderNumPrefix;
    private String desc;
    private String address;
    private Short companyId;
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNumPrefix() {
        return orderNumPrefix;
    }

    public void setOrderNumPrefix(String orderNumPrefix) {
        this.orderNumPrefix = orderNumPrefix;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Short getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Short companyId) {
        this.companyId = companyId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
