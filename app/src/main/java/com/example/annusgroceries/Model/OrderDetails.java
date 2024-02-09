package com.example.annusgroceries.Model;

public class OrderDetails {
    String orderDate,orderTime,prodname,prodDesc,prodPrice,prodQty,prodUnit,prodImgUrl,userName,userPhone,userAddr;

    public OrderDetails(String orderDate, String orderTime, String prodname, String prodDesc, String prodPrice, String prodQty, String prodUnit, String prodImgUrl,String userName,String userPhone,String userAddr) {
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.prodname = prodname;
        this.prodDesc = prodDesc;
        this.prodPrice = prodPrice;
        this.prodQty = prodQty;
        this.prodUnit = prodUnit;
        this.prodImgUrl = prodImgUrl;
        this.userAddr=userAddr;
        this.userPhone=userPhone;
        this.userName=userName;
    }

    public OrderDetails() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdQty() {
        return prodQty;
    }

    public void setProdQty(String prodQty) {
        this.prodQty = prodQty;
    }

    public String getProdUnit() {
        return prodUnit;
    }

    public void setProdUnit(String prodUnit) {
        this.prodUnit = prodUnit;
    }

    public String getProdImgUrl() {
        return prodImgUrl;
    }

    public void setProdImgUrl(String prodImgUrl) {
        this.prodImgUrl = prodImgUrl;
    }
}
