package com.codewithpratik.meatchop;

import java.io.Serializable;

public class CartModel implements Serializable {

    private String productId;
    private String productImg;
    private String productName;
    private String boneType;
    private String productPriceString;
    private String finalPriceString;
    private String finalWeightString;
    private int productPrice;
    private int productPrice1;
    private String productWeight;
    private String productType;
    private float quantity;
    private float quantity1;
    private int tag;
    private int GrandTotal;


    public CartModel() {
    }

    public CartModel(String productId, String productImg, String productName, String boneType, String productPriceString, String finalPriceString, String finalWeightString,
                     int productPrice, int productPrice1, String productWeight, String productType, float quantity, float quantity1, int tag, int grandTotal) {
        this.productId = productId;
        this.productImg = productImg;
        this.productName = productName;
        this.boneType = boneType;
        this.productPriceString = productPriceString;
        this.finalPriceString = finalPriceString;
        this.finalWeightString = finalWeightString;
        this.productPrice = productPrice;
        this.productPrice1 = productPrice1;
        this.productWeight = productWeight;
        this.productType = productType;
        this.quantity = quantity;
        this.quantity1 = quantity1;
        this.tag = tag;
        GrandTotal = grandTotal;
    }

    public int getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(int grandTotal) {
        GrandTotal = grandTotal;
    }

    public String getFinalPriceString() {
        return finalPriceString;
    }

    public void setFinalPriceString(String finalPriceString) {
        this.finalPriceString = finalPriceString;
    }

    public String getFinalWeightString() {
        return finalWeightString;
    }

    public void setFinalWeightString(String finalWeightString) {
        this.finalWeightString = finalWeightString;
    }

    public float getQuantity1() {
        return quantity1;
    }

    public void setQuantity1(float quantity1) {
        this.quantity1 = quantity1;
    }

    public String getProductPriceString() {
        return productPriceString;
    }

    public void setProductPriceString(String productPriceString) {
        this.productPriceString = productPriceString;
    }

    public int getProductPrice1() {
        return productPrice1;
    }

    public void setProductPrice1(int productPrice1) {
        this.productPrice1 = productPrice1;
    }
    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getBoneType() {
        return boneType;
    }

    public void setBoneType(String boneType) {
        this.boneType = boneType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }



}
