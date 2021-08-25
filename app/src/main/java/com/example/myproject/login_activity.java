package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_activity extends AppCompatActivity {
    EditText et_email_signinact,et_password_signinact;
    Button btn_signin;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        btn_signin=findViewById(R.id.btn_signin_signinact);
        et_email_signinact=findViewById(R.id.et_email_signinact);
        et_password_signinact=findViewById(R.id.et_password_signinact);
        mFirebaseAuth=FirebaseAuth.getInstance();

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=et_email_signinact.getText().toString();
                String password=et_password_signinact.getText().toString();
                if(email.isEmpty()){
                    et_email_signinact.setError("Please fill in your email.");
                    et_email_signinact.requestFocus();
                }
                else if(password.isEmpty()){
                    et_password_signinact.setError("You must fill in your password");
                    et_password_signinact.requestFocus();
                }
                else if(email.isEmpty() && password.isEmpty()){
                    Toast.makeText(login_activity.this,"No email and password entered",Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && password.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(login_activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(login_activity.this,"Login Error, Please Try Again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intent3=new Intent(login_activity.this,ChooseTakeOrCreateActivity.class);
                                startActivity(intent3);
                            }
                        }
                    });


                }
                else{
                    Toast.makeText(login_activity.this,"Sorry,error has occured",Toast.LENGTH_SHORT).show();
                }

            }


        });

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser=mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(login_activity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent intent2=new Intent(login_activity.this,ChooseTakeOrCreateActivity.class);
                    String email=mFirebaseUser.getEmail();
                    String uid=mFirebaseUser.getUid();





                    startActivity(intent2);
                }
                else{
                    Toast.makeText(login_activity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
/*
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
                    databaseReference.child(uid).setValue(hashMap);*/
