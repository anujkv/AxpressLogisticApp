package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.PODScan;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.PODScanResponse.PodScanResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.PODScanPresenter.PodScanPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.DateFunction;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ImageConverter;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ImageUtils;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.LoadingLayout;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.PODScanV.PODScannerView;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;

public class PODScanner extends AppCompatActivity implements View.OnClickListener,
        PODScannerView {

    private ImageView cropImageView, imageLogo;
    private Button uploadBtn;
    private EditText inputEdt;
    private Uri resultUri;
    private ProgressBar mProgressBar;
    private MainPresenter presenter;
    private String dbname;
    private ProgressDialog progressDialog;
    private TextView click_msg_txt;
    ImageConverter imageConverter = new ImageConverter();
    Bitmap bitmap;
    DateFunction dateFunction = new DateFunction();
    ImageButton backbtn_toolbar;
    String imageType;
    private Uri filePath;
    LoadingLayout loadingLayout = new LoadingLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podscanner);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.POD_SCAN);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);

        cropImageView = findViewById(R.id.cropImageView);
        click_msg_txt = findViewById(R.id.click_msg_txt);
        uploadBtn = findViewById(R.id.uploadbtn);
        inputEdt = findViewById(R.id.input_edt);
        imageLogo = findViewById(R.id.logoicon);
        uploadBtn.setOnClickListener(this);
        cropImageView.setOnClickListener(this);
        mProgressBar = findViewById(R.id.mProgressbar);
        Intent intent = getIntent();
        dbname = intent.getStringExtra("db_name");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                filePath = data.getData();
//                Log.e("FilePath",new Gson().toJson(filePath));
                resultUri = result.getUri();
//                Log.e("URI ",new Gson().toJson(resultCode));
                String path = resultUri.getPath();
                bitmap = ImageUtils.getInstant().getCompressedBitmap(path);
                cropImageView.setImageURI(resultUri);
                click_msg_txt.setVisibility(View.GONE);
                imageLogo.setVisibility(View.GONE);
//                try {
//                    Bitmap Imagebitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
//                    Log.e("imageBitmap",new Gson().toJson(Imagebitmap));
//                } catch (Exception e) {
//                    //handle exception
//                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cropImageView:
                CropImage.activity()
                        .start(PODScanner.this);
                break;

            case R.id.uploadbtn:
                if(isCheck()){
                    uploadPODfile();
                }else{
                    Toast.makeText(getApplicationContext(),"Enter the docket no",Toast.LENGTH_SHORT).show();
                }
//                finish();
                break;

            case R.id.backbtn_toolbar:
                finish();
                break;

        }
    }

    private String getFileExtinsion(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadPODfile() {
        if(resultUri!=null){
            presenter = new PodScanPresenterImpl(this);
            presenter.init();
        }
        else {
            Toast.makeText(getApplicationContext(),"No image file here",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isCheck() {
        return !inputEdt.getText().toString().trim().equals("");
    }

    @Override
    public String getImage() {
        return imageConverter.BitMapToString(bitmap);
    }

    @Override
    public String getDocketNo() {
        return inputEdt.getText().toString().trim();
    }

    @Override
    public String getEmplid() {
        return Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
    }


    @Override
    public String getImageType() {
        return imageType;
    }

    @Override
    public String getDBName() {
        return dbname;
    }

    @Override
    public void showLoadingLayout() {
//        progressDialog = new ProgressDialog(PODScanner.this) {
//            @Override
//            public void onBackPressed() {
//                progressDialog.dismiss();
//            }
//        };
//        progressDialog.setMessage("Loading");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        loadingLayout.showLoadingLayout(this);
    }

    @Override
    public void hideLoadingLayout() {
        loadingLayout.hideLoadingLayout();
    }

    @Override
    public void showSuccess(Object o) {
        if(o instanceof PodScanResponse){
            PodScanResponse podScanResponse = (PodScanResponse) o;
            if(podScanResponse.getStatus().equals(CONSTANT.TRUE)){

                Toast.makeText(getApplicationContext(), "POD "+ podScanResponse.getMsg() ,Toast.LENGTH_SHORT).show();
                visibility();
            }else{
                Toast.makeText(getApplicationContext(), "POD not save, "+ podScanResponse.getMsg() ,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void visibility() {
        click_msg_txt.setVisibility(View.VISIBLE);
        imageLogo.setVisibility(View.VISIBLE);
        cropImageView.setImageResource(android.R.color.transparent);
        inputEdt.setText("");
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(),CONSTANT.server_error,Toast.LENGTH_SHORT).show();
        loadingLayout.hideLoadingLayout();
    }

}
