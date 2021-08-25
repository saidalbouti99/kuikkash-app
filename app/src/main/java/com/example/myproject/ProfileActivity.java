package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
Button btn_logout,btn_update,btn_changepic;
FirebaseAuth mFirebaseAuth;
private FirebaseAuth.AuthStateListener mAuthStateListener;
FirebaseUser user;
ImageView img_profilepic;
EditText et_username,et_userphone,et_matricno;
FirebaseDatabase mFirebaseDatabase;
DatabaseReference databaseReference;
TextView tv_useremail;
ProgressDialog progressDialog;
FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btn_logout=findViewById(R.id.btn_logout);
        et_username=findViewById(R.id.et_username_profact);
//        tv_useremail=findViewById(R.id.tv_useremail);
        et_userphone=findViewById(R.id.et_userphone_profact);
        img_profilepic=findViewById(R.id.img_profilepic_profact);
        btn_update=findViewById(R.id.btn_update);
        btn_changepic=findViewById(R.id.btn_changepic);
        et_matricno=findViewById(R.id.et_matricno_profact);

        //init progress dialog
        progressDialog=new ProgressDialog(ProfileActivity.this);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();

            }


        });

        btn_changepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,UploadImageActivity.class);
                startActivity(intent);
            }
        });


        //firebase
        mFirebaseAuth=FirebaseAuth.getInstance();
        user=mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=mFirebaseDatabase.getReference("Users");
        final FirebaseUser mFirebaseUser=mFirebaseAuth.getCurrentUser();

        DatabaseReference tempref = databaseReference.child(user.getUid());

        tempref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                String name=""+ ds.child("name").getValue();
//                String email=""+ ds.child("email").getValue();
//                String email=mFirebaseUser.getEmail();
                String phone=""+ ds.child("phone").getValue();
                String image=""+ ds.child("image").getValue();
                String matricno=""+ds.child("matricno").getValue();

                byte[] decodedStr = Base64.decode(image, Base64.DEFAULT);
                Bitmap decodedImg = BitmapFactory.decodeByteArray(decodedStr, 0 , decodedStr.length);

                //set data
                et_username.setText(name);
//               aa
                et_userphone.setText(phone);
                et_matricno.setText(matricno);
//                tv_useremail.setText(email);
                img_profilepic.setImageBitmap(decodedImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        public void addUsers(){
//            String data_useremail=tv_useremail.getText().toString();
//            String data_username=et_username.getText().toString();
//            String data_userphone=et_userphone.getText().toString();
//
//
//
//            if (!TextUtils.isEmpty(data_username)   && !TextUtils.isEmpty(data_userphone) && !TextUtils.isEmpty(data_useremail) ){
//                String id=databaseReference.push().getKey();
//
//                User userData=new User(data_useremail,data_username,data_userphone,);
//                databaseReference.child(id).setValue(jobData);
//                tv_jobcategory.setText("");
//          et_jobtitle.setText("");
//                et_jobdesc.setText("");
//                tv_date.setText("");
//                et_time.setText("");
//                et_price.setText("");
//
//
//            }
//            else{
//                Toast.makeText(ProfileActivity.this,"cannot be empty",Toast.LENGTH_LONG).show();
//
//            }
//        }



//        get info of currently signed user
/*        Query query=databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    //get data
                    String name=""+ ds.child("name").getValue();
                    String email=""+ ds.child("email").getValue();
                    String phone=""+ ds.child("phone").getValue();
                    String image=""+ ds.child("image").getValue();
                    String matricno=""+ds.child("matricno").getValue();

                    byte[] decodedStr = Base64.decode(image, Base64.DEFAULT);
                    Bitmap decodedImg = BitmapFactory.decodeByteArray(decodedStr, 0 , decodedStr.length);

                    //set data
                    et_username.setText(name);
                    tv_useremail.setText(email);
                    et_userphone.setText(phone);
                    et_matricno.setText(matricno);
                    img_profilepic.setImageBitmap(decodedImg);
//                    try {
//                        Picasso.get().load(image).into(img_profilepic);
//
//                    }catch (Exception e){
//                       Picasso.get().load(R.drawable.ic_account_circle_black_24dp).into(img_profilepic);
//
//                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


*/


    }


    private void showEditProfileDialog() {

            //Show dialog containing options to edit

            //options to show dialog
            String options[]={"Edit Name","Edit Phone Number","Edit Matric No."};

//alert dialog
            AlertDialog.Builder builder=new AlertDialog.Builder(ProfileActivity.this);

            //set title
        builder.setTitle("Choose Action");
        //set items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle iteam dialog click
                if (which==0){
                    //edit name clicked
                    progressDialog.setMessage("Updating Name");
                    //calling method and pass key "name"as parameter to update its value in database
                    showNamePhoneMatricNoUpdateDialog("name");

                }
                else if(which==1){
                    //edit phone number clicked
                    progressDialog.setMessage("Updating Phone Number");
                    showNamePhoneMatricNoUpdateDialog("phone");

                }
                else if (which==2){
                    //Edit matric no clicked
                    progressDialog.setMessage("Updating Matric No.");
                    showNamePhoneMatricNoUpdateDialog("matricno");

                }

            }
        });

        //create and show dialog
        builder.create().show();

    }

    private void showNamePhoneMatricNoUpdateDialog(final String key) {
        //parameter key will contain value: either "name" which is key in user's database which is used to update user's name

        //custom dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Update "+key);

        //set layput  of dialog
        LinearLayout linearLayout=new LinearLayout(ProfileActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);
        //add edittext
        final EditText editText=new EditText(ProfileActivity.this);
        editText.setHint("Enter "+key);
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        //add buttons in dialog to update
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input text from edittext
                final String value=editText.getText().toString().trim();
                //validate if user has entered sth or not
                if (!TextUtils.isEmpty(value)){
                    progressDialog.show();
                    HashMap<String,Object> result=new HashMap<>();
                    result.put(key,value);


                    databaseReference.child(user.getUid()).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //updated or dismissed progress
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this,"Updated...",Toast.LENGTH_SHORT).show();

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ProfileActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();


                                }
                            });

                }
                else{
                    Toast.makeText(ProfileActivity.this,"Please enter "+key,Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        //create and show dialog
        builder.create().show();

        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            String profilepic;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    profilepic=dataSnapshot.child("image").getValue().toString();
                    byte[] decodedStr = Base64.decode(profilepic, Base64.DEFAULT);
                    Bitmap decodedImg = BitmapFactory.decodeByteArray(decodedStr, 0 , decodedStr.length);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
