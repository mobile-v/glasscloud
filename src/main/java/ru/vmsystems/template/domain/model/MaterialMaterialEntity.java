package ru.vmsystems.template.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "material_material", schema = "main", catalog = "")
public class MaterialMaterialEntity {
    private Short id;
    private Short depth;
    private Double length;
    private Double width;
    private String price;
    private String desc;
    private Short colorId;
    private Short typeId;
    private Short companyId;

    @Id
    @Column(name = "id")
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "depth")
    public Short getDepth() {
        return depth;
    }

    public void setDepth(Short depth) {
        this.depth = depth;
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
    @Column(name = "price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
    @Column(name = "color_id")
    public Short getColorId() {
        return colorId;
    }

    public void setColorId(Short colorId) {
        this.colorId = colorId;
    }

    @Basic
    @Column(name = "type_id")
    public Short getTypeId() {
        return typeId;
    }

    public void setTypeId(Short typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "company_id")
    public Short getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Short companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MaterialMaterialEntity that = (MaterialMaterialEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (depth != null ? !depth.equals(that.depth) : that.depth != null) return false;
        if (length != null ? !length.equals(that.length) : that.length != null) return false;
        if (width != null ? !width.equals(that.width) : that.width != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        if (colorId != null ? !colorId.equals(that.colorId) : that.colorId != null) return false;
        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (depth != null ? depth.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (colorId != null ? colorId.hashCode() : 0);
        result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }
}
