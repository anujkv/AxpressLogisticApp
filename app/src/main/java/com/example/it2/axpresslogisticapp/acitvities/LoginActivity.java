package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener {

    EditText employee_code, password;
    Button login_button;
    TextView redirectToRegistrationActivity;
    String employeeCodeValue, passwordValue;
    Bundle bundle;

    //google signIn button..
    SignInButton signInButton;
    FirebaseAuth auth;
    private final static int RC_SIGN_IN = 2;
    GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient googleApiClient;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        employee_code = findViewById(R.id.input_employee_id);
        password = findViewById(R.id.input_password_id);
        redirectToRegistrationActivity = findViewById(R.id.redirectRegistrationPage_btnId);
        login_button = findViewById(R.id.btn_login);
        signInButton = findViewById(R.id.google_signIn_btn);

        login_button.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        redirectToRegistrationActivity.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(LoginActivity.this,MainHomeActivity.class));
                    finish();
                }
            }
        };

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(),
                                "User not logged In, Something went wrong", LENGTH_SHORT)
                                .show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();


//        // Build a GoogleSignInClient with the options specified by gso.
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Authentication Failed!!", LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"User logged In successfully!!", LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"Authentication failed....", LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private  void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //set click event on login button, check condition abd redirect it...
            case R.id.btn_login :
                employeeCodeValue = employee_code.getText().toString().trim();
                passwordValue = password.getText().toString().trim();
                userLogin(employeeCodeValue,passwordValue);
                break;

            //google signIn btn for google signIn....
            case R.id.google_signIn_btn:
                signIn();
                break;

            //set click event on signUp link for redirection on registration page activity...
            case R.id.redirectRegistrationPage_btnId:
                userRegistration();
                break;
        }
    }

    //Function of userRegistration....
    private void userRegistration() {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        finish();
    }
    //function of userLogin.....
    private void userLogin(String employeeCodeValue, String passwordValue) {
        this.employeeCodeValue = employeeCodeValue;
        this.passwordValue = passwordValue;
        if (this.employeeCodeValue.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter the employee code", LENGTH_SHORT).show();
        } else if (this.passwordValue.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter the password", LENGTH_SHORT).show();
        } else {
            bundle = new Bundle();
            bundle.putString("empcode",employeeCodeValue);
            bundle.putString("empPassword",passwordValue);
            Toast.makeText(getApplicationContext(), "Login Successfully!!!", LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), MainHomeActivity.class);
            i.putExtras(bundle);
            startActivity(i);

//            startActivity(new Intent(LoginActivity.this, MainHomeActivity.class));
            finish();
        }
    }
}
