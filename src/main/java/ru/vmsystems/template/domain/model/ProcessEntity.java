package ru.vmsystems.template.domain.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "process_process", schema = "main")
public class ProcessEntity {
    private Long id;
    private Integer depth;
    private String price;
    private String desc;
    private Timestamp lastUpdate;
    private ProcessTypeEntity type;
    private CompanyEntity company;
    private List<MaterialEntity> material;

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
    @Column(name = "last_updated")
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @ManyToOne
    public ProcessTypeEntity getType() {
        return type;
    }

    public void setType(ProcessTypeEntity type) {
        this.type = type;
    }

    @ManyToOne
    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PROCESS_PROCESS_MATERIAL_TYPE",
            joinColumns = @JoinColumn(name = "PROCESS_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "MATERIALTYPE_ID", referencedColumnName = "ID"))
    public List<MaterialEntity> getMaterial() {
        return material;
    }

    public void setMaterial(List<MaterialEntity> material) {
        this.material = material;
    }
}
