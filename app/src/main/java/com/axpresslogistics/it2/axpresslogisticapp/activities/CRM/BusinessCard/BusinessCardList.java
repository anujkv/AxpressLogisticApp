package com.axpresslogistics.it2.axpresslogisticapp.activities.CRM.BusinessCard;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.CRMAdaptor.BusinessCardListAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.CRMAdaptor.SearchBusinessCardAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.BusinessCardListResponse.BusinessCard;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.BusinessCardListResponse.BusinessCardListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.BusinessCardListResponse.SearchBusinessCardM;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.BusinessCardListResponse.SearchBusinessCardResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.businessCard.BusinessCardList.BusinessCardListPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.businessCard.BusinessCardList.SearchBusinessListPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CameraUtils;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.BusinessCardView.BusinessCardListView;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.BusinessCardView.SearchBusinessCardListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BusinessCardList extends AppCompatActivity implements View.OnClickListener,
        BusinessCardListView, SearchBusinessCardListView {
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int GALLERY_REQUEST = 1;
    private static final int QRCODE_REQUEST = 2;
    ImageButton backbtn_toolbar, refreshbtn_toolbar;
    ImageView imageView_camera, imageView_gallery, imageview_qrcode;
    android.app.AlertDialog.Builder builder;
    android.app.AlertDialog dialog;
    String created_by;
    List<BusinessCard> businessCards;
    BusinessCardListAdaptor cardListAdaptor;
    SearchBusinessCardAdaptor searchBusinessCardAdaptor;
    List<SearchBusinessCardM> searchbusinessCards;
    RecyclerView recyclerView,search_recyclerView;
    TextView title_toolbar;
    EditText searchedt_toolbar;
    ImageButton searchbtn_toolbar;
    MainPresenter presenter;
    String input;
    String key = "";
    ProgressDialog progressDialogCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_card_list);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setText(CONSTANT.BUSINESS_CARD);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        searchbtn_toolbar = findViewById(R.id.searchbtn_toolbar);
        searchedt_toolbar = findViewById(R.id.searchedt_toolbar);
        refreshbtn_toolbar = findViewById(R.id.newbtn_toolbar);
        refreshbtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_refresh));
        backbtn_toolbar.setOnClickListener(this);
        refreshbtn_toolbar.setOnClickListener(this);
        searchbtn_toolbar.setOnClickListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),BusinessCardDetails.class));
                showDialogBox();
            }
        });
        created_by = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
        recyclerView = findViewById(R.id.cardRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        businessCards = new ArrayList<>();

//        listOfCards();
        ApiKey apiKey = new ApiKey();
        key = apiKey.saltStr();
        search_recyclerView = findViewById(R.id.cardRecyclerView);
        search_recyclerView.setHasFixedSize(true);
        search_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchbusinessCards = new ArrayList<>();
//        buisnessCard();

        if(businessCards!=null){
            if (businessCards.size() > 0) {
                businessCards.clear();
            } else {
                buisnessCard();
            }
        }
    }

    private void showDialogBox() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.card_options, null);
        imageView_camera = alertLayout.findViewById(R.id.imageview_camera);
        imageview_qrcode = alertLayout.findViewById(R.id.imageview_qrcode);

        builder = new android.app.AlertDialog.Builder(alertLayout.getContext());
        builder.setTitle("CHOOSE IMAGE FROM");
        builder.setView(alertLayout);
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
            }
        });

//        checkEmptyFields();
        dialog = builder.create();
        dialog.show();
        dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        ((android.app.AlertDialog) dialog).getButton(android.app.AlertDialog.BUTTON_POSITIVE).setEnabled(true);

//----------------------------------CAMERA----------------------------------------------------------
        imageView_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!CameraUtils.isDeviceSupportCamera(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Your device doesn't support camera",
                            Toast.LENGTH_LONG).show();
                    // will close the app if the device doesn't have camera
                    finish();
                }else{
                    //TODO add camera functionality...
//                    Toast.makeText(getApplicationContext(), "camera", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BusinessCardList.this, BusinessCardDetails.class);
                    intent.putExtra("key",203);
                    startActivity(intent);
                }
            }
        });
//----------------------------------QR CODE---------------------------------------------------------
        imageview_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //TODO add gallery functionality...
                Toast.makeText(getApplicationContext(), "qrcode", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BusinessCardList.this, BusinessCardDetails.class);
                intent.putExtra("key",QRCODE_REQUEST);
                startActivity(intent);

            }
        });

    }

    private void buisnessCard() {
        presenter = new BusinessCardListPresenterImpl(this);
        presenter.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.newbtn_toolbar:
                refresh();
                break;
            case R.id.searchbtn_toolbar:
                title_toolbar.setVisibility(View.GONE);
                searchedt_toolbar.setVisibility(View.VISIBLE);
                searchedt_toolbar.setTextColor(Color.WHITE);
                searchedt_toolbar.setFocusable(true);
                searchedt_toolbar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        searchbusinessCards.clear();
                        businessCards.clear();
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Log.e("ATCs : ", s.toString());
                        input = s.toString();
                        searchBuisnessCard();

                    }
                });
                break;

        }
    }

    private void searchBuisnessCard() {
        presenter = new SearchBusinessListPresenterImpl(this);
        presenter.init();
    }

    private void refresh() {
        if(businessCards !=null){
            if (businessCards.size() > 0) {
                businessCards.clear();
                buisnessCard();
                // listOfCards();
            }
        }

    }

    @Override
    public String getCreatedBy() {
        return Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID);
    }

    @Override
    public String getEmplid() {
        return Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID);
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
    public void showLoadingLayout() {
        progressDialogCall = new ProgressDialog(BusinessCardList.this) {
            @Override
            public void onBackPressed() {
                progressDialogCall.dismiss();
                finish();
            }};
        progressDialogCall.setMessage("Data Sync, wait for few moment!");
        progressDialogCall.setCancelable(false);

        progressDialogCall.show();

    }

    @Override
    public void hideLoadingLayout() {
        progressDialogCall.dismiss();
    }

    @Override
    public void showSuccess(Object object) {
        if (object instanceof BusinessCardListResponse) {
            BusinessCardListResponse response = (BusinessCardListResponse) object;
            Log.e("Response",new Gson().toJson(response));
            if (response != null && response.getStatus().equals(CONSTANT.TRUE)) {
                businessCards.addAll(response.getBusinessCard());
                cardListAdaptor = new BusinessCardListAdaptor(getApplicationContext(), businessCards);
                recyclerView.setAdapter(cardListAdaptor);
            } else {
                Toast.makeText(getApplicationContext(), "Business Card not is not found!",
                        Toast.LENGTH_SHORT).show();
            }
        }else if (object instanceof SearchBusinessCardResponse) {
            SearchBusinessCardResponse response = (SearchBusinessCardResponse) object;
            if (response != null && response.getStatus().equals(CONSTANT.TRUE)) {
                searchbusinessCards.addAll(response.getBusinessCard());
                businessCards.clear();
                searchBusinessCardAdaptor = new SearchBusinessCardAdaptor(getApplicationContext(), searchbusinessCards);
                search_recyclerView.setAdapter(searchBusinessCardAdaptor);
            } else {
                Toast.makeText(getApplicationContext(), "Business Card not is not found!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(), CONSTANT.server_not_reachable,Toast.LENGTH_SHORT).show();
        progressDialogCall.dismiss();
    }
}
