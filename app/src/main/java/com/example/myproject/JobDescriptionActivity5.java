package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class JobDescriptionActivity5 extends AppCompatActivity {

    private static final String TAG = "SpecifyJobGender";



    private Button btn_confirmprice;
    private EditText et_time,et_price,et_jobdesc,et_jobtitle;
    TextView tv_date;
    String category;
    TextView tv_jobcategory;
    DatabaseReference databaseReference;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_description);

        btn_confirmprice=findViewById(R.id.btn_confirmationjob_speact);
        tv_date=findViewById(R.id.tv_date_descact);
        et_jobdesc=findViewById(R.id.et_description_desact);
        et_jobtitle=findViewById(R.id.et_jobtitle_descact);
        et_price=findViewById(R.id.et_price_descact);
        et_time=findViewById(R.id.et_time_descact);
        tv_jobcategory=findViewById(R.id.tv_jobcategory);

        Intent intent=getIntent();
        category=intent.getStringExtra("category");

        tv_jobcategory.setText(category);

        databaseReference= FirebaseDatabase.getInstance().getReference("jobData");


        btn_confirmprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(et_jobtitle.getText().toString().isEmpty()) && !(et_jobdesc.getText().toString().isEmpty()) && !(et_time.getText().toString().isEmpty())) {

                    Intent intent = new Intent(JobDescriptionActivity5.this, RatingActivity.class);

                    startActivity(intent);
                    addJobs();
                }
                if((et_jobtitle.getText().toString().isEmpty())){

                    et_jobtitle.setError("Cannot be empty");

                }
                if((et_jobdesc.getText().toString().isEmpty())){

                    et_jobdesc.setError("Cannot be empty");

                }
//                   if(TextUtils.isEmpty(price)){
//
//                       et_price.setError("Cannot be empty");
//
//                   }
                if((et_time.getText().toString().isEmpty())){

                    et_time.setError("Cannot be empty");

                }
//                   if((tv_date.getText().toString().isEmpty())){
//
//                       tv_date.setError("Cannot be empty");
//
//                   }


            }
        });






        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        JobDescriptionActivity5.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
//
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                Log.d(TAG, "onDateSet: yyy/mm/dd: " + year + "/" + month + "/" + dayOfMonth);

                String date = month + "/" + dayOfMonth + "/" + year;
                tv_date.setText(date);
            }
        };
    }



    public void addJobs(){
        String data_jobtitle=et_jobtitle.getText().toString();
        String data_jobdesc=et_jobdesc.getText().toString();
        String data_jobdate=tv_date.getText().toString();
        String data_jobtime=et_time.getText().toString();
        String data_jobprice=et_price.getText().toString();
        String data_jobcat=tv_jobcategory.getText().toString();

        if (!TextUtils.isEmpty(data_jobtitle)   && !TextUtils.isEmpty(data_jobtime) && !TextUtils.isEmpty(data_jobdesc)){
            String id=databaseReference.push().getKey();

            Job jobData=new Job(id,data_jobcat,data_jobtitle,data_jobdesc,data_jobdate,data_jobtime,data_jobprice);
            databaseReference.child(id).setValue(jobData);
            tv_jobcategory.setText("");
            et_jobtitle.setText("");
            et_jobdesc.setText("");
            tv_date.setText("");
            et_time.setText("");
            et_price.setText("");


        }
        else{
            Toast.makeText(JobDescriptionActivity5.this,"vannot be empty",Toast.LENGTH_SHORT).show();

        }
    }
}

