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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private EditText uname, pword, cpword;
    private TextView login;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), WallActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        register = (Button) findViewById(R.id.rg_register);
        uname = (EditText) findViewById(R.id.rg_uname);
        pword = (EditText) findViewById(R.id.rg_password);
        login = (TextView) findViewById(R.id.rg_login);
        cpword = (EditText) findViewById(R.id.rg_compassword);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == register){
            registerUser();
        }
        if (v == login){
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    private void registerUser() {

        String email = uname.getText().toString().trim();
        String password = pword.getText().toString().trim();
        String cpassword = cpword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter username",Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT);
            return;
        }
        if (!password.equals(cpassword)){
            Toast.makeText(this,"Please check your password",Toast.LENGTH_SHORT);
            return;
        }

        progressDialog.setMessage("Registering User..");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registering Successfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),WallActivity.class));
                }else {
                    Toast.makeText(RegisterActivity.this, "Registering fail or username already in use. Try again.", Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
}
