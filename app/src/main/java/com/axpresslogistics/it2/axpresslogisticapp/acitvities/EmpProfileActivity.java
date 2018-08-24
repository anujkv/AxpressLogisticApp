package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
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
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.DEVELOPMENT_URL;
import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.URL;

public class EmpProfileActivity extends AppCompatActivity implements View.OnClickListener {
    String UPDATE_URL = URL + "HRMS/hr_approval";
    String EMPLOYEE_PROFILE_URL = URL + "HRMS/employee_info";

//    String UPDATE_URL = DEVELOPMENT_URL + "HRMS/hr_approval";
//    String EMPLOYEE_PROFILE_URL = DEVELOPMENT_URL + "HRMS/employee_info";

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
            strAddress, strPAN, strUAN, strESI, strBankAccount, strBankName, strBankIFSC;
    ImageView edtAddressBtn, edtBankAccountBtn, edtBankNameBtn, edtIFSCBtn,userImageView,editImage,image_view;
    Intent intent;
    String  title, method, str;
    JSONObject jObj;
    ImageButton backbtn_toolbar,refreshbtn_toolbar;
    private static int PICK_IMAGE_REQUEST = 1;
    private static String imageStoragePath;

    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final String GALLERY_DIRECTORY_NAME = "axpress/camera";
    public static final String IMAGE_EXTENSION = "jpg";
    public static final int BITMAP_SAMPLE_SIZE = 8;
    public static final int MEDIA_TYPE_IMAGE = 1;
    Dialog dialog;

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
        strEmpCode = Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID);
        employee_profileApi();
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.
                        EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryintent, PICK_IMAGE_REQUEST);

            }
        });
        restoreFromBundle(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent){
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

                if(resultCode == RESULT_OK && imageReturnedIntent != null && imageReturnedIntent.getData() != null) {
                    Uri uri = imageReturnedIntent.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        userImageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
    }

    private void restoreFromBundle(Bundle savedInstanceState) {
        Log.e("restoreFromBundle","I M here");

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH)) {
                imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
                if (!TextUtils.isEmpty(imageStoragePath)) {
                    if (imageStoragePath.substring(imageStoragePath.lastIndexOf(".")).equals("." + IMAGE_EXTENSION)) {
                        Log.e("Show me","I M here");
                        previewCapturedImage();
                    }
                }
            }
        }
    }

    private void previewCapturedImage() {
        try {
//            imageView.setVisibility(View.VISIBLE);

            Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);

            userImageView.setImageBitmap(bitmap);

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
                        Log.e("Response : ", response);
                        try {
                            jObj = new JSONObject(response);
                            String status = jObj.optString(CONSTANT.STATUS);
                            String employee_id = jObj.optString(CONSTANT.EMPID);

                            if(status.equals(CONSTANT.TRUE) && employee_id.equals(strEmpCode)){
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(CONSTANT.METHOD,method);
                params.put(CONSTANT.KEY, apikey.trim());
                params.put(CONSTANT.EMPID, Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void checkPendingStatus() {
        try {
            if(jObj.getString("address_status").equals("Pending") ||
                    jObj.getString("address_status").equals("Approved") ||
                    jObj.getString("address_status").equals("Unapproved")){
                txt_address_update_status.setText(jObj.getString("address_status"));
                txt_address_update_status.setVisibility(View.VISIBLE);
            }else{txt_address_update_status.setVisibility(View.INVISIBLE);}
            if(jObj.getString("ifsc_code_status").equals("Pending") ||
                    jObj.getString("ifsc_code_status").equals("Approved") ||
                    jObj.getString("ifsc_code_status").equals("Unapproved")){
                txt_bank_ifsc_update_status.setText(jObj.getString("ifsc_code_status"));
                txt_bank_ifsc_update_status.setVisibility(View.VISIBLE);
            }else{txt_bank_ifsc_update_status.setVisibility(View.INVISIBLE);}
            if(jObj.getString("bank_name_status").equals("Pending") ||
                    jObj.getString("bank_name_status").equals("Approved") ||
                    jObj.getString("bank_name_status").equals("Unapproved")){
                txt_bank_name_update_status.setText(jObj.getString("bank_name_status"));
                txt_bank_name_update_status.setVisibility(View.VISIBLE);
            }else{txt_bank_name_update_status.setVisibility(View.INVISIBLE);}
            if(jObj.getString("ban_acc_status").equals("Pending") ||
                    jObj.getString("ban_acc_status").equals("Approved") ||
                    jObj.getString("ban_acc_status").equals("Unapproved")){
                txt_bank_account_update_status.setText(jObj.getString("ban_acc_status"));
                txt_bank_account_update_status.setVisibility(View.VISIBLE);
            }else{txt_bank_account_update_status.setVisibility(View.INVISIBLE);}

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setValuesInFields()
    {
        txtName.setText((strName.trim()));
        txtEmpId.setText(strEmpCode.trim());
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
        strAdharCard = jObj.optString("employee_aadharno");
        strAddress = jObj.optString("employee_address");
        //get employee bank/imp info..
        strPAN = jObj.optString("employee_panno");
        strUAN = jObj.optString("employee_uanno");
        strESI = jObj.optString("employee_esicno");
        strBankAccount = jObj.optString("employee_bankacc");
        strBankName = jObj.optString("employee_bankname");
        strBankIFSC = jObj.optString("employee_ifsc");
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
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        image_view = dialog.findViewById(R.id.image_view);
        dialog.show();
    }

//    @Override
//    public void startActivityForResult(Intent intent, int requestCode) {
//        super.startActivityForResult(intent, requestCode);
////        if (requestCode == RESULT_LOAD_IMAGE && requestCode == RESULT_OK && null != intent) {
////            Uri selectedImage = intent.getData();
////            String[] filePathColumn = { MediaStore.Images.Media.DATA };
////
////            Cursor cursor = getContentResolver().query(selectedImage,
////                    filePathColumn, null, null, null);
////            cursor.moveToFirst();
////            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
////            String picturePath = cursor.getString(columnIndex);
////            cursor.close();
//////            ImageView imageView = findViewById(R.id.user_imageId);
////            userImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
////            image_view.setImageBitmap(BitmapFactory.decodeFile(picturePath));
////        }
//
////        if (requestCode == RESULT_LOAD_IMAGE && null != intent){
////            Log.e("Intent",intent.toString());
////            Log.e("Request", String.valueOf(requestCode));
////            Uri selectedImage = intent.getData();
////            String[] filePath = { MediaStore.Images.Media.DATA };
////
////            assert selectedImage != null;
////            Log.e("selectedImage",selectedImage.toString());
////            Log.e("filePath", Arrays.toString(filePath));
////            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
////            if (c != null) {
////                c.moveToFirst();
////                int columnIndex = c.getColumnIndex(filePath[0]);
////                String picturePath = c.getString(columnIndex);
////                c.close();
////                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
////                userImageView.setImageBitmap(thumbnail);
////                image_view.setImageBitmap(thumbnail);
////            }
////
//////            userImageView.setImageBitmap(thumbnail);
////        }
//        if(requestCode==PICK_IMAGE_REQUEST && requestCode == RESULT_OK && null != intent) {
//            Uri selectedImage = intent.getData();
//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                Log.e("Bitmap : ",bitmap.toString());
//            } catch (FileNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }else{
//            Toast.makeText(getApplicationContext(),"false",Toast.LENGTH_SHORT).show();
//        }
//    }

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
                }else if(method.equals("bank_account")){
                    txt_bank_account_update_status.setVisibility(View.VISIBLE);
                }else if(method.equals("bank_name")){
                    txt_bank_account_update_status.setVisibility(View.VISIBLE);
                }else if(method.equals("bank_ifsc")){
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
                    Log.e("HR Approval: ",response);
                    if(response.equals(CONSTANT.TRUE)){
                        Log.e("response for test",response);
                        refresh();
                    }

                    if (status.equals(CONSTANT.VERIFIED) && apiKeyResponse.equals(apikey)) {
//                        for (int i = 0; i < arrlist.length; i++) {
                            if (method.equals("address")) {
                                txt_address_update_status.setText("Verified");
                            }else if(method.equals("bank_account")){
                                txt_bank_account_update_status.setText("Verified");
                            }else if(method.equals("bank_name")){
                                txt_bank_account_update_status.setText("Verified");
                            }else if(method.equals("bank_ifsc")){
                                txt_bank_account_update_status.setText("Verified");
                            }
//                        }
                    } else if(status.equals(CONSTANT.NOT_VERIFIED) && apiKeyResponse.equals(apikey)){
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
                params.put("method",method);
                params.put("key",apikey);
                params.put("emplid",strEmpCode);
                params.put("data",str);
                if(method.equals("address")){
                    params.put("address_type","permanent");
                }else{
                    params.put("address_type","");
                }
                Log.e("method : ",method);
                Log.e("strEmpCode : ",strEmpCode);
                Log.e("String : ",str);
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
