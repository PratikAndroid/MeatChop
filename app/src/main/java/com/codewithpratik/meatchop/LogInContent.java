package com.codewithpratik.meatchop;

public class LogInContent {



    private String mobileNumber;
    private String email;
    private String pin;

    public LogInContent() {
    }

    public LogInContent(String mobileNumber, String email, String pin) {
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.pin = pin;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
