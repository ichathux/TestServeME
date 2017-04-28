package com.example.isurubandara.testserveme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayMainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView name_v;
    ImageView avatarViewUser;
    DatabaseReference mDatabases1;
    RoundImage roundImage;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_main);

        name_v = (TextView) findViewById(R.id.v_name);
        avatarViewUser = (ImageView) findViewById(R.id.userAvatar);

        firebaseAuth = FirebaseAuth.getInstance();



        if (firebaseAuth.getCurrentUser()==null){


            name_v.setText("Guest");

            name_v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent i = new Intent(DisplayMainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            });

        }else {

            FirebaseUser user = firebaseAuth.getCurrentUser();
            mDatabases1 = FirebaseDatabase.getInstance().getReference().child("UserDetails").child(user.getUid()).child("fname");
            mDatabases1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.getValue().toString();
                    name_v.setText(name);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile);
        roundImage = new RoundImage(bitmap);
        avatarViewUser.setImageDrawable(roundImage);


    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        if (firebaseAuth.getCurrentUser()==null){

            menu.findItem(R.id.log_out).setTitle("Login");
            Intent i = new Intent(DisplayMainActivity.this, LoginActivity.class);
            startActivity(i);

        }

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.log_out:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));

                return true;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }


}
