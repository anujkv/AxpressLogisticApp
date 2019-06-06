package com.axpresslogistics.it2.axpresslogisticapp.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.activities.CRM.CRMActivity;
import com.axpresslogistics.it2.axpresslogisticapp.activities.HRMS.HrmsActivity;
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.OperationActivity;
import com.axpresslogistics.it2.axpresslogisticapp.activities.activities_module.ActivitiesActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CheckAccessRole {
    ArrayList<String> list = new ArrayList<>();
    Context context;
    public void check(String call) {
        list = (ArrayList<String>) loadSharedPreferencesLogList(context);
        Log.e("listAdaptor",new Gson().toJson(list));
        if (list.contains(call)) {

            if (call.equals("operations")) {
                Intent intent = new Intent(context, OperationActivity.class);
                intent.putExtra("list", list);
                context.startActivity(intent);
            } else if (call.equals("hrms")) {
                Intent intent = new Intent(context, HrmsActivity.class);
                intent.putExtra("list", list);
                context.startActivity(intent);
            } else if (call.equals("crm")) {
                Intent intent = new Intent(context, CRMActivity.class);
                intent.putExtra("list", list);
                context.startActivity(intent);
            } else if (call.equals("activities")) {
                Intent intent = new Intent(context, ActivitiesActivity.class);
                intent.putExtra("list", list);
                context.startActivity(intent);
            }
        } else {
            Toast.makeText(context, "Sorry, you don't have permission!,contact with IT Department.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static List<String> loadSharedPreferencesLogList(Context context) {
        List<String> list = new ArrayList<String>();
        SharedPreferences mPrefs = context.getSharedPreferences("app_role_list", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("list", "");
        if (json.isEmpty()) {
            list = new ArrayList<String>();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            list = gson.fromJson(json, type);
        }

        return list;
    }
}
