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
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    EditText employee_code, password;
    Button login_button;
    TextView redirectToRegistrationActivity;
    String employeeCodeValue, passwordValue;

    //google signIn button..
    SignInButton signInButton;
    FirebaseAuth auth;
    private final static int RC_SIGN_IN = 2;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener authStateListener;
//    GoogleSignInApi googleSignInApi;


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

        auth = FirebaseAuth.getInstance();
        //google logged In condition checking , its is already logged In or not...
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    Toast.makeText(getApplicationContext(),"user already logged IN",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainHomeActivity.class));
                }
            }
        };

        //set click event on login button, check condition abd redirect it...
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                employeeCodeValue = employee_code.getText().toString().trim();
                passwordValue = password.getText().toString().trim();
                userLogin();
            }
        });


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        //Set click event on google_signIn_btn and check authentication and redirect...
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//                startActivityForResult(signInIntent, RC_SIGN_IN);
                signIn();
            }
        });

        //set click event on signUp link for redirection on registration page activity...
        redirectToRegistrationActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegistration();

            }
        });
    }


    //Google signIn process...
    // Configure Google Sign In
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
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
                            startActivity(new Intent(getApplication(),MainHomeActivity.class));
                            Toast.makeText(getApplicationContext(),"User logged In successfully!!",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"Try Again,Something wrong",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    private void userRegistration() {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        finish();
    }

    private void userLogin() {
        if (employeeCodeValue.isEmpty() && employeeCodeValue.length() == 7) {
            Toast.makeText(getApplication(), "Enter the employee code", Toast.LENGTH_SHORT).show();
        } else if (passwordValue.isEmpty()) {
            Toast.makeText(getApplication(), "Enter the password", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplication(), "Login Successfully!!!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(LoginActivity.this, MainHomeActivity.class));
            finish();
        }
    }
}
