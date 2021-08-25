package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadImageActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    DatabaseReference databaseReference;

    String SP_MATRICIMG = "imagecode";
    String SP_GENDER = "studentgender";
    String SP_GENVALUE = "genvalue";
    String [] genvalue = {"Male" , "Female"};
    FirebaseUser user;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase mFirebaseDatabase;

    ImageView IDProf;
    TextView tv_gender;
    Button Upload_Btn, Select_Img;
    LinearLayout llayout;

    private String name = "";
    private String email="";
    private String phone="";
    private String matricno="";
    private String Document_img1="";

    AlertDialog alertdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        IDProf = findViewById(R.id.img_pic);
        Select_Img = findViewById(R.id.btn_choose);
        Upload_Btn = findViewById(R.id.btn_upload);
        tv_gender = findViewById(R.id.tv_student_gender);
        llayout = findViewById(R.id.llayout_gender);

        mFirebaseAuth= FirebaseAuth.getInstance();
        user=mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=mFirebaseDatabase.getReference("Users");

        sharedPref = getSharedPreferences("student_details", MODE_PRIVATE);
        editor = sharedPref.edit();

        Document_img1= sharedPref.getString(SP_MATRICIMG, "");

        DatabaseReference tempref = databaseReference.child(user.getUid());

        tempref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                name=""+ ds.child("name").getValue();
                email=""+ ds.child("email").getValue();
                phone=""+ ds.child("phone").getValue();
                matricno=""+ds.child("matricno").getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        byte[] decodedStr = Base64.decode(Document_img1, Base64.DEFAULT);
        Bitmap decodedImg = BitmapFactory.decodeByteArray(decodedStr, 0 , decodedStr.length);

        if(decodedImg != null) {
            IDProf.setImageBitmap(decodedImg);
        }

        tv_gender.setText(sharedPref.getString(SP_GENDER, "Male"));

        Select_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });

        llayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                AlertDialog.Builder b = new AlertDialog.Builder(UploadImageActivity.this);
                b.setTitle("Select Gender");

                b.setSingleChoiceItems(genvalue, sharedPref.getInt(SP_GENVALUE, 0), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case 0:
                                editor.putString(SP_GENDER, genvalue[0]);
                                editor.putInt(SP_GENVALUE, 0);
                                editor.commit();
                                break;

                            case 1:
                                editor.putString(SP_GENDER, genvalue[1]);
                                editor.putInt(SP_GENVALUE, 1);
                                editor.commit();
                                break;
                        }

                        alertdialog.dismiss();
                        tv_gender.setText(sharedPref.getString(SP_GENDER, "Male"));
                    }
                });

                alertdialog = b.create();
                alertdialog.show();
            }
        });

        Upload_Btn.setOnClickListener(this);

    }

    private void selectImage() {

        requestWritePermission();

        final String[] options = { "Take Photo from Camera", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadImageActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo from Camera"))
                {
                    startCamera();
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    startGallery();
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void requestWritePermission(){
        if (ContextCompat.checkSelfPermission(UploadImageActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(UploadImageActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                showExplanation(3);
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }
            else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(UploadImageActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    private void startGallery(){

        if (ContextCompat.checkSelfPermission(UploadImageActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(UploadImageActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){

                showExplanation(2);
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }
            else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(UploadImageActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            if(intent.resolveActivity(getPackageManager()) == null)
                Toast.makeText(UploadImageActivity.this, "Sorry, There is no application that can handle this service.", Toast.LENGTH_SHORT).show();
            else
                startActivityForResult(intent, 2);
        }

    }

    private void startCamera(){

        if (ContextCompat.checkSelfPermission(UploadImageActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(UploadImageActivity.this,
                    Manifest.permission.CAMERA)){
                showExplanation(1);
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }
            else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(UploadImageActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(intent.resolveActivity(getPackageManager()) == null)
                Toast.makeText(UploadImageActivity.this, "Sorry, There is no application that can handle this service.", Toast.LENGTH_SHORT).show();
            else
                startActivityForResult(intent, 1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");


                Uri tempUri = getImageUri(getApplicationContext(), photo);
                String filepath = getRealPathFromURI(tempUri);

                Bitmap thumbnail = (BitmapFactory.decodeFile(filepath));
                thumbnail=getResizedBitmap(thumbnail, 250);
                IDProf.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);

            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String filepath = getRealPathFromURI(selectedImage);

                Bitmap thumbnail = (BitmapFactory.decodeFile(filepath));
                thumbnail=getResizedBitmap(thumbnail, 250);
                IDProf.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
            }
        }
    }

    private void showExplanation(int requestcode){

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadImageActivity.this);

        switch(requestcode){
            case 1:
                builder.setTitle("Needs Camera Permission");
                builder.setMessage("This app needs to access your camera in order to take your photo.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(UploadImageActivity.this, new String[] {Manifest.permission.CAMERA}, 0);

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UploadImageActivity.this, "Sorry, this function cannot work until permission is granted.", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();

                break;

            case 2 :
                builder.setTitle("Needs Read Permission");
                builder.setMessage("This app needs to access your gallery in order to select picture.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(UploadImageActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                },
                                0 );
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UploadImageActivity.this, "Sorry, this function cannot work until permission is granted.", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
                break;

            case 3:
                builder.setTitle("Needs Write Permission");
                builder.setMessage("This app needs this permission to store data.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(UploadImageActivity.this, new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                0 );
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UploadImageActivity.this, "Sorry, this function cannot work until permission is granted.", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (Document_img1.equals("") || Document_img1.equals(null)) {

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UploadImageActivity.this);
            alertDialogBuilder.setTitle("Photo Can't Be Empty ");
            alertDialogBuilder.setMessage("Matric Card Photo Can't Be Empty Please Select A Photo");
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            alertDialogBuilder.show();
            return;
        }
        else {

            editor.putString(SP_MATRICIMG, Document_img1);
            editor.commit();

            Intent intent = new Intent(UploadImageActivity.this, ProfileActivity.class);

           // intent.putExtra(SP_MATRICIMG, Document_img1);
           // intent.putExtra(SP_GENDER, sharedPref.getString(SP_GENDER, "Male"));
            addPhoto();
            startActivity(intent);

        }
    }




    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "matricphoto", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public void addPhoto(){
//        String data_picture=Document_img1;
//        String data_gender=tv_gender.getText().toString();
//        String data_jobdesc=et_jobdesc.getText().toString();
//        String data_jobdate=tv_date.getText().toString();
//        String data_jobtime=et_time.getText().toString();
//        String data_jobprice=et_price.getText().toString();
//        String data_jobcat=tv_jobcategory.getText().toString();
          String id= user.getUid();

        HashMap<Object,String> hashMap=new HashMap<>();// to store user info in realtime database


        hashMap.put("email",email);//put info in hashmap
        hashMap.put("uid",id);
        hashMap.put("name",name);
        hashMap.put("phone",phone);
        hashMap.put("image",Document_img1);
        hashMap.put("matricno",matricno);
        hashMap.put("gender", sharedPref.getString(SP_GENDER, "Male"));

        databaseReference.child(id).setValue(hashMap);


//            tv_jobcategory.setText("");
//            et_jobtitle.setText("");
//            et_jobdesc.setText("");
//            tv_date.setText("");
//            et_time.setText("");
//            et_price.setText("");
//        tv_gender.setText("");
  //      byte[] decodedStr = Base64.decode(Document_img1, Base64.DEFAULT);
   //     Bitmap decodedImg = BitmapFactory.decodeByteArray(decodedStr, 0 , decodedStr.length);

//        Bitmap data_profilepic=decodedImg;
//        if(decodedImg != null) {
//            data_profilepic.setImageBitmap(decodedImg);
//        }


     /*   databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            String profilepic;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    profilepic=dataSnapshot.child("image").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Query query=databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    //get data
                    String name=""+ ds.child("name").getValue();
                    String email=""+ ds.child("email").getValue();
                    String phone=""+ ds.child("phone").getValue();
                    String image=""+ ds.child("image").getValue(); */
//                    String data_picture=Document_img1;
//                    String matricno=""+ds.child("matricno").getValue();


                    //set data

//                    try {
//                        Picasso.get().load(image).into(img_profilepic);
//
//                    }catch (Exception e){
//                       Picasso.get().load(R.drawable.ic_account_circle_black_24dp).into(img_profilepic);
//
//                    }
//                }

  //          }

  //          @Override
    //        public void onCancelled(@NonNull DatabaseError databaseError) {
//
      //      }});
       /* mAuthStateListener=new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser=mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){

                    //Intent intent2=new Intent(UploadImageActivity.this,ChooseTakeOrCreateActivity.class);
                    String email=mFirebaseUser.getEmail();
                    String uid=mFirebaseUser.getUid();


                    HashMap<Object,String> hashMap=new HashMap<>();// to store user info in realtime database




                    hashMap.put("image",Document_img1);


                    FirebaseDatabase database=FirebaseDatabase.getInstance();//firebase db instance

                    DatabaseReference databaseReference=database.getReference("Users");//path to store user data named "Users"
                    //put data within hashmap indb
                    databaseReference.child(uid).setValue(hashMap);



                   // startActivity(intent2);
                }
                else{
                    Toast.makeText(UploadImageActivity.this,"Please upload a picture",Toast.LENGTH_SHORT).show();
                }
            }
        };
*/
    }

}
