package com.axpresslogistics.it2.axpresslogisticapp.activities.basic_module;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel.LoginResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.Login.LoginPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.LoadingLayout;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.NativeHelper;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.LoginView;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity implements LoginView {
    String TITLE = CONSTANT.NEED_STORAGE_PERMISSION;
    String MESSAGE = CONSTANT.APP_NEED_PERMISSION;
    int MY_PERMISSION_REQUEST = 1001;
    int MY_PERMISSION_SETTING = 101;
    String[] permissiosRequired = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE};
    LoadingLayout loadingLayout = new LoadingLayout();
    private SharedPreferences permissionStatus;
    private MainPresenter presenter;
    String refreshed_token = "";

    @BindView(R.id.input_employee_id)
    EditText user;

    @BindView(R.id.input_password_id)
    EditText pass;

    @BindView(R.id.forget_password_linkId)
    TextView forgetpassword;

    @BindView(R.id.get_imei_no_btn)
    TextView imei_num;

    String imei_no = "", imei_no1 = "";
    String imei_codeStr = "";
    String imei_code = "";

    String key = "";

    String username;
    String password;
    Button loginbtn;
    Boolean flag = true;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        refreshed_token = Preferences.getPreference(getApplicationContext(), CONSTANT.REFRESHED_TOKEN);
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        getImeiNo();
        final ApiKey apiKey = new ApiKey();
        key = apiKey.saltStr();
        if (!checkPermisson()) {
            callPermissionGranted();
        }
    }

    private boolean checkPermisson() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void callPermissionGranted() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissiosRequired[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissiosRequired[1])
                || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissiosRequired[2])
                || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissiosRequired[3])
                || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissiosRequired[4])
                || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissiosRequired[5])) {
            showDialogMessage(TITLE, MESSAGE, MY_PERMISSION_REQUEST);
        } else if (permissionStatus.getBoolean(permissiosRequired[0], false)) {
            showDialogMessage(TITLE, MESSAGE, MY_PERMISSION_SETTING);
        } else {
            ActivityCompat.requestPermissions(this, permissiosRequired, MY_PERMISSION_REQUEST);
        }

        SharedPreferences.Editor editor = permissionStatus.edit();
        editor.putBoolean(permissiosRequired[0], true);
        editor.commit();
    }

    private void showDialogMessage(String title, String message, final int event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(CONSTANT.GRANT, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                if (event == MY_PERMISSION_REQUEST) {
                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
                } else if (event == MY_PERMISSION_SETTING) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts(CONSTANT.PACKAGE, getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, MY_PERMISSION_SETTING);
                    Toast.makeText(getBaseContext(), CONSTANT.GO_TO_PERMISSION_TO_GRANT, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST) {
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissiosRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissiosRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissiosRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissiosRequired[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissiosRequired[4])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissiosRequired[5])) {
                showDialogMessage(TITLE, MESSAGE, MY_PERMISSION_REQUEST);
            } else {
                Toast.makeText(getBaseContext(), CONSTANT.UNABLE_GET_PERMISSION, Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick({R.id.btn_login, R.id.get_imei_no_btn, R.id.forget_password_linkId})
    public void proceed(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                getImeiNo();
                if (flag.equals(true)) {
                    if (isVaild()) {
                        if (NativeHelper.isConnectionAvailable(LoginActivity.this)) {
                            presenter = new LoginPresenterImpl(this);
                            presenter.init();

                        } else {
                            Toast.makeText(this, CONSTANT.UNABLE_TO_CONNECT, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;
            case R.id.forget_password_linkId:
                startActivity(new Intent(this, ResetPasswordActivity.class));
                break;
            case R.id.get_imei_no_btn:
                //  ProjectUtil.customToast(LoginActivity.this, "check your internet connection");
                getImeiNo();
                if (!imei_no1.equals("")) {
                    imei_code = "imei no 1 : " + imei_no + "\n" + "imei no 2 : " + imei_no1;
                    imei_num.setText(imei_code);
                } else {
                    imei_num.setText(imei_no);
                    imei_codeStr = imei_no;
                }
                break;
        }
    }



    @SuppressLint("HardwareIds")
    private void getImeiNo() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
//                    if (tm.getDeviceId().length() > 1) {
//                        imei_no = tm.getDeviceId(0);
//                        imei_no1 = tm.getDeviceId(1);
//                        imei_codeStr = imei_no + "-" + imei_no1;
//                        Log.e("TM.getDeviceId(0)", imei_no);
//                    } else {
                    imei_codeStr = tm.getDeviceId();
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                imei_no = tm.getDeviceId();
                Log.e("TM.GETDEVICE", imei_no);
                imei_codeStr = imei_no;
            }
            Toast.makeText(getApplicationContext(), "Imei no not found!", LENGTH_SHORT).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (tm.getDeviceId().length() > 1) {
                    imei_no = tm.getDeviceId(0);
                    imei_no1 = tm.getDeviceId(1);
                    imei_codeStr = imei_no + "-" + imei_no1;
                    Log.e("TM.getDeviceId(0)", imei_no);
                } else {
                    imei_no = tm.getDeviceId();
                }
            } else {
                if (Build.VERSION.SDK_INT >= 26) {
                    imei_no = tm.getImei();
                    Log.e("TM.tm.getImei()", imei_no);
                } else {
                    imei_no = tm.getDeviceId();
                    Log.e("TM.GETDEVICE", imei_no);
                    imei_codeStr = imei_no;
                }
            }
        }
    }

    private boolean isVaild() {
        username = user.getText().toString().trim();
        password = pass.getText().toString().trim();
        if (username.length() == 0) {
            Toast.makeText(LoginActivity.this, CONSTANT.ENTER_ADMIN_ID, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() == 0) {
            Toast.makeText(LoginActivity.this, CONSTANT.ENTER_PASSWORD, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String getMethod() {
        return "login";
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getimei() {
        if (imei_codeStr != null || !imei_codeStr.equals("")) {
            return imei_codeStr;
        } else {
            return imei_no;
        }

    }

    @Override
    public String getrefreshtoken() {
        return refreshed_token;
    }

    @Override
    public String getkey() {
        return key;
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
        if (object instanceof LoginResponse) {
            Log.e("Response", new Gson().toJson(object));
            LoginResponse loginResponse = ((LoginResponse) object);
            if (loginResponse.getStatus().equals(CONSTANT.TRUE) && loginResponse.getImeiStatus().equals(CONSTANT.TRUE)) {

                Preferences.setPreference(LoginActivity.this, CONSTANT.USER_NAME,
                        loginResponse.getEmployeeName());
                Preferences.setPreference(LoginActivity.this, CONSTANT.EMPLOYEE_BRANCH,
                        loginResponse.getEmployeeBranch());
                Preferences.setPreference(LoginActivity.this, CONSTANT.EMPID,
                        loginResponse.getEmplid());
                Preferences.setPreference(LoginActivity.this, CONSTANT.EMAIL,
                        loginResponse.getEmail());
                Preferences.setPreference(LoginActivity.this, CONSTANT.EMPLOYEE_DESIGNATION,
                        loginResponse.getEmployeeDesignation());
                Preferences.setPreference(LoginActivity.this, CONSTANT.EMPLOYEE_DEPT,
                        loginResponse.getEmployeeDepartment());
                Preferences.setPreference(LoginActivity.this, CONSTANT.USER_IMAGE,
                        loginResponse.getProfileImage());
                Preferences.setPreference(LoginActivity.this, CONSTANT.IMAGEURL,
                        loginResponse.getImageURL());
                Preferences.setPreference(LoginActivity.this, CONSTANT.SUPERVISER_ID,
                        loginResponse.getSupervisiorId());
                Toast.makeText(getApplicationContext(), "Hi  " + loginResponse.getEmployeeName(),
                        LENGTH_SHORT).show();

                startActivity(new Intent(LoginActivity.this, MainHomeActivity.class));
                finish();

            } else if (loginResponse.getStatus().equals(CONSTANT.TRUE) && loginResponse.getImeiStatus().equals(CONSTANT.FALSE)) {

                Toast.makeText(getApplicationContext(), "Kindly register your IMEI no,kindly contact IT Dept.",
                        LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Please check your ID and password",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            if (object instanceof NetworkError) {
            } else if (object instanceof ServerError) {
                Toast.makeText(getBaseContext(),
                        CONSTANT.RESPONSEERROR,
                        Toast.LENGTH_LONG).show();
            } else if (object instanceof AuthFailureError) {
            } else if (object instanceof ParseError) {
            } else if (object instanceof TimeoutError) {
                Toast.makeText(getBaseContext(),
                        CONSTANT.TIMEOUT_ERROR,
                        Toast.LENGTH_LONG).show();
            } else {
            }
        }
    }


    @Override
    public void showFailure(String error) {
        if (error.equalsIgnoreCase("Auto LoginActivity Error")) {
            Toast.makeText(getApplicationContext(), "Some Error Occured while login", LENGTH_SHORT).show();
            loadingLayout.hideLoadingLayout();
        } else {
            Toast.makeText(getApplicationContext(), error, LENGTH_SHORT).show();
            loadingLayout.hideLoadingLayout();
        }
    }
}
