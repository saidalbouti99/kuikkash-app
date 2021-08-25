package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText et_email_signup,et_password_signup;
    TextView tv_signin;

    Button btn_signup;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_signin=findViewById(R.id.tv_signin);

        et_email_signup=findViewById(R.id.et_email_mainact);
        et_password_signup=findViewById(R.id.et_password_mainact);
        btn_signup=findViewById(R.id.btn_signup_mainact);
        mFirebaseAuth=FirebaseAuth.getInstance();


        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent =new Intent(MainActivity.this, login_activity.class);

                startActivity(intent);


            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=et_email_signup.getText().toString();
                String password=et_password_signup.getText().toString();
                if(email.isEmpty()){
                    et_email_signup.setError("Please fill in your email.");
                    et_email_signup.requestFocus();
                }
                else if(password.isEmpty()){
                    et_password_signup.setError("You must fill in your password");
                    et_password_signup.requestFocus();
                }
                else if(email.isEmpty() && password.isEmpty()){
                    Toast.makeText(MainActivity.this,"No email and password entered",Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && password.isEmpty())){

                    mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser mFirebaseUser=mFirebaseAuth.getCurrentUser();
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Your signup is unsuccessfull,please try again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                               String email= mFirebaseUser.getEmail();
                                String uid=mFirebaseUser.getUid();

                                HashMap<Object,String> hashMap=new HashMap<>();// to store user info in realtime database


                                hashMap.put("email",email);//put info in hashmap
                                hashMap.put("uid",uid);
                                hashMap.put("name","");
                                hashMap.put("phone","");
                                hashMap.put("image","");
                                hashMap.put("matricno","");
                                hashMap.put("gender", "");

                                FirebaseDatabase database=FirebaseDatabase.getInstance();//firebase db instance

                                DatabaseReference databaseReference=database.getReference("Users");//path to store user data named "Users"
                                //put data within hashmap indb
                                databaseReference.child(uid).setValue(hashMap);

                                Intent intent2=new Intent(MainActivity.this,ChooseTakeOrCreateActivity.class);
                                startActivity(intent2);


                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this,"Sorry,error has occured",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
