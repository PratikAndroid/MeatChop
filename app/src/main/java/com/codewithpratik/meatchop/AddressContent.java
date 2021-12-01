package com.codewithpratik.meatchop;

public class AddressContent {

    private String keyName ;
    private String keyPhoneNumber ;
    private String keyAlternatePhoneNumber ;
    private String keyPinCode ;
    private String keyState ;
    private String keyCity ;
    private String keyHouseNo ;
    private String keyRoadName ;
    private String keyLandMark ;

    public AddressContent() {
    }

    public AddressContent(String keyName, String keyPhoneNumber, String keyAlternatePhoneNumber, String keyPinCode, String keyState,
                          String keyCity, String keyHouseNo, String keyRoadName, String keyLandMark) {
        this.keyName = keyName;
        this.keyPhoneNumber = keyPhoneNumber;
        this.keyAlternatePhoneNumber = keyAlternatePhoneNumber;
        this.keyPinCode = keyPinCode;
        this.keyState = keyState;
        this.keyCity = keyCity;
        this.keyHouseNo = keyHouseNo;
        this.keyRoadName = keyRoadName;
        this.keyLandMark = keyLandMark;
    }

    @Override
    public String toString() {
        return "AddressContent{" +
                "keyName='" + keyName + '\'' +
                ", keyPhoneNumber='" + keyPhoneNumber + '\'' +
                ", keyPinCode='" + keyPinCode + '\'' +
                ", keyState='" + keyState + '\'' +
                ", keyCity='" + keyCity + '\'' +
                ", keyHouseNo='" + keyHouseNo + '\'' +
                ", keyRoadName='" + keyRoadName + '\'' +
                '}';
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyPhoneNumber() {
        return keyPhoneNumber;
    }

    public void setKeyPhoneNumber(String keyPhoneNumber) {
        this.keyPhoneNumber = keyPhoneNumber;
    }

    public String getKeyAlternatePhoneNumber() {
        return keyAlternatePhoneNumber;
    }

    public void setKeyAlternatePhoneNumber(String keyAlternatePhoneNumber) {
        this.keyAlternatePhoneNumber = keyAlternatePhoneNumber;
    }

    public String getKeyPinCode() {
        return keyPinCode;
    }

    public void setKeyPinCode(String keyPinCode) {
        this.keyPinCode = keyPinCode;
    }

    public String getKeyState() {
        return keyState;
    }

    public void setKeyState(String keyState) {
        this.keyState = keyState;
    }

    public String getKeyCity() {
        return keyCity;
    }

    public void setKeyCity(String keyCity) {
        this.keyCity = keyCity;
    }

    public String getKeyHouseNo() {
        return keyHouseNo;
    }

    public void setKeyHouseNo(String keyHouseNo) {
        this.keyHouseNo = keyHouseNo;
    }

    public String getKeyRoadName() {
        return keyRoadName;
    }

    public void setKeyRoadName(String keyRoadName) {
        this.keyRoadName = keyRoadName;
    }

    public String getKeyLandMark() {
        return keyLandMark;
    }

    public void setKeyLandMark(String keyLandMark) {
        this.keyLandMark = keyLandMark;
    }
}
