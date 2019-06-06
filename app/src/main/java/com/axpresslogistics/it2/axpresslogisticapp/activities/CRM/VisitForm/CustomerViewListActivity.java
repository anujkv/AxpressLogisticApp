package com.axpresslogistics.it2.axpresslogisticapp.activities.CRM.VisitForm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.CRMAdaptor.SearchInputFormListAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.CRMAdaptor.VisitFormListAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.visitform.SavedList;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.visitform.SearchVisitFormList;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.visitform.SearchVisitFormResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.visitform.VisitFormListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.visitform.SearchVisitFormListPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.visitform.VisitFormListPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.VisitFormView.VisitFormListSearchView;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.VisitFormView.VisitFormListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CustomerViewListActivity extends AppCompatActivity implements View.OnClickListener, VisitFormListView,
        VisitFormListSearchView {
    public static final int NEW_VISITFORM_REQUEST_CODE = 200;
    String emplid;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.title_toolbar)
    TextView title_toolbar;
    ArrayList<String> list = new ArrayList<String>();

    EditText searchedt_toolbar;
    ImageButton backbtn_toolbar, addbtn_toolbar, searchbtn_toolbar;
    //    @BindView(R.id.refresh_image)
    ImageView refresh_image;

    List<SavedList> savedLists;
    VisitFormListAdaptor adapter;
    //
    List<SearchVisitFormList> inputListModelList;
    SearchInputFormListAdaptor setAdapter;

    LinearLayout no_data_availableLayout;
    ApiKey apiKey = new ApiKey();
    String key = apiKey.saltStr();
    MainPresenter presenter;

    String input;

    @BindView(R.id.visitlist_recyclerview)
    RecyclerView recyclerView;
    //    @BindView(R.id.visitlist_recyclerview)
    RecyclerView search_recyclerView;
    @BindView(R.id.no_list_layout)
    LinearLayout emptylayout;
    ProgressDialog progressDialog;

    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_list);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setText("Visit Form");
        floatingActionButton = findViewById(R.id.fab);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        searchbtn_toolbar = findViewById(R.id.searchbtn_toolbar);
        searchedt_toolbar = findViewById(R.id.searchedt_toolbar);
        addbtn_toolbar = findViewById(R.id.newbtn_toolbar);
        addbtn_toolbar.setVisibility(View.GONE);
        no_data_availableLayout = findViewById(R.id.no_list_layout);
        backbtn_toolbar.setOnClickListener(this);
        addbtn_toolbar.setOnClickListener(this);
        searchbtn_toolbar.setOnClickListener(this);

        recyclerView = findViewById(R.id.visitlist_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        savedLists = new ArrayList<>();

        search_recyclerView = findViewById(R.id.visitlist_recyclerview);
        search_recyclerView.setHasFixedSize(true);
        search_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        inputListModelList = new ArrayList<>();
//        presenter = new VisitFormListPresenterImpl(this);
//        presenter.init();
        if (savedLists.size() > 0) {
            savedLists.clear();
        } else {
            visitFormList();
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(CustomerViewListActivity.this,
                        NewVisitForm.class), NEW_VISITFORM_REQUEST_CODE);
            }
        });
    }

    private void visitFormList() {
        presenter = new VisitFormListPresenterImpl(this);
        presenter.init();

    }

    private void searchVisitFormList() {
        presenter = new SearchVisitFormListPresenterImpl(this);
        presenter.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.newbtn_toolbar:
                startActivity(new Intent(getApplicationContext(), NewVisitForm.class));
                break;
            case R.id.searchbtn_toolbar:
                title_toolbar.setVisibility(View.GONE);
                searchedt_toolbar.setVisibility(View.VISIBLE);
                searchedt_toolbar.setTextColor(Color.WHITE);
                searchedt_toolbar.setFocusable(true);
                searchedt_toolbar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        inputListModelList.clear();
                        savedLists.clear();
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Log.e("ATCs : ", s.toString());
                        input = s.toString();
                        //  hitSearchAPi(s.toString());
                        searchVisitFormList();
                    }
                });
                break;
            case R.id.refresh_image:
                refresh_page();
                break;
        }
    }


    private void refresh_page() {
        Animation animation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(2000);
        ((ImageView) findViewById(R.id.refresh_image)).setAnimation(animation);
        final String method = "leave_info";
        try {
            if (inputListModelList.size() > 0) {
                inputListModelList.clear();
                savedLists.clear();
                visitFormList();
                // showVisitFormList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getEmplId() {
        return Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
    }

    @Override
    public String getInput() {
        return input;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getDBName() {
        return Preferences.getPreference(CustomerViewListActivity.this,CONSTANT.DB_NAME);
    }

    @Override
    public void showLoadingLayout() {
//        progressDialog = new ProgressDialog(CustomerViewListActivity.this) {
//            @Override
//            public void onBackPressed() {
//                progressDialog.dismiss();
//                finish();
//            }};
//        progressDialog.setMessage("Loading");
//        progressDialog.setCancelable(false);
//
//        progressDialog.show();

    }

    @Override
    public void hideLoadingLayout() {
//        progressDialog.show();
    }

    @Override
    public void showSuccess(Object object) {
        if(object instanceof VisitFormListResponse){
            VisitFormListResponse response = (VisitFormListResponse)object;
            if(response!=null && response.getStatus().equals(CONSTANT.TRUE)){
                recyclerView.setVisibility(View.VISIBLE);
                no_data_availableLayout.setVisibility(View.GONE);
                savedLists.addAll(response.getSavedList());
                adapter = new VisitFormListAdaptor(getApplicationContext(),savedLists);
                recyclerView.setAdapter(adapter);
            }
            else {
                no_data_availableLayout.setVisibility(View.VISIBLE);
            }
        }
        else if(object instanceof SearchVisitFormResponse){
            SearchVisitFormResponse response = (SearchVisitFormResponse)object;
            if(response!=null && response.getStatus().equals(CONSTANT.TRUE)){
                inputListModelList.addAll(response.getSearch());
                setAdapter = new SearchInputFormListAdaptor(getApplicationContext(), inputListModelList);
                search_recyclerView.setAdapter(setAdapter);}

            else {
                no_data_availableLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(), "server not reachable", Toast.LENGTH_SHORT).show();
//        progressDialog.dismiss();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        refresh();
    }

    private void refresh() {
        searchedt_toolbar.setText("");
        title_toolbar.setVisibility(View.VISIBLE);
        searchedt_toolbar.setVisibility(View.GONE);
        if (inputListModelList.size() > 0) {
            inputListModelList.clear();
            savedLists.clear();
            visitFormList();
            // showVisitFormList();
        }
    }
}
