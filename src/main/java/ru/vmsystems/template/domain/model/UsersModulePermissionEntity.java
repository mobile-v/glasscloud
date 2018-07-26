package ru.vmsystems.template.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "users_modulepermission", schema = "main", catalog = "")
public class UsersModulePermissionEntity {
    private Long id;
    private String isView;
    private String isEdit;
    private Short moduleId;
    private Short userId;
    private String desc;

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
}
