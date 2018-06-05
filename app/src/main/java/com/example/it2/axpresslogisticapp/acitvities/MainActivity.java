package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.it2.axpresslogisticapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button login, signup;
    //google auth...
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;

    /*@Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.btn_signup);
        signup.setText(getString(R.string.signup));

//        auth = FirebaseAuth.getInstance();
//        //google logged In condition checking , its is already logged In or not...
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if(firebaseAuth.getCurrentUser() != null){
//                    startActivity(new Intent(MainActivity.this,MainHomeActivity.class));
//                }
//            }
//        };


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }
}
