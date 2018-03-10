package ru.vmsystems.template.interfaces.dto;

import lombok.Data;

@Data
public class MaterialDto {
    private Long id;
    private Integer depth;
    private Double length;
    private Double width;
    private String price;
    private String desc;
    private String name;
    private MaterialColorDto color;
    private MaterialTypeDto type;

    public void setName() {
        name = String.join(" ", type.getName(), color.getName(), String.valueOf(depth), " мм");
    }
}
