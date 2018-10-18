package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.axpresslogistics.it2.axpresslogisticapp.BuildConfig;
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;

public class Version extends AppCompatActivity {
    TextView version_name,application_publish,app_all_rights;
    String appVersion,publisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);

        appVersion = BuildConfig.VERSION_NAME;
        Log.e("VERSION",appVersion);
        version_name = findViewById(R.id.application_version);

        version_name.setText("Version "+appVersion);
        application_publish = findViewById(R.id.application_publish);
        publisher = BuildConfig.APPLICATION_ID;

    }
}
