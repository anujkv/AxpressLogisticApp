package com.axpresslogistics.it2.axpresslogisticapp.activities.basic_module;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.BuildConfig;
import com.axpresslogistics.it2.axpresslogisticapp.R;

import org.jsoup.Jsoup;

public class Version extends AppCompatActivity {
    TextView version_name,application_publish,app_all_rights,check_update_link;
    String appVersion,publisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);

        appVersion = BuildConfig.VERSION_NAME;
        Log.e("VERSION",appVersion);
        version_name = findViewById(R.id.application_version);
        check_update_link = findViewById(R.id.check_update_link);

        version_name.setText("Version "+appVersion);
        application_publish = findViewById(R.id.application_publish);

        check_update_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.axpresslogistics.it2.axpresslogisticapp"));
                startActivity(intent);
            }
        });
    }

    private void show_dialogBox_for_update() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Version Available");
        builder.setMessage("Please, update app to new version to continue reposting");
        builder.setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);
            }
        });
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.axpresslogistics.it2.axpresslogisticapp"));
                    startActivity(viewIntent);
                }catch(Exception e) {
                    Toast.makeText(getApplicationContext(),"Unable to Connect Try Again...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        builder.create();
        builder.show();
    }

    private String check_current_version() {
        String appVersion = BuildConfig.VERSION_NAME;
        return appVersion;
    }

    private String check_new_version_on_playstore() {
        String newVersion = null;
        try {
            newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + Version.this.getPackageName() + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select("div[itemprop=softwareVersion]")
                    .first()
                    .ownText();
            Toast.makeText(getApplicationContext(), newVersion,Toast.LENGTH_SHORT).show();
            Log.e("update", newVersion);
            return newVersion;
        } catch (Exception e) {
            return newVersion;
        }
    }
}
