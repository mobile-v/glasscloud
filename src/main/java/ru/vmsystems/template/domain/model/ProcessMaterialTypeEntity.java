package ru.vmsystems.template.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "process_process_material_type", schema = "main", catalog = "")
public class ProcessMaterialTypeEntity {
    private Long id;
    private Short processId;
    private Short materialtypeId;

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
    @Column(name = "process_id")
    public Short getProcessId() {
        return processId;
    }

    public void setProcessId(Short processId) {
        this.processId = processId;
    }

    @Basic
    @Column(name = "materialtype_id")
    public Short getMaterialtypeId() {
        return materialtypeId;
    }

    public void setMaterialtypeId(Short materialtypeId) {
        this.materialtypeId = materialtypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessMaterialTypeEntity that = (ProcessMaterialTypeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (processId != null ? !processId.equals(that.processId) : that.processId != null) return false;
        if (materialtypeId != null ? !materialtypeId.equals(that.materialtypeId) : that.materialtypeId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (processId != null ? processId.hashCode() : 0);
        result = 31 * result + (materialtypeId != null ? materialtypeId.hashCode() : 0);
        return result;
    }
}
