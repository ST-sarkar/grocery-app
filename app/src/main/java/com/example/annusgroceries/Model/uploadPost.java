package com.example.annusgroceries.Model;

public class uploadPost {

    private String name ;
    private String desc;
    private String price;
    private String unit;
    private String imgUri;
    private String type;
    private String qty;

    public uploadPost(String name, String des, String price, String unit,String qty, String imgUri, String type) {
        this.name = name;
        this.desc = des;
        this.price = price;
        this.unit = unit;
        this.imgUri = imgUri;
        this.type = type;
        this.qty=qty;
    }

    public uploadPost(String name, String des, String price, String unit,String qty, String imgUri) {
        this.name = name;
        this.desc = des;
        this.price = price;
        this.unit = unit;
        this.imgUri = imgUri;
        this.qty=qty;
    }

    public uploadPost() {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String des) {
        this.desc = des;
    }

    public String  getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setRating(String rating) {
        this.unit = unit;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
