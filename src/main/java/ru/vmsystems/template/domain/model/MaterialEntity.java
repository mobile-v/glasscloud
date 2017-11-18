package ru.vmsystems.template.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "material_material", schema = "main")
public class MaterialEntity {
    private Long id;
    private Integer depth;
    private Double length;
    private Double width;
    private String price;
    private String desc;
    private MaterialColorEntity color;
    private MaterialTypeEntity type;
    private CompanyEntity company;

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
    @Column(name = "depth")
    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
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

    @ManyToOne
    public MaterialColorEntity getColor() {
        return color;
    }

    public void setColor(MaterialColorEntity color) {
        this.color = color;
    }

    @ManyToOne
    public MaterialTypeEntity getType() {
        return type;
    }

    public void setType(MaterialTypeEntity type) {
        this.type = type;
    }

    @ManyToOne
    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }
}
