package com.example.isurubandara.testserveme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    Button surf;
    TextView login, register;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), DisplayMainActivity.class));
        }

        surf = (Button) findViewById(R.id.wb_surf);

        login = (TextView) findViewById(R.id.wb_login);
        register = (TextView) findViewById(R.id.wb_register);

        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        surf.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(WelcomeActivity.this, DisplayMainActivity.class);
                startActivity(i);
            }
        });

    }
}
