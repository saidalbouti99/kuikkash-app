package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    TextView textTitle;
    TextView JobCategory;
    TextView JobDesc;
    TextView JobDate;
    TextView JobPrice;
Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textTitle =findViewById(R.id.tv_name_detail);
        JobCategory =findViewById(R.id.tv_jobcategory_detail);
        JobDesc =findViewById(R.id.tv_jobdescription_detail);
        JobDate =findViewById(R.id.tv_deadline_detail);
        JobPrice =findViewById(R.id.tv_price_detail);
        btn_confirm=findViewById(R.id.btn_confirmjob_detail);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailActivity.this,ChooseTakeOrCreateActivity.class);
                startActivity(intent);
                Toast.makeText(DetailActivity.this,"You have successfully take this job.",Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();

        String jobtitle = intent.getStringExtra("title");
        textTitle.setText(jobtitle);

        String jobcategory = intent.getStringExtra("category");
        JobCategory.setText(jobcategory);

        String jobdescription = intent.getStringExtra("description");
        JobDesc.setText(jobdescription);

        String jobdate = intent.getStringExtra("Date");
        JobDate.setText(jobdate);

        String jobprice = intent.getStringExtra("Price");
        JobPrice.setText(jobprice);
    }
}
