package ru.vmsystems.template.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "company_receptionoforder", schema = "main", catalog = "")
public class CompanyReceptionoforderEntity {
    private Long id;
    private String name;
    private String orderNumPrefix;
    private String desc;
    private String address;
    private CompanyCompanyEntity company;
    private String phone;

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
    @Column(name = "order_num_prefix")
    public String getOrderNumPrefix() {
        return orderNumPrefix;
    }

    public void setOrderNumPrefix(String orderNumPrefix) {
        this.orderNumPrefix = orderNumPrefix;
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
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToOne
    @JoinColumn(name = "company_id")
    public CompanyCompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyCompanyEntity company) {
        this.company = company;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
