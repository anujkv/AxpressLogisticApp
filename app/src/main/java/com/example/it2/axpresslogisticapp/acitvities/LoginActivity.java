package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;

public class LoginActivity extends AppCompatActivity {

    EditText employee_code, password;
    Button login_button;
    TextView redirectToRegistrationActivity;
    String employeeCodeValue, passwordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        employee_code = findViewById(R.id.input_employee_id);
        password = findViewById(R.id.input_password_id);
        redirectToRegistrationActivity = findViewById(R.id.redirectRegistrationPage_btnId);
        login_button = findViewById(R.id.btn_login);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                employeeCodeValue = employee_code.getText().toString().trim();
                passwordValue = password.getText().toString().trim();

                if (employeeCodeValue.isEmpty()) {
                    Toast.makeText(getApplication(), "Enter the employee code", Toast.LENGTH_SHORT).show();
                } else if (passwordValue.isEmpty()) {
                    Toast.makeText(getApplication(), "Enter the password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "Login Successfully!!!", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginActivity.this, OperationActivity.class));
                    finish();
                }
            }
        });

        redirectToRegistrationActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
            }
        });
    }
}
