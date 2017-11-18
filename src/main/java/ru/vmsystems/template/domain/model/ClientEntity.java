package ru.vmsystems.template.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "client_client", schema = "main", catalog = "")
public class ClientEntity {
    private Long id;
    private String name;
    private Short inn;
    private String account;
    private String phone;
    private String email;
    private String desc;
    private String discount;
    private ClientTypeEntity clientType;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "inn")
    public Short getInn() {
        return inn;
    }

    public void setInn(Short inn) {
        this.inn = inn;
    }

    @Basic
    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Basic
    @Column(name = "discount")
    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @ManyToOne
    @JoinColumn(name="client_type_id")
    public ClientTypeEntity getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeEntity clientType) {
        this.clientType = clientType;
    }
}
