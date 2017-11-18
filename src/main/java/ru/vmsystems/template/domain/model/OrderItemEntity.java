package ru.vmsystems.template.domain.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "order_item", schema = "main")
public class OrderItemEntity {
    private Long id;
    private String desc;
    private String number;
    private Double length;
    private Double width;
    private Short count;
    private Double area;
    private Double perimeter;
    private String processSum;
    private String summa;
    private OrderEntity order;
    private MaterialEntity material;
    private Timestamp creationDate;
    private Timestamp updateDate;

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
    @Column(name = "desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
    @Column(name = "length")
    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    @Basic
    @Column(name = "width")
    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
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
    @Column(name = "process_sum")
    public String getProcessSum() {
        return processSum;
    }

    public void setProcessSum(String processSum) {
        this.processSum = processSum;
    }

    @Basic
    @Column(name = "summa")
    public String getSumma() {
        return summa;
    }

    public void setSumma(String summa) {
        this.summa = summa;
    }

    @ManyToOne
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity orderId) {
        this.order = orderId;
    }

    @ManyToOne
    public MaterialEntity getMaterial() {
        return material;
    }

    public void setMaterial(MaterialEntity material) {
        this.material = material;
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
}
