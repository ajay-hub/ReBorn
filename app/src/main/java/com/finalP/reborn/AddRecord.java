package com.finalP.reborn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AddRecord extends AppCompatActivity {
    private CircularImageView homei;
    private EditText name,mobile,address,sqft,balcony,bhk,price,date,des;
    private FloatingActionButton save;
    private ActionBar actionBar;
    private static final int CAMERA_REQUEST_CODE=100;
    private static final int STORAGE_REQUEST_CODE=101;
    private static final int IMAGE_PICK_CAMERA_CODE=102;
    private static final int IMAGE_PICK_GALLARY_CODE=103;
    private String[] cameraPermissions;
    private String[] storagePermissions;
    private Uri imageUri;
    private String Name,Mobile,Address,Sqft,Balcony,Bhk,Price,Date,Des,Id,AddedTime,UpdatedTime;
    private boolean isEditMode=false;

    private MyDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);


        actionBar=getSupportActionBar();
        actionBar.setTitle("Add Record");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        homei=findViewById(R.id.homei);
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobile);
        address=findViewById(R.id.address);
        sqft=findViewById(R.id.sqft);
        balcony=findViewById(R.id.balcony);
        bhk=findViewById(R.id.bhk);
        price=findViewById(R.id.price);
        date=findViewById(R.id.date);
        des=findViewById(R.id.des);
        save=findViewById(R.id.save);

        Intent intent=getIntent();
        isEditMode=intent.getBooleanExtra("isEditMode",false);
        Id=intent.getStringExtra("ID");



        if(isEditMode){
            getSupportActionBar().setTitle("Update Data");


            Name=intent.getStringExtra("NAME");
            Mobile=intent.getStringExtra("MOBILE");
            Address=intent.getStringExtra("ADDRESS");
            Sqft=intent.getStringExtra("SQFT");
            Balcony=intent.getStringExtra("BALCONY");
            Bhk=intent.getStringExtra("BHK");
            Price=intent.getStringExtra("PRICE");
            Date=intent.getStringExtra("DATE");
            Des=intent.getStringExtra("DES");
            imageUri= Uri.parse(intent.getStringExtra("IMAGE"));
            UpdatedTime=intent.getStringExtra("UPDATEDTIME");
            AddedTime=intent.getStringExtra("ADDEDTIME");

            name.setText(Name);
            mobile.setText(Mobile);
            address.setText(Address);
            sqft.setText(Sqft);
            balcony.setText(Balcony);
            bhk.setText(Bhk);
            price.setText(Price);
            date.setText(Date);
            des.setText(Des);

            if(imageUri.toString().equals("null")){
                homei.setImageResource(R.drawable.ic_person);

            }
            else {
                homei.setImageURI(imageUri);

            }



        }
        else {

        }







        dbHelper=new MyDbHelper(this);

        cameraPermissions=new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        homei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();



            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
                startActivity(new Intent(AddRecord.this,welcomepage.class));

            }
        });



    }

    private void inputData() {
        Name=""+name.getText().toString().trim();
        Mobile=""+mobile.getText().toString().trim();
        Address=""+address.getText().toString().trim();
        Sqft=""+sqft.getText().toString().trim();
        Balcony=""+balcony.getText().toString().trim();
        Bhk=""+bhk.getText().toString().trim();
        Price=""+price.getText().toString().trim();
        Date=""+date.getText().toString().trim();
        Des=""+des.getText().toString().trim();

        if(isEditMode){
            String timestamp=""+System.currentTimeMillis();
            dbHelper.updateRecord(""+Id,""+imageUri,""+Name,""+Mobile,""+Address,""+Sqft,""+Balcony,""+Bhk,""+Price,""+Date,""+Des,""+AddedTime,""+timestamp);
            Toast.makeText(this,"Updated:",Toast.LENGTH_SHORT).show();
        }else {
            String timestamp=""+System.currentTimeMillis();
            long id =dbHelper.insertRecord(""+imageUri,""+Name,""+Mobile,""+Address,""+Sqft,""+Balcony,""+Bhk,""+Price,""+Date,""+Des,""+timestamp,""+timestamp);
            Toast.makeText(this,"Record added agains id:"+id,Toast.LENGTH_SHORT).show();

        }






    }

    private void imagePickDialog(){
        String[] options={"Camera","Gallery"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    if(!checkCameraPermission()){
                        requestCameraPerssion();
                    }
                    else {
                        pickFromCamera();

                    }
                }
                else if (which==1){
                    if (!checkStoragepermission()){
                        requestStoragePermission();
                    }
                    else {
                        pickFromGallary();

                    }
                }

            }
        });
        builder.create().show();
    }

    private void pickFromGallary() {
        Intent galleryIntent=new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLARY_CODE);

    }

    private void pickFromCamera() {
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Image_Title");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image_description");
        imageUri =getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragepermission(){
        boolean result= ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result= ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1= ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result1;

    }

    private void requestCameraPerssion(){
        ActivityCompat.requestPermissions(this,cameraPermissions,CAMERA_REQUEST_CODE);

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this,"Camera and storage required ",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean storageAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        pickFromGallary();
                    }
                    else{
                        Toast.makeText(this,"storage required ",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            if (requestCode==IMAGE_PICK_GALLARY_CODE){
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode==IMAGE_PICK_CAMERA_CODE){
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result=CropImage.getActivityResult(data);
                if(resultCode==RESULT_OK){
                    Uri resultUri=result.getUri();
                    imageUri=resultUri;
                    homei.setImageURI(resultUri);
                }
                else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();
                    Toast.makeText(this,""+error,Toast.LENGTH_SHORT).show();

                }

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
