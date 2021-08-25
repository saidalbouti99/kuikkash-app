package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobAvailableListActivity extends AppCompatActivity {

     RecyclerView recyclerView;
     LinearLayoutManager linearLayoutManager;
     RecyclerViewAdapter recyclerViewAdapter;
     DatabaseReference databaseReference2;
    ArrayList<Job> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_available_list);

        recyclerView=findViewById(R.id.rv_job_list);
        linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        list=new ArrayList<Job>();
        databaseReference2= FirebaseDatabase.getInstance().getReference().child("jobData");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Job j= dataSnapshot1.getValue(Job.class);
                    list.add(j);
                }

                recyclerViewAdapter= new RecyclerViewAdapter(JobAvailableListActivity.this,list);
                recyclerView.setAdapter(recyclerViewAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(JobAvailableListActivity.this,"Sorry there is an error.Try again",Toast.LENGTH_SHORT).show();


            }
        });

    }

}
