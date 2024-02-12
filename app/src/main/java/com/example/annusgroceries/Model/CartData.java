package com.example.annusgroceries.Model;

public class CartData {
    private String name,desc,price,unit,qty,imgUri;


    public CartData(String name, String desc, String price, String unit, String qty, String imgUri) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.unit = unit;
        this.qty = qty;
        this.imgUri = imgUri;
    }

    public CartData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
}
