package com.example.it2.axpresslogisticapp.Utilities;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateConvertor  {
    String month,str_month,currentMonthINTEXT, currentYear;
    public void getCurrentMonth() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM");
        SimpleDateFormat cm = new SimpleDateFormat("MMM");
        SimpleDateFormat cy = new SimpleDateFormat("yyyy");
        month = df.format(c);
        if (Integer.parseInt(df.format(c)) < 10) {
            str_month = df.format(c).replace("0", "");
        } else {
            str_month = df.format(c);
        }
        currentMonthINTEXT = cm.format(c);
        currentYear = cy.format(c);

    }

    public String convertDate_dd_MMM_yyyy(String date){
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.getDefault());
        Date date1 = null;
        String finaldate;
        try {
            date1 = inputFormat.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        finaldate = outputFormat.format(date1);
        if (date1 != null) {
            Log.e("Date1 : ",date1.toString());
        }
        Log.e("FinalOutput : ",finaldate);

        return finaldate;
    }

}
