package ru.vmsystems.template.domain.model;

import ru.vmsystems.template.domain.model.user.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "users_module", schema = "main", catalog = "")
public class UsersModuleEntity {
    private Long id;
    private String name;
    private String desc;
    private String isTitle;
    private Short number;
    private UserEntity user;

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

    @Basic
    @Column(name = "desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Basic
    @Column(name = "is_title")
    public String getIsTitle() {
        return isTitle;
    }

    public void setIsTitle(String isTitle) {
        this.isTitle = isTitle;
    }

    @Basic
    @Column(name = "number")
    public Short getNumber() {
        return number;
    }

    public void setNumber(Short number) {
        this.number = number;
    }
}
