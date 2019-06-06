package com.axpresslogistics.it2.axpresslogisticapp.activities.basic_module;

import android.os.AsyncTask;
import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.BuildConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GetVersionCode extends AsyncTask<Void, String, String> {
    private float currentVersion;
    @Override
    protected String doInBackground(Void... voids) {

        String newVersion = null;

        try {
            Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=com.axpresslogistics.it2.axpresslogisticapp&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
            if (document != null) {
                Elements element = document.getElementsContainingOwnText("Current Version");
                for (Element ele : element) {
                    if (ele.siblingElements() != null) {
                        Elements sibElemets = ele.siblingElements();
                        for (Element sibElemet : sibElemets) {
                            newVersion = sibElemet.text();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newVersion;

    }


    @Override

    protected void onPostExecute(String onlineVersion) {
        super.onPostExecute(onlineVersion);
        String update = "New Update "+currentVersion+" available on playstore version";
        String appVersion = BuildConfig.VERSION_NAME;
        if (onlineVersion != null && !onlineVersion.isEmpty()) {
            try{
                Log.e("appVersion",appVersion);
                if (Float.valueOf(appVersion) < Float.valueOf(onlineVersion)){
                    Log.e("Update ",update);

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

    }
}

