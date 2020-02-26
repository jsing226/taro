package com.taro.sampleservice.models;


public class Product {
    private Integer id;
    private String name;
    private Integer version;
    private Integer quantity;

    public Product(){}
    public Product(Integer id, String name, Integer version, Integer quantity) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.quantity = quantity;
    }

    public Product(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
