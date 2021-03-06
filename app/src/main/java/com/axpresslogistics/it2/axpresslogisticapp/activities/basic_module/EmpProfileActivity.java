package com.axpresslogistics.it2.axpresslogisticapp.activities.basic_module;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CameraUtils;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ImageUtils;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.VolleySingleton;
import com.axpresslogistics.it2.axpresslogisticapp.VolleySupport.VolleyMultipartRequest;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT.URL;

public class EmpProfileActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final String GALLERY_DIRECTORY_NAME = "axpress/camera";
    public static final String IMAGE_EXTENSION = "jpg";
    public static final int BITMAP_SAMPLE_SIZE = 8;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static int PICK_IMAGE_REQUEST = 1;
    private static String imageStoragePath;
    String UPDATE_URL = URL + "HRMS/hr_approval";
    String EMPLOYEE_PROFILE_URL = URL + "HRMS/employee_info";
    String PROFILE_IMAGE_UPLOAD_URL = URL + "HRMS/image";
    Boolean FLAG = false;
    EditText edtName, edtEmpCode, edtContactNo, edtContactAltNo, edtDesignation, edtDept, edtBranch,
            edtBranchCode,
            edtEmail, edtFatherName, edtDOB, edtBOP, edtDOJ, edtQulification, edtAdharCard,
            edtAddress, edtPAN, edtUAN, edtESI, edtBankAccount, edtBankName, edtBankIFSC;
    TextView txtName, txtEmpId, txt_address_update_status, txt_bank_account_update_status,
            txt_bank_name_update_status,
            txt_bank_ifsc_update_status;
    String strName, strEmpCode, strContactNo, strContactAltNo, strDesignation, strDept, strBranch,
            strBranchCode, strEmail, strFatherName, strDOB, strBOP, strDOJ, strQulification, strAdharCard,
            strAddress, strPAN, strUAN, strESI, strBankAccount, strBankName, strBankIFSC,strProfileImage,strImageURL;
    ImageView edtAddressBtn, edtBankAccountBtn, edtBankNameBtn, edtIFSCBtn, userImageView, editImage, image_view;
    Intent intent;
    String title, method, str, image_profile;
    JSONObject jObj;
    ImageButton backbtn_toolbar, refreshbtn_toolbar;
    Dialog dialog;
    Bitmap bitmap;
    byte[] image = null;
    boolean hasImage = false;
    int GALLERY = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_profile);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.PROFILE);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        refreshbtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        refreshbtn_toolbar.setOnClickListener(this);
        refreshbtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_refresh));
        //Set View with fields..
        setView();
        strEmpCode = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
        String imageP = Preferences.getPreference(getApplicationContext(), CONSTANT.IMAGEURL);
        image_profile = Preferences.getPreference(getApplicationContext(), CONSTANT.USER_IMAGE);

        try {
                userImageView.setImageBitmap(convertBasetoBitmap(imageP));
        } catch (Exception e) {
            e.printStackTrace();
        }
        employee_profileApi();
        editImage.setVisibility(View.VISIBLE);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        restoreFromBundle(savedInstanceState);
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Profile Image");
        String[] pictureDialogItems = {
                "Select From Gallery",
//                "Take  A Picture"
        };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if (Build.VERSION.SDK_INT >= 23) {
                                    int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;
                                    if (ContextCompat.checkSelfPermission(EmpProfileActivity.this,
                                            Manifest.permission.READ_EXTERNAL_STORAGE)
                                            != PackageManager.PERMISSION_GRANTED) {
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(EmpProfileActivity.this,
                                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                        } else {
                                            ActivityCompat.requestPermissions(EmpProfileActivity.this,
                                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                                            choosePhotoFromGallary();
                                        }
                                    }else{
                                        ActivityCompat.requestPermissions(EmpProfileActivity.this,
                                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                                        choosePhotoFromGallary();
                                    }
                                } else {
                                    choosePhotoFromGallary();
                                }
                                break;
                            case 1:
                                // dispatchTakePictureIntent();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        try {
            if (requestCode == GALLERY) {
                if (imageReturnedIntent != null) {
                    Uri uri = imageReturnedIntent.getData();
                    String path = getRealPathFromURI(uri);
                    Preferences.setPreference(EmpProfileActivity.this, CONSTANT.imagePath, path);
                    bitmap = ImageUtils.getInstant().getCompressedBitmap(path);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    image = byteArrayOutputStream.toByteArray();
                    Log.e("setimage", path);
                    userImageView.setImageBitmap(bitmap);
                    hasImage = true;
                    UpdateProfile(bitmap, path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = EmpProfileActivity.this.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    void UpdateImage(final  Bitmap bitmap){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final String method = "image";
        final ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();

        HashMap<String, String> params = new HashMap<>();
        params.put("method", method);
        params.put("key", key);
        params.put("emplid", strEmpCode);
        params.put("image",convertBitmapToBase(bitmap));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UPDATE_URL,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status = response.optString(CONSTANT.STATUS);
                if(status.equals(CONSTANT.TRUE)){
                    try {
                        Preferences.setPreference(EmpProfileActivity.this, CONSTANT.IMAGEURL,response.getString(CONSTANT.IMAGEURL));
                        String imageString = response.getString(CONSTANT.IMAGEURL);
                        userImageView.setImageBitmap(convertBasetoBitmap(imageString));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }

    public String convertBitmapToBase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b,Base64.DEFAULT);
    }

    public Bitmap convertBasetoBitmap(String image_profile) {
        byte[] decodedString = Base64.decode(image_profile, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    void UpdateProfile(final Bitmap bitmap, final String path) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final String method = "image";
        final ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                PROFILE_IMAGE_UPLOAD_URL, new Response.Listener<NetworkResponse>() {

            @Override
            public void onResponse(NetworkResponse response) {
                String responseString = new String(response.data);
                Log.e("ERRORRESPONSE", responseString + "ERROR");
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(responseString);
                    //TODO response codeyour respone is onh
                    Log.e("TEXT", responseString);
                    String status = object.optString(CONSTANT.STATUS);
                    if (status.equals(CONSTANT.TRUE)) {
                        Toast.makeText(getApplicationContext(), CONSTANT.IMAGE_UPLOADED_SUCCESSFULLY,
                                Toast.LENGTH_SHORT).show();
                        Preferences.setPreference(EmpProfileActivity.this, CONSTANT.USER_IMAGE, path);
                        Preferences.setPreference(EmpProfileActivity.this, CONSTANT.IMAGEURL, path);
                        userImageView.setImageBitmap(bitmap);

//                        Preferences.setPreference(EmpProfileActivity.this, CONSTANT.IMAGEURL,
//                                object.getString(CONSTANT.IMAGEURL));
//
//                        String imageString = object.getString(CONSTANT.IMAGEURL);
//                        userImageView.setImageBitmap(convertBasetoBitmap(imageString));
                    }

                } catch (JSONException e) {
                    Log.e("JSONException", e.getMessage());
                    Toast.makeText(getApplicationContext(), CONSTANT.IMAGE_UPLOADED_UNSUCCESSFULL,
                            Toast.LENGTH_SHORT).show();
                    Log.e("ERRORcatch", responseString + "catch");

                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
                Log.e("ERROR", error + "ERROR");
                progressDialog.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("method", method);
                params.put("key", key);
                params.put("emplid", strEmpCode);
                Log.e("Params",new Gson().toJson(params));
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                if (hasImage) {
                    params.put("image", new DataPart(strEmpCode + ".jpg", image, "image/jpeg"));
                }
                Log.e("Image",new Gson().toJson(params));
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(EmpProfileActivity.this).addToRequestQueue(multipartRequest);
    }

    public boolean imageFlagFunction() {
        return true;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        Log.e("bitmapImage", bitmapImage.toString());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        Log.e("directory", directory.toString());

        File mypath = new File(directory, "profile.jpg");
        Log.e("mypath", mypath.toString());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private void restoreFromBundle(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH)) {
                imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
                if (!TextUtils.isEmpty(imageStoragePath)) {
                    if (imageStoragePath.substring(imageStoragePath.lastIndexOf(".")).equals("." + IMAGE_EXTENSION)) {
                        previewCapturedImage();
                    }
                }
            }
        }
    }

    private void previewCapturedImage() {
        try {
            Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
            userImageView.setImageBitmap(bitmap);
            Log.e("PREVIEW : ", bitmap.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void employee_profileApi() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final String method = CONSTANT.EMPLOYEE_METHOD;
        final ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EMPLOYEE_PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Response multi : ", response);
                        try {
                            jObj = new JSONObject(response);
                            String status = jObj.optString(CONSTANT.STATUS);
                            String employee_id = jObj.optString(CONSTANT.EMPID);

                            if (status.equals(CONSTANT.TRUE) && employee_id.equals(strEmpCode)) {
                                getValuesFromAPI();
                                setValuesInFields();
                                checkPendingStatus();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.INTERNET_ERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(CONSTANT.METHOD, method);
                params.put(CONSTANT.KEY, apikey.trim());
                params.put(CONSTANT.EMPID, Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void checkPendingStatus() {
        try {
            if (jObj.getString("address_status").equals("Pending") ||
                    jObj.getString("address_status").equals("Approved") ||
                    jObj.getString("address_status").equals("Unapproved")) {
                txt_address_update_status.setText(jObj.getString("address_status"));
                txt_address_update_status.setVisibility(View.VISIBLE);
            } else {
                txt_address_update_status.setVisibility(View.INVISIBLE);
            }
            if (jObj.getString("ifsc_code_status").equals("Pending") ||
                    jObj.getString("ifsc_code_status").equals("Approved") ||
                    jObj.getString("ifsc_code_status").equals("Unapproved")) {
                txt_bank_ifsc_update_status.setText(jObj.getString("ifsc_code_status"));
                txt_bank_ifsc_update_status.setVisibility(View.VISIBLE);
            } else {
                txt_bank_ifsc_update_status.setVisibility(View.INVISIBLE);
            }
            if (jObj.getString("bank_name_status").equals("Pending") ||
                    jObj.getString("bank_name_status").equals("Approved") ||
                    jObj.getString("bank_name_status").equals("Unapproved")) {
                txt_bank_name_update_status.setText(jObj.getString("bank_name_status"));
                txt_bank_name_update_status.setVisibility(View.VISIBLE);
            } else {
                txt_bank_name_update_status.setVisibility(View.INVISIBLE);
            }
            if (jObj.getString("bank_name_status").equals("Pending") ||
                    jObj.getString("bank_acc_status").equals("Approved") ||
                    jObj.getString("bank_acc_status").equals("Unapproved")) {
                txt_bank_account_update_status.setText(jObj.getString("bank_acc_status"));
                txt_bank_account_update_status.setVisibility(View.VISIBLE);
            } else {
                txt_bank_account_update_status.setVisibility(View.INVISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setValuesInFields() {
        txtName.setText((strName.trim()));
        txtEmpId.setText(strEmpCode.trim());
        if(!strImageURL.equals("null")){
            userImageView.setImageBitmap(convertBasetoBitmap(strImageURL));

        }else if (!strProfileImage.equals("null")){
            Picasso.get().load(image_profile).memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE).into(userImageView);
        }else{
            userImageView.setImageDrawable(getResources().getDrawable(R.drawable.userimage));
        }
        //put employee info value..
        edtName.setText(strName.trim());
        edtEmpCode.setText(strEmpCode.trim());
        edtContactNo.setText(strContactNo.trim());
//        edtContactAltNo.setText(strContactAltNo.trim());
        edtDesignation.setText(strDesignation.trim());
        edtDept.setText(strDept.trim());
        edtBranch.setText(strBranch.trim());
        edtBranchCode.setText(strBranchCode.trim());
        edtEmail.setText(strEmail.trim());
        //put employee ipersonal info..
        edtFatherName.setText(strFatherName.trim());
        edtDOB.setText(strDOB.trim());
        edtBOP.setText(strBOP.trim());
        edtDOJ.setText(strDOJ.trim());
        edtQulification.setText(strQulification.trim());
        edtAdharCard.setText(strAdharCard.trim());
        edtAddress.setText(strAddress.trim());
        edtPAN.setText(strPAN.trim());
        edtUAN.setText(strUAN.trim());
        edtESI.setText(strESI.trim());
        edtBankAccount.setText(strBankAccount.trim());
        edtBankName.setText(strBankName.trim());
        edtBankIFSC.setText(strBankIFSC.trim());
    }

    private void getValuesFromAPI() {
        strName = jObj.optString("employee_name");
        strContactNo = jObj.optString("employee_contact");
        try {
            strProfileImage = jObj.getString("profile_image");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            strImageURL  =jObj.getString("imageURL");
            if(!strImageURL.equals("null")){
                userImageView.setImageBitmap(convertBasetoBitmap(strImageURL));

            }else if (!strProfileImage.equals("null")){
                Picasso.get().load(image_profile).memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE).into(userImageView);
            }else{
                userImageView.setImageDrawable(getResources().getDrawable(R.drawable.userimage));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        strContactAltNo = jObj.optString("employee_contact1");

        strDesignation = jObj.optString("employee_designation");
        strDept = jObj.optString("employee_department");
        strBranch = jObj.optString("employee_branch");
        strBranchCode = jObj.optString("employee_branch");
        strEmail = jObj.optString("employee_email");
        //get employee personal info..
        strFatherName = jObj.optString("employee_father_name");
        strDOB = jObj.optString("employee_dob");
        strBOP = jObj.optString("employee_birth_state");
        strDOJ = jObj.optString("employee_doj");
        strQulification = jObj.optString("employee_qualification");
        strAdharCard = jObj.optString("employee_adhaarno");
        strAddress = jObj.optString("employee_address");
        //get employee bank/imp info..
        strPAN = jObj.optString("employee_panno");
        strUAN = jObj.optString("employee_uanno");
        strESI = jObj.optString("employee_esicno");
        strBankAccount = jObj.optString("employee_bankac");
        strBankName = jObj.optString("employee_bankname");
        strBankIFSC = jObj.optString("employee_ifsccode");
    }

    private void setView() {
        userImageView = findViewById(R.id.user_imageId);
        userImageView.setOnClickListener(this);
        editImage = findViewById(R.id.useredit_imageId);
        edtName = findViewById(R.id.edtname);
        edtEmpCode = findViewById(R.id.edtEmpCode);
        edtContactNo = findViewById(R.id.edtContactNo);
        edtDesignation = findViewById(R.id.edtDesignation);
        edtDept = findViewById(R.id.edtDept);
        edtBranch = findViewById(R.id.edtBranch);
        edtBranchCode = findViewById(R.id.edtBranchCode);
        edtEmail = findViewById(R.id.edtEmail);
        // put employee personal id..
        edtFatherName = findViewById(R.id.edtFatherName);
        edtDOB = findViewById(R.id.edtDOB);
        edtBOP = findViewById(R.id.edtBOP);
        edtDOJ = findViewById(R.id.edtDOJ);
        edtQulification = findViewById(R.id.edtQulification);
        edtAdharCard = findViewById(R.id.edtAdharCard);
        edtAddress = findViewById(R.id.edtAddress);
        //put employee bank info..
        edtPAN = findViewById(R.id.edtPAN);
        edtUAN = findViewById(R.id.edtUAN);
        edtESI = findViewById(R.id.edtESI);
        edtBankAccount = findViewById(R.id.edtBankAccount);
        edtBankName = findViewById(R.id.edtBankName);
        edtBankIFSC = findViewById(R.id.edtBankIFSC);
        txtName = findViewById(R.id.user_nameId);
        txtEmpId = findViewById(R.id.user_empcodeId);


        //edit edittextfield on btn click..
        edtAddressBtn = findViewById(R.id.edtImageButtonAddress);
        edtBankAccountBtn = findViewById(R.id.edtImageButtonBankAccount);
        edtBankNameBtn = findViewById(R.id.edtImageButtonbankName);
        edtIFSCBtn = findViewById(R.id.edtImageButtonBankIFSC);
        edtAddressBtn.setOnClickListener(this);
        edtBankAccountBtn.setOnClickListener(this);
        edtBankNameBtn.setOnClickListener(this);
        edtIFSCBtn.setOnClickListener(this);

        //update status textview fields...
        txt_address_update_status = findViewById(R.id.txt_address_update_status);
        txt_bank_account_update_status = findViewById(R.id.txt_bank_account_update_status);
        txt_bank_name_update_status = findViewById(R.id.txt_bank_name_update_status);
        txt_bank_ifsc_update_status = findViewById(R.id.txt_bank_ifsc_update_status);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtImageButtonAddress:
                if (FLAG.equals(false)) {
                    String title = "Address";
                    method = "address";
                    showChangeProfileDialog(title, strAddress, method);
                }
                break;
            case R.id.edtImageButtonBankAccount:
                if (FLAG.equals(false)) {
                    title = "Bank Account";
                    method = "bank_account";
                    showChangeProfileDialog(title, strBankAccount, method);
                } else {
                    Toast.makeText(getApplicationContext(), "disable", Toast.LENGTH_SHORT).show();
                    edtBankAccount.setFocusable(false);
                    edtBankAccount.setFocusableInTouchMode(false);
                    edtBankAccount.setClickable(false);
                    FLAG = false;
                }
                break;
            case R.id.edtImageButtonbankName:
                String title = "Bank Name";
                method = "bank_name";
                showChangeProfileDialog(title, strBankName, method);
                break;
            case R.id.edtImageButtonBankIFSC:
                title = "Bank IFSC Code";
                method = "bank_ifsc";
                showChangeProfileDialog(title, strBankIFSC, method);
                break;
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                refresh();
                break;
            case R.id.user_imageId:
                showimage();
                break;
        }
    }

    private void showimage() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.user_imageview);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        image_view = dialog.findViewById(R.id.image_view);
        if(!strImageURL.equals("null")){
            userImageView.setImageBitmap(convertBasetoBitmap(strImageURL));

        }else if (!strProfileImage.equals("null")){
            Picasso.get().load(image_profile).memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE).into(userImageView);
        }else{
            userImageView.setImageDrawable(getResources().getDrawable(R.drawable.userimage));
        }
        image_view.getAdjustViewBounds();
        image_view.getMaxWidth();
        image_view.getMaxHeight();
        dialog.show();
    }


    public void showChangeProfileDialog(String title, final String field_detail, final String method) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_emp_profile, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (dialogView.findViewById(R.id.edtUpdateDialog));
        edt.setText(field_detail);
        dialogBuilder.setTitle(title);
        dialogBuilder.setPositiveButton(CONSTANT.DONE, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                str = edt.getText().toString().trim();
                //do something with edt.getText().toString();
                Toast.makeText(getApplicationContext(), "Pending for approval : " + edt.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                postUpdateFunction(str, method);
                if (method.equals("address")) {
                    txt_address_update_status.setVisibility(View.VISIBLE);
                } else if (method.equals("bank_account")) {
                    txt_bank_account_update_status.setVisibility(View.VISIBLE);
                } else if (method.equals("bank_name")) {
                    txt_bank_account_update_status.setVisibility(View.VISIBLE);
                } else if (method.equals("bank_ifsc")) {
                    txt_bank_account_update_status.setVisibility(View.VISIBLE);
                }
            }
        });
        dialogBuilder.setNegativeButton(CONSTANT.CANCEL, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                edt.setText(field_detail);
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void postUpdateFunction(final String str, final String method) {
        this.method = method;
        ApiKey apiKey = new ApiKey();
        this.str = str;
        final String apikey = apiKey.saltStr();
        final String arrlist[] = {"address", "bank_name", "bank_account", "bank_ifsc"};

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.optString(CONSTANT.RESPNOSE);
                    String apiKeyResponse = object.optString(CONSTANT.KEY);
                    String method = object.optString(CONSTANT.METHOD);
                    Log.e("HR Approval: ", response);
                    if (response.equals(CONSTANT.TRUE)) {
                        Log.e("response for test", response);
                        refresh();
                    }

                    if (status.equals(CONSTANT.VERIFIED) && apiKeyResponse.equals(apikey)) {
//                        for (int i = 0; i < arrlist.length; i++) {
                        if (method.equals("address")) {
                            txt_address_update_status.setText("Verified");
                        } else if (method.equals("bank_account")) {
                            txt_bank_account_update_status.setText("Verified");
                        } else if (method.equals("bank_name")) {
                            txt_bank_account_update_status.setText("Verified");
                        } else if (method.equals("bank_ifsc")) {
                            txt_bank_account_update_status.setText("Verified");
                        }
//                        }
                    } else if (status.equals(CONSTANT.NOT_VERIFIED) && apiKeyResponse.equals(apikey)) {
                        txt_address_update_status.setText(CONSTANT.PENDING);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response======", "" + error.toString());
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.INTERNET_ERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("method", method);
                params.put("key", apikey);
                params.put("emplid", strEmpCode);
                params.put("data", str);
                if (method.equals("address")) {
                    params.put("address_type", "permanent");
                } else {
                    params.put("address_type", "");
                }
                Log.e("Params",new Gson().toJson(params));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void refresh() {
        employee_profileApi();
    }
}