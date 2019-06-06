package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.DocketEnquiry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.DocketEnquriy.DocketResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.InvoiceResponse.InvoiceResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.DocketPresenter.DocketPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.InvoicePresenter.InvoicePresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.LoadingLayout;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Docket_InvoiceView.DocketEnquiryView;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Docket_InvoiceView.InvoiceEnquiryView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT.URL;

public class DocketEnquiry extends AppCompatActivity implements View.OnClickListener, DocketEnquiryView,
        InvoiceEnquiryView {
    private String get_company_url = URL + "Operations/Get_Company?EMPLID=";
    RadioGroup radio_group_id;
    RadioButton radio_btn_id,docketRB,invoiceRB;
    EditText input_editSearch_text_id;
    Button submit_docket_btn;
    String method, strInput_editSearch_text, docket, db_name;
    ProgressBar progressBar;
    Spinner spinner;
    String[] newArray;
    String emplid = "";
    String apikey;
    String requestType;
    ProgressDialog progressDialog;
    MainPresenter  presenter;
    ApiKey apiKey = new ApiKey();
    LoadingLayout loadingLayout = new LoadingLayout();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docket_enquiry);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        progressBar = findViewById(R.id.progressBar);
        lable.setText(CONSTANT.DOCKET_INVOICE);
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        emplid = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
        docketRB = findViewById(R.id.docketRB);
        invoiceRB = findViewById(R.id.invoiceRB);
        docketRB.setOnClickListener(this);
        invoiceRB.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        get_company();
        //initialize_data_types..
        initialize_dataType();
        radio_group_id.clearCheck();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getSelectedItem().toString().contains("Logistics")){
                    db_name = "AxpressERP";
                }else if(spinner.getSelectedItem().toString().contains("SCM")){
                    db_name = "ASCM";
                }else if(spinner.getSelectedItem().toString().contains("CARGO")){
                    db_name = "AxpressCargo";
                }else{
                    db_name = spinner.getSelectedItem().toString();
                }
                Log.e("DB_NAME", db_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit_docket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radio_group_id.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radio_btn_id = findViewById(selectedId);
                strInput_editSearch_text = input_editSearch_text_id.getText().toString().trim();

                if (radio_btn_id == null) {
                    Toast.makeText(getApplicationContext(), CONSTANT.CHOOSE_THE_SEARCH_TYPE,
                            Toast.LENGTH_SHORT).show();

                } else if (strInput_editSearch_text.isEmpty() || strInput_editSearch_text.equals("")) {
                    Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_DOCKET_INVOICE_NO,
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (radio_btn_id.getText().equals("Docket")) {
                        method = CONSTANT.DOCKET;
                        requestType = "D";
                        dataJsonFunction();
                    } else{
                        method = CONSTANT.INVOICE_DETAILS_METHOD;
                        requestType = "I";
                        dataJsonFunctionInvoice();
                    }
                }
            }
        });
    }

    private void dataJsonFunctionInvoice() {
        submit_docket_btn.setClickable(false);
        ApiKey apiKey = new ApiKey();
        apikey = apiKey.saltStr();
        presenter = new InvoicePresenterImpl(this);
        presenter.init();
    }

    private void get_company() {
        Log.e("URL", get_company_url + emplid);
        String GET_COM_URL = get_company_url + emplid;
        final String[] a = new String[]{"Axpress Logistics", "Axpress SCM", "Axpress Cargo", "Axpress Transaction"};
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_COM_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Request", response);

                String stringArray = response;
                newArray = stringArray.replace("[", "")
                        .replace("]", "")
                        .replace("\"", "")
                        .split(",");
                List<String> companey_list = new ArrayList<>();
                List<String> list = new ArrayList<>();
                list.addAll(Arrays.asList(newArray));
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).toUpperCase().contains("ERP"))
                            companey_list.add("Axpress Logistics");
                        else if (list.get(i).toUpperCase().contains("SCM"))
                            companey_list.add("Axpress SCM");
                        else if (list.get(i).toUpperCase().contains("CARGO"))
                            companey_list.add("Axpress CARGO");
                        else{
                            companey_list.add(list.get(i));
                        }
                        for (int l = 0; l < companey_list.size(); l++) {
                            if (companey_list.get(l).equals("Axpress Logistics")) {
                                companey_list.remove(l);
                                companey_list.add(0, "Axpress Logistics");
                            }
                        }

                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DocketEnquiry.this,
                        android.R.layout.simple_spinner_item, companey_list);
                Preferences.setPreference(getApplicationContext(), CONSTANT.COM_LIST, new Gson().toJson(list));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Log.e("LIST", Preferences.getPreference(getApplicationContext(), CONSTANT.COM_LIST));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Log.e("NetworkError", CONSTANT.INTERNET_ERROR);
                } else if (error instanceof TimeoutError) {
                    Log.e("TimeoutError", CONSTANT.TIMEOUT_ERROR);
                } else if (error instanceof ServerError) {
                    Log.e("ServerError", CONSTANT.RESPONSEERROR);
                } else if (error instanceof ParseError) {
                    Log.e("ParseError", CONSTANT.TIMEOUT_ERROR);
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void dataJsonFunction() {
        submit_docket_btn.setClickable(false);
        presenter = new DocketPresenterImpl(this);
        presenter.init();

    }

    private void initialize_dataType() {
        radio_group_id = findViewById(R.id.radio_group_id);
        input_editSearch_text_id = findViewById((R.id.input_editSearch_text_id));
        submit_docket_btn = findViewById(R.id.btn_search);
        spinner = findViewById(R.id.company_spinner);
    }

    @Override
    protected void onPostResume() {
        submit_docket_btn.setClickable(true);
        progressBar.setVisibility(View.INVISIBLE);
        input_editSearch_text_id.setText("");
        super.onPostResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.docketRB:
                invoiceRB.setChecked(false);
                break;

            case R.id.invoiceRB:
                docketRB.setChecked(false);
                break;
        }
    }

    @Override
    public String getKey() {
        return apiKey.saltStr();
    }

    @Override
    public String getInvoiceNo() {
        return input_editSearch_text_id.getText().toString();
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getInput() {
        return input_editSearch_text_id.getText().toString();
    }

    @Override
    public String getDBName() {
        return db_name;
    }

    @Override
    public void showLoadingLayout() {
        loadingLayout.showLoadingLayout(this);
    }

    @Override
    public void hideLoadingLayout() {
        loadingLayout.hideLoadingLayout();
    }

    @Override
    public void showSuccess(Object object) {
        if (object instanceof DocketResponse) {
            DocketResponse response = (DocketResponse) object;
            Log.e("Response", new Gson().toJson(response));
            if (response.getStatus().equals(CONSTANT.TRUE)) {
                Intent intent = new Intent(DocketEnquiry.this, DocketTracking.class);
                Bundle b = new Bundle();
                b.putString("docketEnquiryResponse",strInput_editSearch_text);
                b.putString("method","docket");
                b.putString("db_name",response.getDbName());
                intent.putExtras(b);
                startActivity(intent);
            }

            else if (response.getStatus().equals(CONSTANT.FALSE)) {
                Toast.makeText(getApplicationContext(), "Docket" + CONSTANT.NOT_FOUND,
                        Toast.LENGTH_SHORT).show();
                submit_docket_btn.setClickable(true);

            } else {
                Toast.makeText(getApplicationContext(), "DOCKET_NOT_CORRECT_OR_DATABASE_NOT_CORRECT",
                        Toast.LENGTH_SHORT).show();
                submit_docket_btn.setClickable(true);
            }
        }
        else if (object instanceof InvoiceResponse){
            InvoiceResponse invoiceResponse = (InvoiceResponse) object;
            Log.e("InvoiceResponse",new Gson().toJson(invoiceResponse));
            if (invoiceResponse.getStatus().equals(CONSTANT.TRUE)) {

                Intent intent = new Intent(DocketEnquiry.this, InvoiceList.class);
                Bundle b = new Bundle();
//                b.putParcelable("invoiceEnquiryResponse",  invoiceResponse);
                b.putString("invoice_no",strInput_editSearch_text);
                b.putString("db_name",db_name);
                intent.putExtras(b);
                startActivity(intent);
            } else if (invoiceResponse.getStatus().equals(CONSTANT.FALSE)) {
                Toast.makeText(getApplicationContext(), "Invoice" + CONSTANT.NOT_FOUND,
                        Toast.LENGTH_SHORT).show();
                submit_docket_btn.setClickable(true);

            } else {
                Toast.makeText(getApplicationContext(), "DOCKET_NOT_CORRECT_OR_DATABASE_NOT_CORRECT",
                        Toast.LENGTH_SHORT).show();
                submit_docket_btn.setClickable(true);
            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(),CONSTANT.server_error,Toast.LENGTH_SHORT).show();
        loadingLayout.hideLoadingLayout();

    }
}
