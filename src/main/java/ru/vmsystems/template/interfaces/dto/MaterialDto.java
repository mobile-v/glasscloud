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
    private MaterialColorDto color;
    private MaterialTypeDto type;
}
