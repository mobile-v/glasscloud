package ru.vmsystems.template.domain.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "order_order", schema = "main")
public class OrderEntity {
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
    private ClientEntity client;
    private ReceptionOfOrderEntity receptionOfOrder;
    private Timestamp creationDate;
    private Timestamp updateDate;

    private List<OrderItemEntity> items;

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
    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
    @Column(name = "account_number")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Basic
    @Column(name = "discount")
    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "discount_sum")
    public String getDiscountSum() {
        return discountSum;
    }

    public void setDiscountSum(String discountSum) {
        this.discountSum = discountSum;
    }

    @Basic
    @Column(name = "count")
    public Short getCount() {
        return count;
    }

    public void setCount(Short count) {
        this.count = count;
    }

    @Basic
    @Column(name = "summa")
    public String getSumma() {
        return summa;
    }

    public void setSumma(String summa) {
        this.summa = summa;
    }

    @Basic
    @Column(name = "area")
    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    @Basic
    @Column(name = "perimeter")
    public Double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(Double perimeter) {
        this.perimeter = perimeter;
    }

    @ManyToOne
    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    @ManyToOne
    public ReceptionOfOrderEntity getReceptionOfOrder() {
        return receptionOfOrder;
    }

    public void setReceptionOfOrder(ReceptionOfOrderEntity receptionOfOrder) {
        this.receptionOfOrder = receptionOfOrder;
    }

    @Basic
    @Column(name = "creation_date")
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "last_updated")
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<OrderItemEntity> getItems() {
        return items;
    }

    public void setItems(List<OrderItemEntity> items) {
        this.items = items;
    }

}
