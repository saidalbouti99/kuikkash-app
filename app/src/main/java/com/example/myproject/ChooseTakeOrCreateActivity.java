package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseTakeOrCreateActivity extends AppCompatActivity {

    Button btn_offerjob,btn_takejob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_take_or_create);

        btn_offerjob=findViewById(R.id.btn_offerjob_chooseact);
        btn_takejob=findViewById(R.id.btn_takejob_chooseact);

        btn_takejob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(ChooseTakeOrCreateActivity.this,JobAvailableListActivity.class);
                startActivity(intent2);
            }
        });

        btn_offerjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChooseTakeOrCreateActivity.this,JobCategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
