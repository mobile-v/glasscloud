package ru.vmsystems.template.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "order_order", schema = "main")
public class OrderOrderEntity {
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

//    private List<OrderItemEntity> items;

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

    @Basic
    @Column(name = "client_id")
    public Short getClientId() {
        return clientId;
    }

    public void setClientId(Short clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "receptionOfOrder_id")
    public Short getReceptionOfOrderId() {
        return receptionOfOrderId;
    }

    public void setReceptionOfOrderId(Short receptionOfOrderId) {
        this.receptionOfOrderId = receptionOfOrderId;
    }

    @Basic
    @Column(name = "client_type_id")
    public Short getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(Short clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

//    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
//    public List<OrderItemEntity> getItems() {
//        return items;
//    }
//
//    public void setItems(List<OrderItemEntity> items) {
//        this.items = items;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderOrderEntity that = (OrderOrderEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (discount != null ? !discount.equals(that.discount) : that.discount != null) return false;
        if (discountSum != null ? !discountSum.equals(that.discountSum) : that.discountSum != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (summa != null ? !summa.equals(that.summa) : that.summa != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (perimeter != null ? !perimeter.equals(that.perimeter) : that.perimeter != null) return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (receptionOfOrderId != null ? !receptionOfOrderId.equals(that.receptionOfOrderId) : that.receptionOfOrderId != null)
            return false;
        if (clientTypeId != null ? !clientTypeId.equals(that.clientTypeId) : that.clientTypeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (discountSum != null ? discountSum.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (summa != null ? summa.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (perimeter != null ? perimeter.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (receptionOfOrderId != null ? receptionOfOrderId.hashCode() : 0);
        result = 31 * result + (clientTypeId != null ? clientTypeId.hashCode() : 0);
        return result;
    }
}
