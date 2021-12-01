package com.codewithpratik.meatchop;

import java.util.ArrayList;

public class MyOrderContent {

    private String orderId;
    private String totalCost;
    private String status;
    private String date;
    private String time;
    private String deliveryCharges;
    private String paymentType;
    private String quantity;
    private ArrayList<CartModel> items;

    public MyOrderContent() {
    }

    public MyOrderContent(String orderId, String totalCost,
                          String status, String date, String time, String deliveryCharges, String paymentType, String quantity, ArrayList<CartModel> items) {
        this.orderId = orderId;
        this.totalCost = totalCost;
        this.status = status;
        this.date = date;
        this.time = time;
        this.deliveryCharges = deliveryCharges;
        this.paymentType = paymentType;
        this.quantity = quantity;
        this.items = items;
    }

    public ArrayList<CartModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartModel> items) {
        this.items = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }




    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
