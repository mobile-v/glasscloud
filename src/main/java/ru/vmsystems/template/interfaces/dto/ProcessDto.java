package ru.vmsystems.template.interfaces.dto;

import java.util.List;

public class ProcessDto {
    private Long id;
    private Integer depth;
    private String price;
    private String desc;
    private ProcessTypeDto type;
    private List<MaterialDto> material;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ProcessTypeDto getType() {
        return type;
    }

    public void setType(ProcessTypeDto type) {
        this.type = type;
    }

    public List<MaterialDto> getMaterial() {
        return material;
    }

    public void setMaterial(List<MaterialDto> material) {
        this.material = material;
    }
}
