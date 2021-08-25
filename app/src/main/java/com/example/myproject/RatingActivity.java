package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RatingActivity extends AppCompatActivity {

    ImageView imgstudentpic;
    TextView tvName;
    RatingBar studentRate;
    Button btnConfirm;

    String imgInStr;
    String studentName;
    float numStar;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseUser user;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        imgstudentpic = findViewById(R.id.img_student_pic);
        tvName = findViewById(R.id.tv_name);
        studentRate = findViewById(R.id.rate_student);
        btnConfirm = findViewById(R.id.btn_confirm);

        mFirebaseAuth= FirebaseAuth.getInstance();
        user=mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=mFirebaseDatabase.getReference("Users");

        DatabaseReference tempref = databaseReference.child(user.getUid());

        tempref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                studentName=""+ ds.child("name").getValue();
                imgInStr=""+ds.child("image").getValue();

                tvName.setText(studentName);
                byte[] decodedStr = Base64.decode(imgInStr, Base64.DEFAULT);
                Bitmap decodedImg = BitmapFactory.decodeByteArray(decodedStr, 0 , decodedStr.length);

                if(decodedImg != null) {
                    decodedImg = getResizedBitmap(decodedImg, 250);
                    imgstudentpic.setImageBitmap(decodedImg);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numStar = studentRate.getRating();

                Toast.makeText(RatingActivity.this, "You have rated this freelancer " + numStar + " stars. Thank you for your feedback.", Toast.LENGTH_SHORT).show();

                Intent in = new Intent(RatingActivity.this, JobCategoryActivity.class);
                in.putExtra("studentRating", numStar );

                startActivity(in);

            }
        });

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
