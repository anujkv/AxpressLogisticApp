package com.axpresslogistics.it2.axpresslogisticapp.MyFirebaseInstanceServices;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceServices extends FirebaseMessagingService {
    private static final String REG_TOKEN = "REG_TOKEN";

//    @Override
//    public void onTokenRefresh() {
//        String value_method = "refreshed_token";
//        String refresh_token = FirebaseInstanceId.getInstance().getToken();
//        try{
////            Toast.makeText(this,"Refresh_toker"+refresh_token,Toast.LENGTH_SHORT).show();
//            Preferences.setPreference(MyFirebaseInstanceServices.this, CONSTANT.METHOD, value_method);
//
//            if (refresh_token!=null || !refresh_token.equals("")) {
//                Preferences.setPreference(MyFirebaseInstanceServices.this, CONSTANT.REFRESHED_TOKEN,
//                        refresh_token);
//            }
//            Log.e("Refresh_toker",refresh_token);
//            Log.d(REG_TOKEN,refresh_token);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String value_method = "refreshed_token";
        String refresh_token = FirebaseInstanceId.getInstance().getToken();
        try{
//            Toast.makeText(this,"Refresh_toker"+refresh_token,Toast.LENGTH_SHORT).show();
            Preferences.setPreference(MyFirebaseInstanceServices.this, CONSTANT.METHOD, value_method);

            if (refresh_token!=null || !refresh_token.equals("")) {
                Preferences.setPreference(MyFirebaseInstanceServices.this, CONSTANT.REFRESHED_TOKEN,
                        refresh_token);
            }
            Log.e("Refresh_toker",refresh_token);
            Log.d(REG_TOKEN,refresh_token);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
