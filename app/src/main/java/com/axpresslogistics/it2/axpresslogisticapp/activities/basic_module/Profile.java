package com.axpresslogistics.it2.axpresslogisticapp.activities.basic_module;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel.HRApprovalRes.HRApprovalResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel.ProfileResponse;
import com.axpresslogistics.it2.axpresslogisticapp.network.PosApiInterface;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.HRApprovalPresent.HRApprovalPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.Profile.ProfileImageUploadPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.Profile.ProfilePresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ImageUtils;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.HRApprovalView;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.ProfileEditableView;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.ProfileImageUploadView;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.ProfileView;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class Profile extends AppCompatActivity implements View.OnClickListener, ProfileView,
        ProfileEditableView, ProfileImageUploadView, HRApprovalView {
    String apikey = "";
    RequestBody nameType;
    byte[] byteArray;
    MainPresenter presenter;
    EditText edtName, edtEmpCode, edtContactNo, edtDesignation, edtDept, edtBranch,
            edtBranchCode,
            edtEmail, edtFatherName, edtDOB, edtBOP, edtDOJ, edtQulification, edtAdharCard,
            edtAddress, edtPAN, edtUAN, edtESI, edtBankAccount, edtBankName, edtBankIFSC;
    TextView txtName, txtEmpId, txt_address_update_status, txt_bank_account_update_status,
            txt_bank_name_update_status,
            txt_bank_ifsc_update_status;
    String
            strAddress,  strBankAccount, strBankName, strBankIFSC, strImageURL;
    ImageView edtAddressBtn, edtBankAccountBtn, edtBankNameBtn, edtIFSCBtn, userImageView, editImage;
    String title, method, str;
    Bitmap bitmap;
    byte[] image = null;
    boolean hasImage = false;
    int GALLERY = 1234;
    ImageButton backbtn_toolbar, savebtn_toolbar;
    ProgressDialog progressDialog;
    String methodE;

    Boolean FLAG = false;
    ApiKey obj = new ApiKey();
    MultipartBody.Part body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Profile");
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        savebtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_refresh));
        setView();
        apikey = obj.saltStr();
        employee_profileApi();

    }

    private void employee_profileApi() {
        presenter = new ProfilePresenterImpl(this);
        presenter.init();
    }

    private void setView() {
        userImageView = findViewById(R.id.user_imageId);
        userImageView.setOnClickListener(this);
        editImage = findViewById(R.id.useredit_imageId);
        editImage.setOnClickListener(this);
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
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                refresh();
                break;
            case R.id.useredit_imageId:
                showPictureDialog();
                break;
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

        }
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
                                    if (ContextCompat.checkSelfPermission(Profile.this,
                                            Manifest.permission.READ_EXTERNAL_STORAGE)
                                            != PackageManager.PERMISSION_GRANTED) {
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(Profile.this,
                                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                        } else {
                                            ActivityCompat.requestPermissions(Profile.this,
                                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                                            choosePhotoFromGallary();
                                        }
                                    } else {
                                        ActivityCompat.requestPermissions(Profile.this,
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

    private void choosePhotoFromGallary() {
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
                    Preferences.setPreference(Profile.this, CONSTANT.imagePath, path);
                    bitmap = ImageUtils.getInstant().getCompressedBitmap(path);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    image = byteArrayOutputStream.toByteArray();
                    Log.e("setimage", path);
                    userImageView.setImageBitmap(bitmap);
                    hasImage = true;
                    UpdateProfile(uri, path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void UpdateProfile(Uri uri, String path) {
        method = "image";
        apikey = obj.saltStr();

        try {

            if (byteArray != null) {
                File filesDir = getApplicationContext().getFilesDir();
                File file = new File(filesDir, "image" + ".png");
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                ProgressRequestBody fileBody = new ProgressRequestBody(file, (ProgressRequestBody.UploadCallbacks) this);
                body = MultipartBody.Part.createFormData("upload", file.getName(), fileBody);
                nameType = RequestBody.create(MediaType.parse("text/plain"), "image");

                presenter = new ProfileImageUploadPresenterImpl(this);
                presenter.init();

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = Profile.this.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void showChangeProfileDialog(String title, final String field_detail, final String methodE) {
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
                if (methodE.equals("address")) {
                    txt_address_update_status.setVisibility(View.VISIBLE);
                } else if (methodE.equals("bank_account")) {
                    txt_bank_account_update_status.setVisibility(View.VISIBLE);
                } else if (methodE.equals("bank_name")) {
                    txt_bank_account_update_status.setVisibility(View.VISIBLE);
                } else if (methodE.equals("bank_ifsc")) {
                    txt_bank_account_update_status.setVisibility(View.VISIBLE);
                }
                postUpdateFunction(str, methodE);
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

    private void postUpdateFunction(String str, String method) {
        this.str = str;
        this.methodE = method;
        presenter = new HRApprovalPresenterImpl(this);
        presenter.init();
    }

    private void refresh() {
        employee_profileApi();
    }

    @Override
    public String getMethodEditable() {
        return methodE;
    }

    @Override
    public String getEmpid() {
        return Preferences.getPreference(this, CONSTANT.EMPID);
    }

    @Override
    public String getMethod() {
        return "employee_info";
    }

    @Override
    public String getEmplid() {
        return Preferences.getPreference(Profile.this, CONSTANT.EMPID);
    }

    @Override
    public String getHRMethod() {
        return methodE;
    }

    @Override
    public String getKey() {
        return apikey;
    }

    @Override
    public MultipartBody.Part getImage() {
        return body;
    }

    @Override
    public RequestBody getType() {
        return nameType;
    }

    @Override
    public String getData() {
        return str;
    }

    @Override
    public String getAddressType() {
        if (method.equals("address")) {
            return "permanent";
        } else {
            return "correspondent";
        }
    }

    @Override
    public void showLoadingLayout() {
        progressDialog = new ProgressDialog(Profile.this) {
            @Override
            public void onBackPressed() {
                progressDialog.dismiss();
                finish();
            }
        };
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        progressDialog.show();

    }

    @Override
    public void hideLoadingLayout() {
        progressDialog.dismiss();
    }

    @Override
    public void showSuccess(Object object) {
        if (object instanceof ProfileResponse) {
            ProfileResponse myprofileResponse = ((ProfileResponse) object);
            Log.e("Response", new Gson().toJson(myprofileResponse));
            if (myprofileResponse.getStatus().equals(CONSTANT.TRUE)) {
                // ProjectUtil.customToast(this, "Successfull");
                edtName.setText((CharSequence) myprofileResponse.getEmployeeName().trim());
                edtEmpCode.setText((CharSequence) myprofileResponse.getEmplid().trim());
                edtContactNo.setText((CharSequence) myprofileResponse.getEmployeeContact());
                edtDesignation.setText((CharSequence) myprofileResponse.getEmployeeDesignation());
                edtDept.setText((CharSequence) myprofileResponse.getEmployeeDepartment());
                edtBranch.setText((CharSequence) myprofileResponse.getEmployeeBranch());
                edtBranchCode.setText((CharSequence) myprofileResponse.getEmployeeBranch());
                edtEmail.setText((CharSequence) myprofileResponse.getEmployeeEmail());
                edtFatherName.setText((CharSequence) myprofileResponse.getEmployeeFatherName());
                edtDOB.setText((CharSequence) myprofileResponse.getEmployeeDob());
                edtBOP.setText((CharSequence) myprofileResponse.getEmployeeBirthState());
                edtDOJ.setText((CharSequence) myprofileResponse.getEmployeeDob());
                edtQulification.setText((CharSequence) myprofileResponse.getEmployeeQualification());
                edtAdharCard.setText((CharSequence) myprofileResponse.getEmployeeAdhaarno());
                edtAddress.setText((CharSequence) myprofileResponse.getEmployeeAddress());
                edtPAN.setText((CharSequence) myprofileResponse.getEmployeePanno());
                edtUAN.setText((CharSequence) myprofileResponse.getEmployeeUanno());
                edtESI.setText((CharSequence) myprofileResponse.getEmployeeEsicno());
                edtBankAccount.setText((CharSequence) myprofileResponse.getEmployeeBankac());
                edtBankName.setText((CharSequence) myprofileResponse.getEmployeeBankname());
                edtBankIFSC.setText((CharSequence) myprofileResponse.getEmployeeIfsccode());
                txtEmpId.setText(Preferences.getPreference(this, CONSTANT.EMPID));
                txtName.setText((CharSequence) myprofileResponse.getEmployeeName());
                strImageURL = Preferences.getPreference(this, CONSTANT.USER_IMAGE);
                try {
                    Picasso.get().load(strImageURL).memoryPolicy(MemoryPolicy.NO_CACHE)
                            .networkPolicy(NetworkPolicy.NO_CACHE).into(userImageView);
                } catch (Exception e) {
                    e.printStackTrace();
                    Picasso.get().load("http://webapi.axpresslogistics.com/image/avatar.png").memoryPolicy(MemoryPolicy.NO_CACHE)
                            .networkPolicy(NetworkPolicy.NO_CACHE).into(userImageView);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Data not available!", Toast.LENGTH_SHORT).show();
            }
        }
        else if(object instanceof HRApprovalResponse){
            HRApprovalResponse hrApprovalResponse = (HRApprovalResponse) object;
            Log.e("Response",new Gson().toJson(hrApprovalResponse));
            if(hrApprovalResponse.getStatus().equals(CONSTANT.TRUE)){
                refresh();

                if (hrApprovalResponse.getStatus().equals(CONSTANT.VERIFIED)) {
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
                } else if (hrApprovalResponse.getStatus().equals(CONSTANT.NOT_VERIFIED)) {
                    txt_address_update_status.setText(CONSTANT.PENDING);
                }

            }else {
                Toast.makeText(getApplicationContext(), "Data not available!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(), CONSTANT.serverNotReachable, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}
