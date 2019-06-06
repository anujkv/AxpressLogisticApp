package com.axpresslogistics.it2.axpresslogisticapp.activities.activities_module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.activities.activities_module.NewToDo;

public class AlarmReceiver extends BroadcastReceiver {
    /* Receives scheduled Alarm intents */
    public void onReceive(Context context, Intent intent) {
        /* Show a success toast*/
        Toast.makeText(context, "Howdy partner", Toast.LENGTH_SHORT);
        /* Launch the MainActivity, just for fun */
        context.startActivity(new Intent(context, NewToDo.class));
    }
}
