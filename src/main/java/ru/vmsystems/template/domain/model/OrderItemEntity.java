package ru.vmsystems.template.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "order_item", schema = "main", catalog = "")
public class OrderItemEntity {
    private Short id;
    private String desc;
    private String number;
    private Double length;
    private Double width;
    private Short count;
    private Double area;
    private Double perimeter;
    private String processSum;
    private String summa;
    private Short orderId;
    private Short materialId;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
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

    @Basic
    @Column(name = "order_id")
    public Short getOrderId() {
        return orderId;
    }

    public void setOrderId(Short orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "material_id")
    public Short getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Short materialId) {
        this.materialId = materialId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItemEntity that = (OrderItemEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (length != null ? !length.equals(that.length) : that.length != null) return false;
        if (width != null ? !width.equals(that.width) : that.width != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (perimeter != null ? !perimeter.equals(that.perimeter) : that.perimeter != null) return false;
        if (processSum != null ? !processSum.equals(that.processSum) : that.processSum != null) return false;
        if (summa != null ? !summa.equals(that.summa) : that.summa != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (materialId != null ? !materialId.equals(that.materialId) : that.materialId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (perimeter != null ? perimeter.hashCode() : 0);
        result = 31 * result + (processSum != null ? processSum.hashCode() : 0);
        result = 31 * result + (summa != null ? summa.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (materialId != null ? materialId.hashCode() : 0);
        return result;
    }
}
