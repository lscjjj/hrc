package com.cebrains.hrc.common.persistence.vo;

public class ConsumableSuggestVo {
    private Integer id;
    private String name;
    private String number;
    private String specification;
    private Double price;
    private String measureUnit;
    private Integer available;

    public ConsumableSuggestVo(Integer id, String name, String number, String specification, Double price, String measureUnit, Integer available) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.specification = specification;
        this.price = price;
        this.measureUnit = measureUnit;
        this.available = available;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
