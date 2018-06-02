package com.example.it2.axpresslogisticapp.acitvities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LeaveInfoActivity extends AppCompatActivity {

    ArrayList<String> datafetchlist = new ArrayList<>();
    EditText input_editText;
    Button search_btn;
    String input_text = "asdfsdfsdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_info);

        input_editText  = findViewById(R.id.input_edit_text_id);
        search_btn = findViewById(R.id.btn_search);


        input_text = input_editText.getText().toString();
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_data();
                Toast.makeText(getApplicationContext(),"search is "+input_editText.getText().toString(),Toast.LENGTH_SHORT).show();
                input_editText.setText("");
            }
        });
    }

    public void get_data() {
        String json;

        try {
            InputStream inputStream = getAssets().open("employeedetails.json");
            int size = inputStream.available();
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            inputStream.close();

            json = new String(bytes,"UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for(int i = 0; i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                Toast.makeText(getApplicationContext(),object.getString("name"),Toast.LENGTH_SHORT).show();

//                if (object.getString("name").equals("input_text")){
//                    datafetchlist.add(object.getString("empid"));
//                }
            }
            Toast.makeText(getApplicationContext(),datafetchlist.toString(),Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
