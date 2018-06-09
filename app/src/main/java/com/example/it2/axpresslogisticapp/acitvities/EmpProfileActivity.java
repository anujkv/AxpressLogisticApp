package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.it2.axpresslogisticapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class EmpProfileActivity extends AppCompatActivity {
    EditText edtName,edtEmpCode,edtContactNo,edtDesignation,edtDept,edtBranch,edtBranchCode,
                edtEmail,edtFatherName,edtDOB,edtBOP,edtDOJ,edtQulification,edtAdharCard,
                edtAddress,edtPAN,edtUAN,edtESI,edtBankAccount,edtBankName,edtBankIFSC;
    TextView txtName,txtEmpId;
    String strName,strEmpCode,strContactNo,strDesignation,strDept,strBranch,strBranchCode,
            strEmail,strFatherName,strDOB,strBOP,strDOJ,strQulification,strAdharCard,
            strAddress,strPAN,strUAN,strESI,strBankAccount,strBankName,strBankIFSC;
    Intent intent;
    String jsonString;
    JSONObject jObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_profile);

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

        try {
            intent = getIntent();
            jsonString = intent.getStringExtra("response");
            jObj = new JSONObject(jsonString);

            //get employee info...
            strName = jObj.optString("Employee_Name");
            strEmpCode = jObj.optString("Emplid");
            strContactNo = jObj.optString("Employee_Contact");
            strDesignation = jObj.optString("Employee_Designation");
            strDept = jObj.optString("Employee_Department");
            strBranch = jObj.optString("Employee_Email");
            strBranchCode = jObj.optString("Employee_Branch");
            strEmail = jObj.optString("Employee_Email");
            //get employee personal info..
            strFatherName = jObj.optString("Employee_Father_Name");
            strDOB = jObj.optString("Employee_DOB");
            strBOP = jObj.optString("Employee_birth_state");
            strDOJ = jObj.optString("Employee_DOJ");
            strQulification = jObj.optString("Employee_Qualification");
            strAdharCard = jObj.optString("Employee_AdhaarNo");
            strAddress = jObj.optString("Employee_Address");
            //get employee bank/imp info..
            strPAN = jObj.optString("Employee_PanNo");
            strUAN = jObj.optString("Employee_UanNO");
            strESI = jObj.optString("Employee_EsicNo");
            strBankAccount = jObj.optString("Employee_BanckAC");
            strBankName = jObj.optString("Employee_BankName");
            strBankIFSC = jObj.optString("Employee_IfscCode");

            //#########################################################//

            txtName.setText(strName.trim());
            txtEmpId.setText(strEmpCode.trim().toString());
            //put employee info value..
            edtName.setText(strName.trim());
            edtEmpCode.setText(strEmpCode.trim());
            edtContactNo.setText(strContactNo.trim());
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




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
