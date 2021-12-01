package com.codewithpratik.meatchop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Constant {

    private String userNumber;
    public static final String userKey = "User";
    public static final String isLogIney = "isLogIn";
    private String isLogIn ;
    private String isLogOut ;

    public Constant() {
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getIsLogIn() {
        return isLogIn;
    }

    public void setIsLogIn(String isLogIn) {
        this.isLogIn = isLogIn;
    }

    public String getIsLogOut() {
        return isLogOut;
    }

    public void setIsLogOut(String isLogOut) {
        this.isLogOut = isLogOut;
    }

    public String dateTime()
    {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss z");

        Date date = new Date();

        formatDate.setTimeZone(TimeZone.getTimeZone("IST"));

        return formatDate.format(date);

    }



}
