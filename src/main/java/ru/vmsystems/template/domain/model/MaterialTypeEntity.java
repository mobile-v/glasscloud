package ru.vmsystems.template.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "material_materialtype", schema = "main")
public class MaterialTypeEntity {
    private Long id;
    private String name;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }
}
