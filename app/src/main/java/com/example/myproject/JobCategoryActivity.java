package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobCategoryActivity extends AppCompatActivity {
CardView cd_transport,cd_printing,cd_food,cd_laundry,cd_others;
ImageView img_profile;
String category,category2;
TextView tv_transport,tv_printing,tv_food,tv_laundry,tv_others;

DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_category);
        img_profile=findViewById(R.id.img_profile);
        tv_transport=findViewById(R.id.tv_transportation);

        databaseReference=FirebaseDatabase.getInstance().getReference("profile");

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JobCategoryActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        cd_transport=findViewById(R.id.cd_transport);

        cd_transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category=tv_transport.getText().toString();
                Intent intent=new Intent(JobCategoryActivity.this,JobDescriptionActivity.class);
                intent.putExtra("category",category);
                startActivity(intent);

            }
        });

        cd_printing=findViewById(R.id.cd_printing);
tv_printing=findViewById(R.id.tv_printing);
        cd_printing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category2=tv_printing.getText().toString();
                Intent intent2=new Intent(JobCategoryActivity.this,JobDescriptionActivity2.class);
                intent2.putExtra("category",category2);
                startActivity(intent2);

            }
        });

        cd_food=findViewById(R.id.cd_food);
        tv_food=findViewById(R.id.tv_food);
        cd_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category=tv_food.getText().toString();
                Intent intent=new Intent(JobCategoryActivity.this,JobDescriptionActivity3.class);
                intent.putExtra("category",category);
                startActivity(intent);

            }
        });

        cd_laundry=findViewById(R.id.cd_laundry);
        tv_laundry=findViewById(R.id.tv_laundry);
        cd_laundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category=tv_laundry.getText().toString();
                Intent intent=new Intent(JobCategoryActivity.this,JobDescriptionActivity4.class);
                intent.putExtra("category",category);
                startActivity(intent);

            }
        });

        cd_others=findViewById(R.id.cd_others);
        tv_others=findViewById(R.id.tv_others);
        cd_others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category=tv_others.getText().toString();
                Intent intent=new Intent(JobCategoryActivity.this,JobDescriptionActivity4.class);
                intent.putExtra("category",category);
                startActivity(intent);

            }
        });

        databaseReference=FirebaseDatabase.getInstance().getReference().child("profile");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Profile p=dataSnapshot1.getValue(Profile.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        byte[] decodedStr = Base64.decode(Document_img1, Base64.DEFAULT);
//        Bitmap decodedImg = BitmapFactory.decodeByteArray(decodedStr, 0 , decodedStr.length);

//        Bitmap data_profilepic=decodedImg;
//        if(decodedImg != null) {
//            data_profilepic.setImageBitmap(decodedImg);
//        }
    }


}
