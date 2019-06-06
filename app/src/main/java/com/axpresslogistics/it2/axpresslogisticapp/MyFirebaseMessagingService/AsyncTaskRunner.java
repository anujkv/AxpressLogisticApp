package com.axpresslogistics.it2.axpresslogisticapp.MyFirebaseMessagingService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.AlarmClock;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AsyncTaskRunner extends AsyncTask<String, String, String> {

    Context context;
    public AsyncTaskRunner(Context context) {
        this.context = context;
    }

//    NewToDo newToDoInstance = new NewToDo();

    @Override
    protected String doInBackground(String... strings) {
        String title, body;
        if(strings[0] != null && strings[1]!=null){
            title = strings[0];
            body = strings[1];
//            compareDate( title,  body);
//            newToDoInstance.scheduleAlarm(title,body);
            scheduleAlarm(title,body);
        }
        return null;
    }

    private void scheduleAlarm(String title, String body) {
        Date alarmDate = null;
        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm", Locale.US);
        try {

            alarmDate = formatter.parse(body);
            int hours = alarmDate.getHours();
            int min = alarmDate.getMinutes();
            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_HOUR, hours);
            intent.putExtra(AlarmClock.EXTRA_MINUTES, min);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE,title);
            Log.e("ALARM", hours + ":"+min);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


//    private void compareDate(String date_time, String note) {
//        Log.e("Alarm Date",date_time);//12/19/2018 10:13:00 AM
//        Date c = Calendar.getInstance().getTime();
//        Date currentDate = null;
//        Date alarmDate = null;
//        Date date = null;
//        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.US);
//        try {
//            currentDate = formatter.parse(String.valueOf(c));
//            alarmDate = formatter.parse(date_time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if(currentDate!=null && date_time!=null){
//            if(alarmDate.compareTo(currentDate) >=0){
//
//            }
//        }
//
//        try {
//            date = (Date)formatter.parse(date_time);
//            int year = date.getYear();
//            year = year -1;
//            int month = date.getMonth();
//            int monthoftheday = date.getDay();
//            int hours = date.getHours();
//            int min = date.getMinutes();
//            int days = date.getDay();
//
//            Log.e("Alarm Date", String.valueOf(date));
//            scheduleAlarm(note,year,month,monthoftheday,hours,min,days);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void scheduleAlarm(String note, int year, int month, int monthoftheday, int hours, int mins, int days) {
//        Context context = null;
//        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
//        intent.putExtra(AlarmClock.EXTRA_HOUR, hours);
//        intent.putExtra(AlarmClock.EXTRA_MINUTES, mins);
//        intent.putExtra(AlarmClock.EXTRA_DAYS,days);
//        intent.putExtra(AlarmClock.EXTRA_MESSAGE,note);
//        Log.e("ALARM", hours + ":"+mins + " "+days);
//        context.startActivity(intent);
////        AlarmManager objAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
////        Calendar objCalendar = Calendar.getInstance();
////        objCalendar.set(Calendar.YEAR, year);
//////        objCalendar.set(Calendar.YEAR, objCalendar.get(Calendar.YEAR));
////        objCalendar.set(Calendar.MONTH, month);
////        objCalendar.set(Calendar.DAY_OF_MONTH, monthoftheday);
////        objCalendar.set(Calendar.HOUR_OF_DAY, hours);
////        objCalendar.set(Calendar.MINUTE, mins);
////        objCalendar.set(Calendar.SECOND, 0);
////        objCalendar.set(Calendar.MILLISECOND, 0);
//////        objCalendar.set(Calendar.AM_PM, Calendar.AM);
////
////        Intent alamShowIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
////        alamShowIntent.putExtra(AlarmClock.EXTRA_MESSAGE,listModel.getNote());
////        PendingIntent alarmPendingIntent = PendingIntent.getActivity(context, 0,alamShowIntent,0 );
////        objAlarmManager.set(AlarmManager.RTC_WAKEUP,objCalendar.getTimeInMillis(), alarmPendingIntent);
//    }
}
