package com.codewithpratik.meatchop;

public class VendorContent {

    private String address_vendor;
    private MyOrderContent myOrderContent_vendor;

    public VendorContent() {
    }

    public String getAddress_vendor() {
        return address_vendor;
    }

    public void setAddress_vendor(String address_vendor) {
        this.address_vendor = address_vendor;
    }

    public MyOrderContent getMyOrderContent_vendor() {
        return myOrderContent_vendor;
    }

    public void setMyOrderContent_vendor(MyOrderContent myOrderContent_vendor) {
        this.myOrderContent_vendor = myOrderContent_vendor;
    }
}
