package com.axpresslogistics.it2.axpresslogisticapp.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFunction {
    String currentDate = "";

    public String currentDateFormate_DDMMMYYYY_hhmmss(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        currentDate = formatter.format(date);
        return currentDate;
    }
}
