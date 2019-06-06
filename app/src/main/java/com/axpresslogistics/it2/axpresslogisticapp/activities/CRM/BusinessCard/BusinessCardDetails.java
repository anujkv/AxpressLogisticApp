package com.axpresslogistics.it2.axpresslogisticapp.activities.CRM.BusinessCard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentProviderOperation;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.SlideAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.AddBusinessCardResponse.AddBusinessCardResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.FetchBusinessCardResponse.FetchBusinessCard;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.FetchBusinessCardResponse.FetchBusinessCardResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.UpdateBusinessCardResponse.UpdateBusinessCardResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.businessCard.FetchBusinessCardDetails.FetchBusinessCardPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.businessCard.NewBusinessCard.AddBusinessCardPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.businessCard.UpdateBusinessCard.UpdateBusinessCardPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CameraUtils;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Convertors;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ImageUtils;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.BusinessCardView.AddBusinessCardView;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.BusinessCardView.FetchBusinessCardView;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.BusinessCardView.UpdateBusinessCardDetailsView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessCardDetails extends AppCompatActivity implements View.OnClickListener,
        AddBusinessCardView, UpdateBusinessCardDetailsView, FetchBusinessCardView {
    public static final int CAMERA_REQUEST = 1888;
    public static final int CAMERA_PERMISSION_CODE = 100;
    public static final int GALLERY_REQUEST = 1;

    static final int REQUEST_TAKE_PHOTO = 11;
    public static final int QRCODE_REQUEST = 2;
    public static final int QRCODE_REQUEST_CODE = 49374;
    public static final int PIC_CROP = 400;
    String TAG = "TAG";

    ProgressDialog progressDialogCall;

    //qr code scanner object
    private IntentIntegrator qrScan;
    StringBuilder stringBuilder;
    Bitmap bitmap, finalImageFront;
    LinearLayout nameLayout, orgLayout, mobile1Layout, mobile2Layout, teleLayout, emailLayout, faxLayout,
            webLayout, addLayout;
    CardView email_cardview, fax_cardview, website_cardview, location_cardview;
    byte[] image = null;
    boolean hasImage = false;
    boolean edit_save = false;
    String method, frontimage, backimage;

    private Uri resultUri;

    String strName = "", strJobTitle = "", strMobile = "", strMobile2 = "", strEmail = "", strAddress = "",
            strTel = "", strWeb = "", strCompany = "", strFax = "", rex = "", strQRCode = "", created_by = "";
    EditText editname, edtJobTitle, edtMobile, edtMobile2, edttelephone, edtEmail, edtAddress, edtWebsite,
            edtCompany, edtFax, edtQRCode;
    CardView QRCardCardView, make_call_cardview, send_email_cardview, address_location_cardview;
    TextView txtname;
    TextView txtJobTitle;
    TextView txtMobile, txtMobile2;
    TextView txttelephone;
    TextView txtEmail;
    TextView txtAddress;
    TextView txtWebsite;
    TextView txtCompany;
    TextView txtFax;
    TextView txtQRCode;
    ImageButton editbtn_toolbar;
    public String photoFileName = "photo.jpg";
    String card_id = null;
    ImageView iv;
    String key;
    Bitmap bitmapCroped;
    MainPresenter presenter;
    Convertors convertors = new Convertors();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_card_details);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Business Card");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        editbtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        editbtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_white_24dp));
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        created_by = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
//        editbtn_toolbar.setImageIcon();
        editbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_save == false) {
                    edit_fields_visible();
                    iv.setClickable(true);
                } else {
                    edit_save = true;
                    edit_fields_invisible();
                    call_save_apiRequest();
                    iv.setClickable(false);
                }
            }
        });

        //intializing scan object
        qrScan = new IntentIntegrator(this);
//        viewPager = findViewById(R.id.view_pager);
//        slideAdaptor = new SlideAdaptor(this);
//        slideAdaptor.list_images = {}
//        viewPager.setAdapter(slideAdaptor);
        init();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int id = bundle.getInt("key");
            if(id == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.activity()
                        .start(BusinessCardDetails.this);
            }
            else if (id == (QRCODE_REQUEST)) {
                Log.e("KEY REQUEST : ", String.valueOf(QRCODE_REQUEST));
                qrScan.initiateScan();
            }
        } else {
            Toast.makeText(getApplicationContext(), "key wrong", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (bundle != null) {
            card_id = bundle.getString("id");
            if (card_id != null) {
                setDataOnLayout(card_id);
            }
        }
    }

    private void add_to_card() {
        AlertDialog.Builder add_to_contactDialogBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            add_to_contactDialogBuilder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog);
        } else {
            add_to_contactDialogBuilder = new AlertDialog.Builder(this);
        }
        add_to_contactDialogBuilder.setTitle("Add Contact");
        add_to_contactDialogBuilder.setMessage("Do you want to add in contact!");
        add_to_contactDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addContact();
                call_save_apiRequest();
                finish();
            }
        });
        add_to_contactDialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                call_save_apiRequest();
                finish();
            }
        });
        add_to_contactDialogBuilder.setIcon(android.R.drawable.ic_input_add);
        add_to_contactDialogBuilder.show();
    }

    private void addContact() {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
//        --------------------------------------------------------------------------
        if (strName != null || !strName.equals("")) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            strName).build());
