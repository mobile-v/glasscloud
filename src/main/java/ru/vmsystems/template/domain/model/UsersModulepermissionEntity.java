package ru.vmsystems.template.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "users_modulepermission", schema = "main", catalog = "")
public class UsersModulepermissionEntity {
    private Short id;
    private String isView;
    private String isEdit;
    private Short moduleId;
    private Short userId;
    private String desc;

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
    @Column(name = "is_view")
    public String getIsView() {
        return isView;
    }

    public void setIsView(String isView) {
        this.isView = isView;
    }

    @Basic
    @Column(name = "is_edit")
    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    @Basic
    @Column(name = "module_id")
    public Short getModuleId() {
        return moduleId;
    }

    public void setModuleId(Short moduleId) {
        this.moduleId = moduleId;
    }

    @Basic
    @Column(name = "user_id")
    public Short getUserId() {
        return userId;
    }

    public void setUserId(Short userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersModulepermissionEntity that = (UsersModulepermissionEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (isView != null ? !isView.equals(that.isView) : that.isView != null) return false;
        if (isEdit != null ? !isEdit.equals(that.isEdit) : that.isEdit != null) return false;
        if (moduleId != null ? !moduleId.equals(that.moduleId) : that.moduleId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (isView != null ? isView.hashCode() : 0);
        result = 31 * result + (isEdit != null ? isEdit.hashCode() : 0);
        result = 31 * result + (moduleId != null ? moduleId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        return result;
    }
}
