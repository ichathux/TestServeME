package com.example.isurubandara.testserveme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button login;
    TextView register;
    EditText username, password;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), DisplayMainActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        login = (Button) findViewById(R.id.lg_login);
        register = (TextView) findViewById(R.id.lg_register);
        username = (EditText) findViewById(R.id.lg_uname);
        password = (EditText) findViewById(R.id.lg_password);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == register){

            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        }
        if (v == login){
            userLogin();
        }
    }

    private void userLogin() {
        String uname = username.getText().toString().trim();
        String pword = password.getText().toString().trim();

        if (TextUtils.isEmpty(uname)){
            Toast.makeText(this,"Please enter username",Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(pword)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT);
            return;
        }

        progressDialog.setMessage("Login User..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(uname,pword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), DisplayMainActivity.class));
                }
            }
        });
    }
}