//            ------------------------------------------------------------------------
            if (strMobile != null || !strMobile.equals("")) {
                ops.add(ContentProviderOperation.
                        newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, strMobile)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());
            }
            if (strMobile2 != null || !strMobile2.equals("")) {
                ops.add(ContentProviderOperation.
                        newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, strMobile2)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());
            }
            if (strTel != null || !strTel.equals("")) {
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, strTel)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                        .build());
            }
//            ------------------------------------------------------------------------
            if (strEmail != null || !strEmail.equals("")) {
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Email.DATA, strEmail)
                        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                        .build());
            }
            //--------------------------------------------------------------------------
            if (!strCompany.equals("") && !strJobTitle.equals("")) {
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, strCompany)
                        .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                        .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, strJobTitle)
                        .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                        .build());
            }
        }
    }

    private void setDataOnLayout(final String card_id) {
        presenter = new FetchBusinessCardPresenterImpl(this);
        presenter.init();
    }

    private void call_save_apiRequest() {
        ApiKey apiKey = new ApiKey();
        key = apiKey.saltStr();
        if (card_id != null) {
            method = "business_card_update";
            presenter = new UpdateBusinessCardPresenterImpl(this);
            presenter.init();

        } else {

            method = "business_card_save";
            presenter = new AddBusinessCardPresenterImpl(this);
            presenter.init();
        }
    }

    private void edit_fields_invisible() {
        edit_save = false;
        editbtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_white_24dp));
        editname.setVisibility(View.GONE);
        edtJobTitle.setVisibility(View.GONE);
        edtCompany.setVisibility(View.GONE);
        edtMobile.setVisibility(View.GONE);
        edtMobile2.setVisibility(View.GONE);
        edttelephone.setVisibility(View.GONE);
        edtEmail.setVisibility(View.GONE);
        edtWebsite.setVisibility(View.GONE);
        edtAddress.setVisibility(View.GONE);
        edtFax.setVisibility(View.GONE);
        edtQRCode.setVisibility(View.GONE);
        iv.setClickable(false);

        setData_edit_to_string();
        txtname.setText(strName);
        txtJobTitle.setText(strJobTitle);
        txtCompany.setText(strCompany);
        txtMobile.setText(strMobile);
        txtMobile2.setText(strMobile2);
        txttelephone.setText(strTel);
        txtEmail.setText(strEmail);
        txtWebsite.setText(strWeb);
        txtAddress.setText(strAddress);
        txtFax.setText(strFax);
        txtQRCode.setText(strQRCode);

        txtname.setVisibility(View.VISIBLE);
        txtJobTitle.setVisibility(View.VISIBLE);
        txtCompany.setVisibility(View.VISIBLE);
        txtMobile.setVisibility(View.VISIBLE);
        txtMobile2.setVisibility(View.VISIBLE);
        txttelephone.setVisibility(View.VISIBLE);
        txtEmail.setVisibility(View.VISIBLE);
        txtWebsite.setVisibility(View.VISIBLE);
        txtAddress.setVisibility(View.VISIBLE);
        txtFax.setVisibility(View.VISIBLE);
        txtQRCode.setVisibility(View.VISIBLE);


    }

    private void setData_edit_to_string() {
        strName = editname.getText().toString().trim();
        strJobTitle = edtJobTitle.getText().toString().trim();
        strCompany = edtCompany.getText().toString().trim();
        strMobile = edtMobile.getText().toString().trim();
        strMobile2 = edtMobile2.getText().toString().trim();
        strTel = edttelephone.getText().toString().trim();
        strEmail = edtEmail.getText().toString().trim();
        strWeb = edtWebsite.getText().toString().trim();
        strAddress = edtAddress.getText().toString().trim();
        strFax = edtFax.getText().toString().trim();
        strQRCode = edtQRCode.getText().toString().trim();
    }

    private void edit_fields_visible() {
        edit_save = true;
        editbtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_save));
        editname.setText(txtname.getText().toString().trim());
        edtJobTitle.setText(txtJobTitle.getText().toString().trim());
        edtCompany.setText(txtCompany.getText().toString().trim());
        edtMobile.setText(txtMobile.getText().toString().trim());
        edtMobile2.setText(txtMobile2.getText().toString().trim());
        edttelephone.setText(txttelephone.getText().toString().trim());
        edtEmail.setText(txtEmail.getText().toString().trim());
        edtWebsite.setText(txtWebsite.getText().toString().trim());
        edtAddress.setText(txtAddress.getText().toString().trim());
        edtFax.setText(txtFax.getText().toString().trim());
        iv.setClickable(true);

        editname.setVisibility(View.VISIBLE);
        edtJobTitle.setVisibility(View.VISIBLE);
        edtCompany.setVisibility(View.VISIBLE);
        edtMobile.setVisibility(View.VISIBLE);
        edtMobile2.setVisibility(View.VISIBLE);
        edttelephone.setVisibility(View.VISIBLE);
        edtEmail.setVisibility(View.VISIBLE);
        edtWebsite.setVisibility(View.VISIBLE);
        webLayout.setVisibility(View.VISIBLE);
        edtAddress.setVisibility(View.VISIBLE);
        edtFax.setVisibility(View.VISIBLE);
        mobile1Layout.setVisibility(View.VISIBLE);
        mobile2Layout.setVisibility(View.VISIBLE);
        orgLayout.setVisibility(View.VISIBLE);
        email_cardview.setVisibility(View.VISIBLE);
        fax_cardview.setVisibility(View.VISIBLE);
        teleLayout.setVisibility(View.VISIBLE);

        txtname.setVisibility(View.GONE);
        txtJobTitle.setVisibility(View.GONE);
        txtCompany.setVisibility(View.GONE);
        txtMobile.setVisibility(View.GONE);
        txtMobile2.setVisibility(View.GONE);
        txttelephone.setVisibility(View.GONE);
        txtEmail.setVisibility(View.GONE);
        txtWebsite.setVisibility(View.GONE);
        txtAddress.setVisibility(View.GONE);
        txtFax.setVisibility(View.GONE);
    }

    private void init() {
        txtname = findViewById(R.id.name);
        txtJobTitle = findViewById(R.id.designation);
        txtCompany = findViewById(R.id.organization);
        txtMobile = findViewById(R.id.cell1);
        txtMobile2 = findViewById(R.id.cell2);
        txttelephone = findViewById(R.id.telcom1);
        txtEmail = findViewById(R.id.email);
        txtWebsite = findViewById(R.id.website);
        txtAddress = findViewById(R.id.address_location);
        txtFax = findViewById(R.id.fax);
        txtQRCode = findViewById(R.id.qrcode);

        editname = findViewById(R.id.edit_name);
        edtJobTitle = findViewById(R.id.edit_designation);
        edtCompany = findViewById(R.id.edit_organization);
        edtMobile = findViewById(R.id.edit_cell1);
        edtMobile2 = findViewById(R.id.edit_cell2);
        edttelephone = findViewById(R.id.edit_telcom1);
        edtEmail = findViewById(R.id.edit_email);
        edtWebsite = findViewById(R.id.edit_website);
        edtAddress = findViewById(R.id.edit_address_location);
        edtFax = findViewById(R.id.edit_fax);
        edtQRCode = findViewById(R.id.edit_qrcode);

        QRCardCardView = findViewById(R.id.qrcode_cardview);

        nameLayout = findViewById(R.id.name_layout);
        orgLayout = findViewById(R.id.organization_layout);
        mobile1Layout = findViewById(R.id.moblie1_layout);
        mobile2Layout = findViewById(R.id.moblie2_layout);
        teleLayout = findViewById(R.id.telephone_layout);
        emailLayout = findViewById(R.id.telephone_layout);
        faxLayout = findViewById(R.id.fax_layout);
        webLayout = findViewById(R.id.website_layout);
        email_cardview = findViewById(R.id.email_cardview);
        fax_cardview = findViewById(R.id.fax_cardview);
        website_cardview = findViewById(R.id.website_cardview);
        location_cardview = findViewById(R.id.location_cardview);

        address_location_cardview = findViewById(R.id.address_location_cardview);
        address_location_cardview.setOnClickListener(this);
        make_call_cardview = findViewById(R.id.make_call_cardview);
        make_call_cardview.setOnClickListener(this);
        send_email_cardview = findViewById(R.id.send_email_cardview);
        send_email_cardview.setOnClickListener(this);

        iv = findViewById(R.id.view_pager);
        iv.setOnClickListener(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Bitmap bitmapCameraImage = null;
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(imageReturnedIntent);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Log.e("URI ",new Gson().toJson(resultCode));
                String path = resultUri.getPath();
                bitmapCroped = ImageUtils.getInstant().getCompressedBitmap(path);
                frontimage = convertors.convertBitmapToBase(bitmapCroped);
                textRecognizerFunction(bitmapCroped);
                iv.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == QRCODE_REQUEST_CODE) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, imageReturnedIntent);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    String jsonString[] = result.toString().replace(";;", "").split("\\n");
                    String array[] = jsonString;
                    JSONObject obj;

                    for (int i = 0; i < jsonString.length; i++) {
                        jsonString[i] = "\"" + jsonString[i].replace("BEGIN:", "") + "\"";
                        array[i] = jsonString[i].replace("BEGIN:", "");
                        jsonString[i] = jsonString[i].replace(":", "\":\"");
                        Log.e("jsonString " + i, jsonString[i]);
                    }

                    String json_string = Arrays.toString(jsonString)
                            .replace("[", "{")
                            .replace("]", "}")
                            .replace(", \"\",", ",")
                            .replace("=", ",")
                            .replace(";", ",");
                    //if json format find...
                    Log.e("Array : ", Arrays.toString(jsonString));
                    Log.e("string : ", json_string);
                    try {
                        obj = new JSONObject(json_string);

                        if (json_string.contains("FN") && !obj.getString("FN").equals("")) {

                            Log.e("Name : ", obj.getString("FN"));
                            strName = obj.getString("FN").replace(",", " ");
                            txtname.setText(strName);
                        } else if (json_string.contains("N") && !obj.getString("N").equals("")) {

                            Log.e("Name : ", obj.getString("N"));
                            strName = obj.getString("N").replace(",", " ");
                            txtname.setText(strName);
                        } else if (json_string.contains("Contents")) {
                            String n = obj.getString("Contents");
                            if (!n.contains("VCARD")) {
                                strName = obj.getString("Contents").replace(",", " ");
                                txtname.setText(strName);
                            }
                        }

                        if (json_string.toUpperCase().contains("ORG") && !obj.getString("ORG").equals("")) {
                            strCompany = obj.getString("ORG");
                            txtCompany.setText(strCompany);
                            Log.e("WORK", strCompany);
                        }

                        if (json_string.toUpperCase().contains("CELL")) {
                            if (json_string.toUpperCase().contains("TEL,CELL")) {
                                strMobile = obj.getString("TEL,CELL");
                            } else if (json_string.toUpperCase().contains("TEL,TYPE,CELL")) {
                                strMobile2 = obj.getString("TEL,TYPE,CELL");
                            } else {
                                strMobile = "";
                                strMobile2 = "";
                            }
                            txtMobile.setText(strMobile);
                            txtMobile2.setText(strMobile2);
                        }
                        if (json_string.toUpperCase().contains("TEL")) {
                            if (json_string.toUpperCase().contains("TEL,WORK,VOICE")) {
                                strTel = obj.getString("TEL,WORK,VOICE");
                                txttelephone.setText(obj.getString("TEL,WORK,VOICE"));
                            }
                            txtCompany.setText(strCompany);
                        }
                        if (json_string.toUpperCase().contains("ADR")) {
                            strAddress = obj.getString("ADR");
                            txtAddress.setText(strAddress);
                        }
                        if (json_string.toUpperCase().contains("EMAIL")) {
                            if (json_string.toUpperCase().contains("EMAIL,WORK,INTERNET")) {
                                strEmail = obj.getString("EMAIL,WORK,INTERNET");
                            } else if (json_string.toUpperCase().contains("EMAIL,TYPE,INTERNET")) {
                                strEmail = obj.getString("EMAIL,TYPE,INTERNET");
                            }
                            txtEmail.setText(strEmail);
                        }

                        if (json_string.toUpperCase().contains("URL")) {
                            String url = obj.getString("URL");
                            if (url != null && url.contains(".")) {
                                strWeb = obj.getString("URL");
                                txtWebsite.setText(url);
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        try {
                            if (array != null) {
                                Log.e("Array", Arrays.toString(array));
                                for (int i = 0; i < array.length; i++) {
                                    Log.e("I", array[i]);
                                    //name
                                    try {
                                        if (array[i].contains("Contents") &&
                                                !array[i].contains("VCARD") &&
                                                !array[i].contains("www.") &&
                                                array[i] != null &&
                                                array[i] != "") {
                                            try {
                                                rex = regexMatcher(array[i]);
                                                txtname.setText(rex);
                                            } catch (NullPointerException e) {
                                                e.printStackTrace();
                                            }
                                        } else if (array[i].contains("www.")) {
                                            rex = regexWebsiteMatcher(array[i].replace("\"", ""));
                                            txtname.setText(rex);
                                            txtWebsite.setText(strName);
                                        }
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }

                                    //Moblile
                                    try {
                                        if (array[i].toUpperCase().contains("CELL")) {
                                            try {
                                                rex = regexCellMatcher(array[i].replace(" ", ""));
                                                txtMobile.setText(rex);
                                            } catch (NullPointerException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        if (array[i].contains("Personal")) {
                                            try {
                                                rex = regexCellMatch(array[i].replace(" ", ""));
                                                txtMobile2.setText(rex);
                                            } catch (NullPointerException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                    //Email
                                    try {
                                        if (array[i].toUpperCase().contains("EMAIL")) {
                                            try {
                                                Log.e("STRING : ", array[i]);
                                                System.out.println("Getting Phone Number");
//        edtMobile.setText("");
                                                final String PHONE_REGEX = "(\\w+\\@\\w+\\.\\w+)";
                                                Pattern p = Pattern.compile(PHONE_REGEX, Pattern.MULTILINE);
                                                Matcher m = p.matcher(array[i]);   // get a matcher object
                                                if (m.find()) {
                                                    System.out.println(m.group());
                                                    edtMobile.setText(m.group().trim());
                                                    System.out.println(edtMobile);
                                                }
                                                strEmail = rex;
                                            } catch (NullPointerException e) {
                                                e.printStackTrace();
                                            }
                                            if (strEmail.equals("")) {
                                                if (array[i].contains("@")) {
                                                    try {
                                                        rex = regexEmailMatcher(array[i]);
                                                        strEmail = rex;
                                                    } catch (NullPointerException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            } else {
                                                strEmail = "";
                                            }
                                            txtEmail.setText(strEmail);
                                        } else {
                                            if (array[i].contains("@")) {
                                                strEmail = array[i].replace("\"", "");
                                                txtEmail.setText(strEmail);
                                            }
                                        }
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }

                                    //address
                                    try {
                                        if (array[i].toUpperCase().contains("ADR")) {
                                            try {
                                                rex = regexMatcher(array[i]);
                                                txtAddress.setText(rex);
                                            } catch (NullPointerException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }

                                    //Telephone
                                    try {
                                        if (array[i].toUpperCase().contains("VOICE")) {
                                            try {
                                                rex = regexMatcher(array[i]);
                                                txttelephone.setText(rex);
                                            } catch (NullPointerException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public Bitmap BITMAP_RESIZER(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;

    }

    private void performCrop(Uri takenPhotoUri) {
        try {
            //Start Crop Activity
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(takenPhotoUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", true);
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
//            cropIntent.putExtra("scale",true);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public Uri getPhotoFileUri(String fileName) {
        if (isExternalStorageAvailable()) {
            File mediaStoreDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

            if (!mediaStoreDir.exists() && !mediaStoreDir.mkdirs()) {
                Log.d(TAG, "Failed to create Directory");
            }
            return Uri.fromFile(new File(mediaStoreDir.getPath() + File.separator + fileName));

        }
        return null;
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public static Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }


    private void textRecognizerFunction(Bitmap photo) {
        if (photo != null) {
            TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();

            Log.e("Function Recognizer", "Inside");

            if (!textRecognizer.isOperational()) {
                Log.e("Text Recognizer", "Not Operational");
                IntentFilter intentFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
                boolean hasLowStorage = registerReceiver(null, intentFilter) != null;

                if (hasLowStorage) {
                    Toast.makeText(getApplicationContext(), "Low Storage", Toast.LENGTH_SHORT).show();
                    Log.e("Device has", "Low Storage");
                }
            }

            Frame frame = new Frame.Builder().setBitmap(photo).build();
            Log.e("Text Recognizer Frame", String.valueOf(frame));

            SparseArray<TextBlock> textBlocks = textRecognizer.detect(frame);
            stringBuilder = new StringBuilder();
            TextBlock textBlock;
            for (int i = 0; i < textBlocks.size(); i++) {
                textBlock = textBlocks.valueAt(i);
                stringBuilder.append(textBlock.getValue());

                Log.e("stringbuilder addd", textBlock.getValue());
                stringBuilder.append("\n");
            }
            Log.e("stringbuilder", String.valueOf(stringBuilder));
            processImage();
        } else {
            Toast.makeText(getApplicationContext(), "no text found", Toast.LENGTH_SHORT).show();
        }
    }

    private void processImage() {
        String ORCResult = stringBuilder.toString();
        Log.e("OUTPUT>>", ORCResult);
        extractName(ORCResult);
        extractOrg(ORCResult);
        extractJobTitle(ORCResult);
        extractMobile(ORCResult);
        extractTel(ORCResult);
        extractEmail(ORCResult);
        extractFax(ORCResult);
        extractWebsite(ORCResult);
        extractAddress(ORCResult);
    }

    private void extractName(String orcResult) {
        System.out.println("Getting the Name");
//        edtname.setText("");
        final String NAME_REGEX = "^([A-Z]([a-z]*|\\.) *){1,2}([A-Z][a-z]+-?)+$";
        Pattern p = Pattern.compile(NAME_REGEX, Pattern.MULTILINE);
        Matcher m = p.matcher(orcResult);
        if (m.find()) {
            System.out.println(m.group());
            Log.e("NAME_REGEX : ", m.group());
//            edtname.setText(m.group().trim());
//            System.out.println(edtname);
        }
    }

    private void extractOrg(String orcResult) {
        System.out.println("Getting Organization");
        final String ORG_REGEX = "^([A-Z]([a-z]*|\\.) *){1,2}([A-Z][a-z]+-?)+$";
        Pattern pattern = Pattern.compile(ORG_REGEX, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(orcResult);
        if (matcher.find()) {
            System.out.println(matcher.group());
            strCompany = matcher.group().trim();
            txtCompany.setText(matcher.group().trim());
            Log.e("ORG_REGEX : ", matcher.group());
        }
    }

    private void extractJobTitle(String orcResult) {
        System.out.println("Getting the Job Title");
//        edtJobTitle.setText("");
        final String NAME_REGEX = "([A-Z]([a-z]*|\\.) *){1,2}([A-Z][a-z]+-?)+$";
        Pattern p = Pattern.compile(NAME_REGEX, Pattern.MULTILINE);
        Matcher m = p.matcher(orcResult);
        if (m.find()) {
            System.out.println(m.group());
            txtJobTitle.setText(m.group().trim());
            strJobTitle = m.group().trim();
            Log.e("NAME_REGEX : ", m.group());
            System.out.println(edtJobTitle);
        }
    }

    private void extractMobile(String orcResult) {
        Log.e("STRING : ", orcResult);
        System.out.println("Getting Phone Number");
//        edtMobile.setText("");
        final String PHONE_REGEX = "(\\+?\\d+\\-?\\s?\\d+)";
        Pattern p = Pattern.compile(PHONE_REGEX, Pattern.MULTILINE);
        Matcher m = p.matcher(orcResult);   // get a matcher object
        if (m.find()) {
            System.out.println(m.group());
            txtMobile.setText(m.group().trim());
            strMobile = m.group().trim();
            System.out.println(edtMobile);
            Log.e("PHONE_REGEX : ", m.group());
        }
    }

    private void extractTel(String orcResult) {

    }

    private void extractEmail(String orcResult) {
        System.out.println("Getting the email");
//        edtEmail.setText("");
        final String EMAIL_REGEX = "(\\w+\\@\\w+\\.\\w+)";
        Pattern p = Pattern.compile(EMAIL_REGEX, Pattern.MULTILINE);
        Matcher m = p.matcher(orcResult);   // get a matcher object
        if (m.find()) {
            System.out.println(m.group());
            txtEmail.setText(m.group().trim());
            strEmail = m.group().trim();
            Log.e("EMAIL_MATCHER : ", m.group());
        }
    }

    private void extractFax(String orcResult) {

    }

    private void extractWebsite(String orcResult) {
        System.out.println("Getting Website");
        final String WEBSITE_REGEX = "(www\\.?\\w+\\.?\\w+)";
        Pattern p = Pattern.compile(WEBSITE_REGEX, Pattern.MULTILINE);
        Matcher matcher = p.matcher(orcResult);
        if (matcher.find()) {
            System.out.println(matcher.group());
            txtWebsite.setText(matcher.group().trim());
            strWeb = matcher.group().trim();
            Log.e("WEBSITE_REGEX : ", matcher.group());
            System.out.println(edtWebsite);
        }
    }

    private void extractAddress(String orcResult) {
        System.out.println("Getting Address");
        final String ADDRESS_REGEX = "^([A-Z]([a-z]*|\\.) *){1,2}([A-Z][a-z]+-?)+$";
        Pattern p = Pattern.compile(ADDRESS_REGEX, Pattern.MULTILINE);
        Matcher matcher = p.matcher(orcResult);
        if (matcher.find()) {
            System.out.println(matcher.group());
            txtAddress.setText(matcher.group().trim());
            strAddress = matcher.group().trim();
            Log.e("ADDRESS_REGEX : ", matcher.group());
            System.out.println(edtAddress);
        }
    }

    private void hide_not_found_fields() {
        if (strMobile.equals("") || strMobile.equals("")) {
            txtMobile.setVisibility(View.INVISIBLE);
            mobile1Layout.setVisibility(View.GONE);
        } else {
            mobile1Layout.setVisibility(View.VISIBLE);
        }
        if (strMobile2.equals("")) {
            txtMobile2.setVisibility(View.INVISIBLE);
            mobile2Layout.setVisibility(View.GONE);
        } else {
            mobile2Layout.setVisibility(View.VISIBLE);
        }
        if (strTel.equals("")) {
            txttelephone.setVisibility(View.INVISIBLE);
            teleLayout.setVisibility(View.GONE);
        } else {
            teleLayout.setVisibility(View.VISIBLE);
        }
        if (strFax.equals("")) {
            txtFax.setVisibility(View.INVISIBLE);
            fax_cardview.setVisibility(View.GONE);
        } else {
            fax_cardview.setVisibility(View.VISIBLE);
        }
        if (strWeb.equals("")) {
            txtWebsite.setVisibility(View.INVISIBLE);
        } else {
            website_cardview.setVisibility(View.VISIBLE);
        }
        if (strEmail.equals("")) {
            txtEmail.setVisibility(View.INVISIBLE);
        } else {
            website_cardview.setVisibility(View.VISIBLE);
        }
    }

    private String regexEmailMatcher(String s) {
        String result = null;
        final String NAME_REGEX = "(\\w+\\@\\w+\\.\\w+)";
        Pattern p = Pattern.compile(NAME_REGEX, Pattern.MULTILINE);
        Matcher m = p.matcher(s);
        if (m.find()) {
            result = m.group().trim()
                    .replace(":\"", "")
                    .replace("\"", "").trim();
        }
        Log.e("result", result);
        return result;
    }

    private String regexWebsiteMatcher(String s) {
        String result = null;
        final String NAME_REGEX = "(\\w+\\.\\w+\\.\\w+)";
        Pattern p = Pattern.compile(NAME_REGEX, Pattern.MULTILINE);
        Matcher m = p.matcher(s);
        if (m.find()) {
            result = m.group().trim()
                    .replace(":\"", "")
                    .replace("\"", "").trim();
        }
        Log.e("result", result);
        return result;
    }

    private String regexMatcher(String s) {
        String result = null;
        final String NAME_REGEX = ":\"\\s*(.*)\"";
        Pattern p = Pattern.compile(NAME_REGEX, Pattern.MULTILINE);
        Matcher m = p.matcher(s);
        if (m.find()) {
            result = m.group().trim()
                    .replace(":\"", "")
                    .replace("\"", "").trim();
        }
        Log.e("resule", result);
        return result;
    }

    private String regexCellMatcher(String s) {
        String result = null;
        final String PHONE_REGEX = "(\\+?\\d+\\-?\\s?\\d+)";
        Pattern p = Pattern.compile(PHONE_REGEX, Pattern.MULTILINE);
        Matcher m = p.matcher(s);   // get a matcher object
        if (m.find()) {
            System.out.println(m.group());
            txtMobile.setText(m.group().trim());
            strMobile = m.group().trim();
            System.out.println(edtMobile);
            Log.e("PHONE_REGEX : ", m.group());
        }
        result = strMobile;
        return result;
    }

    private String regexCellMatch(String s) {
        String result = null;
        final String PHONE_REGEX = "(\\+?\\d+\\-?\\s?\\d+)";
        Pattern p = Pattern.compile(PHONE_REGEX, Pattern.MULTILINE);
        Matcher m = p.matcher(s);   // get a matcher object
        if (m.find()) {
            System.out.println(m.group());
            txtMobile2.setText(m.group().trim());
            strMobile2 = m.group().trim();
            System.out.println(edtMobile2);
            Log.e("PHONE_REGEX : ", m.group());
        }
        result = strMobile2;
        return result;
    }

    public Bitmap imageFolder() {
        Bitmap bitmapsave = bitmap;
        return bitmapsave;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CropImage.startPickImageActivity(this);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.make_call_cardview:
                if (!strMobile.equals("")) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", strMobile, null)));

                } else if (!strMobile2.equals("")) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", strMobile2, null)));

                } else if (!strTel.equals("")) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", strTel, null)));

                }
                break;
            case R.id.send_email_cardview:
                Toast.makeText(getApplicationContext(), "Msg Sending", Toast.LENGTH_SHORT).show();
                Log.i("Send email", "");
                String[] TO = {"" + strEmail};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    Log.i("Finished sending email", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.address_location_cardview:
                Toast.makeText(getApplicationContext(), "show Location", Toast.LENGTH_SHORT).show();
                break;

            case R.id.view_pager:
                if(edit_save == false){
                    CropImage.activity()
                            .start(BusinessCardDetails.this);
                }
                break;
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("IOException", String.valueOf(ex));
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.axpress.scm.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        Log.e("ContantURI",new Gson().toJson(contentUri));
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        setPic(contentUri);
    }

    private void setPic(Uri contentUri) {
        // Get the dimensions of the View
        int targetW = iv.getMaxWidth();
        int targetH = iv.getMaxHeight();
        Log.e("Width", String.valueOf(targetW));
        Log.e("Height", String.valueOf(targetH));

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        iv.setImageBitmap(bitmap);
        frontimage = convert(bitmap);
        textRecognizerFunction(bitmap);

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
    public String getCardId() {
        return card_id;
    }

    @Override
    public String getFrontImage() {
        return frontimage;
    }

    @Override
    public String getBackImage() {
        return backimage;
    }

    @Override
    public String getJobTitle() {
        return strJobTitle;
    }

    @Override
    public String getName() {
        return strName;
    }

    @Override
    public String getCompany() {
        return strCompany;
    }

    @Override
    public String getMobile1() {
        return strMobile;
    }

    @Override
    public String getMobile2() {
        return strMobile2;
    }

    @Override
    public String getTel() {
        return strTel;
    }

    @Override
    public String getEmail() {
        return strEmail;
    }

    @Override
    public String getFax() {
        return strFax;
    }

    @Override
    public String getWeb() {
        return strWeb;
    }

    @Override
    public String getQRCode() {
        return strQRCode;
    }

    @Override
    public String getAddress() {
        return strAddress;
    }

    @Override
    public String getCreatedBy() {
        return Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID);
    }

    @Override
    public void showLoadingLayout() {
        progressDialogCall = new ProgressDialog(this);
        progressDialogCall.setTitle("Data Sync, wait for few moment!");
        progressDialogCall.setMessage("Loading...");
        progressDialogCall.show();
    }

    @Override
    public void hideLoadingLayout() {
        progressDialogCall.dismiss();
    }

    @Override
    public void showSuccess(Object object) {
        if (object instanceof AddBusinessCardResponse){
            AddBusinessCardResponse response = (AddBusinessCardResponse) object;
            Log.e("response",new Gson().toJson(response));
            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    Toast.makeText(getApplicationContext(), CONSTANT.dataSaved, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), CONSTANT.dataNotSaved, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),CONSTANT.SEVER_NOT_RECHABLE,Toast.LENGTH_SHORT).show();
            }
        }

        else if (object instanceof UpdateBusinessCardResponse){
            UpdateBusinessCardResponse response = (UpdateBusinessCardResponse) object;
            Log.e("response",new Gson().toJson(response));
            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    Toast.makeText(getApplicationContext(), CONSTANT.dataUpdated, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), CONSTANT.dataNotUpdate, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),CONSTANT.serverNotReachable,Toast.LENGTH_SHORT).show();
            }
        }

        else if (object instanceof FetchBusinessCardResponse){
            FetchBusinessCardResponse response = (FetchBusinessCardResponse) object;
            Log.e("response",new Gson().toJson(response));
            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    FetchBusinessCard fetchResponse = (FetchBusinessCard) response.getBusinessCard();
                    txtname.setText(fetchResponse.getName());
                    strName = fetchResponse.getName();
                    frontimage = fetchResponse.getFrontimage();
                    try {
                        iv.setImageBitmap(convert(frontimage));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!frontimage.equals("")) {
                        iv.setImageBitmap(convert(frontimage));
                    }
                    backimage = fetchResponse.getBackimage();
                    txtJobTitle.setText(fetchResponse.getJobTitle());
                    strName = fetchResponse.getName();
                    txtCompany.setText(fetchResponse.getOrganization());
                    strCompany = fetchResponse.getOrganization();
                    txtMobile.setText(fetchResponse.getMobile1());
                    strMobile = fetchResponse.getMobile1();
                    txtMobile2.setText(fetchResponse.getMobile2());
                    strMobile2 = fetchResponse.getMobile2();
                    txttelephone.setText(fetchResponse.getTelephone());
                    strTel = fetchResponse.getTelephone();
                    txtEmail.setText(fetchResponse.getEmail());
                    strEmail = fetchResponse.getEmail();
                    txtFax.setText(fetchResponse.getFax());
                    strFax = fetchResponse.getFax();
                    txtWebsite.setText(fetchResponse.getWebsite());
                    strWeb = fetchResponse.getWebsite();
                    txtQRCode.setText(fetchResponse.getImageBarcode());
                    strQRCode = fetchResponse.getImageBarcode();
                    txtAddress.setText(fetchResponse.getAddress());
                    strAddress = fetchResponse.getAddress();
                } else {
                    Toast.makeText(getApplicationContext(), CONSTANT.DATA_NOT_FOUND, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),CONSTANT.server_not_reachable,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(),CONSTANT.server_not_reachable,Toast.LENGTH_SHORT).show();
        progressDialogCall.dismiss();
    }
}

