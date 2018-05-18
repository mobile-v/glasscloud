package ru.vmsystems.template.interfaces.dto;

import lombok.Data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialDto that = (MaterialDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(depth, that.depth) &&
                Objects.equals(length, that.length) &&
                Objects.equals(width, that.width) &&
                Objects.equals(price, that.price) &&
                Objects.equals(desc, that.desc) &&
                Objects.equals(name, that.name) &&
                Objects.equals(color, that.color) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, depth, length, width, price, desc, name, color, type);
    }
}
