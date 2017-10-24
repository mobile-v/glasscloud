package ru.vmsystems.template.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "order_itemprocess", schema = "main", catalog = "")
public class OrderItemprocessEntity {
    private Short id;
    private String summa;
    private String desc;
    private Short itemId;
    private Short processId;

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
    @Column(name = "summa")
    public String getSumma() {
        return summa;
    }

    public void setSumma(String summa) {
        this.summa = summa;
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
    @Column(name = "item_id")
    public Short getItemId() {
        return itemId;
    }

    public void setItemId(Short itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "process_id")
    public Short getProcessId() {
        return processId;
    }

    public void setProcessId(Short processId) {
        this.processId = processId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItemprocessEntity that = (OrderItemprocessEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (summa != null ? !summa.equals(that.summa) : that.summa != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        if (processId != null ? !processId.equals(that.processId) : that.processId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (summa != null ? summa.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (itemId != null ? itemId.hashCode() : 0);
        result = 31 * result + (processId != null ? processId.hashCode() : 0);
        return result;
    }
}
