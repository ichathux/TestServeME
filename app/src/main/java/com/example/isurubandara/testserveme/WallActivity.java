package com.example.isurubandara.testserveme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class WallActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 234;
    private FirebaseAuth firebaseAuth;
    private Button logout, submit,bgo;
    private TextView address, phone, about, fname, lname, dob;
    private DatabaseReference databaseReference;
    private Spinner spinner_utype;
    private ProgressDialog progressDialog1, progressDialogImage;
    private ImageButton avatar;
    private ImageView avatarView;
    private StorageReference mStorage;
    private Uri filePath;
    RoundImage roundImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);



        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("UserDetails");
        mStorage = FirebaseStorage.getInstance().getReference();



        avatar = (ImageButton) findViewById(R.id.rg_bimage);
        dob = (EditText) findViewById(R.id.rg_dob);
        address = (EditText) findViewById(R.id.rg_address);
        fname = (EditText) findViewById(R.id.rg_fname);
        lname = (EditText) findViewById(R.id.rg_lname);
        phone = (EditText) findViewById(R.id.rg_phone);
        about = (EditText) findViewById(R.id.rg_about);
        avatarView = (ImageView) findViewById(R.id.avatar);
        submit = (Button) findViewById(R.id.rg_submit);
        spinner_utype = (Spinner) findViewById(R.id.sUType);
        submit.setOnClickListener(this);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile);
        roundImage = new RoundImage(bitmap);
        avatarView.setImageDrawable(roundImage);

        avatar.setOnClickListener(this);

    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_IMAGE_REQUEST);
    }

    private void uploadFile(){

        if (filePath != null) {
            progressDialogImage = new ProgressDialog(this);
            progressDialogImage.setTitle("Uploading image...");
            progressDialogImage.show();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            StorageReference riversRef = mStorage.child("avatar/avatar"+user.getUid()+".jpg");

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialogImage.dismiss();
                            Toast.makeText(getApplicationContext(), "File uploaded", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialogImage.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred())/ taskSnapshot.getTotalByteCount();
                            progressDialogImage.setMessage(((int) progress) +"% uploaded..");
                        }
                    });


        }else {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                roundImage = new RoundImage(bitmap);
                avatarView.setImageDrawable(roundImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == submit){
            addUserDetails();

        }
        if (v == avatar){
            showFileChooser();
        }
    }



    public void addUserDetails(){

        FirebaseUser user = firebaseAuth.getCurrentUser();

        String stfname = fname.getText().toString().trim();
        String stlname = lname.getText().toString().trim();
        String stphone = phone.getText().toString().trim();
        String staddr = address.getText().toString().trim();
        String stabout = about.getText().toString().trim();
        String stdob = dob.getText().toString().trim();
        String sttype = spinner_utype.getSelectedItem().toString();
        String avatar = "avatar"+user.getUid()+".jpg";

        if (!TextUtils.isEmpty(stfname) ||!TextUtils.isEmpty(stlname)){

            String id = user.getUid().toString();


            UserInformation ui = new UserInformation(stfname, stlname,  staddr,  stphone,  stdob,  stabout, sttype, id, avatar);
            databaseReference.child(id).setValue(ui);

            Toast.makeText(this, "Saving data", Toast.LENGTH_SHORT).show();
            uploadFile();
            startActivity(new Intent(getApplicationContext(), DisplayMainActivity.class));

        }else {
            Toast.makeText(this, "Fill all values ", Toast.LENGTH_SHORT).show();
        }


    }


}
