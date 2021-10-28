package com.finalP.reborn;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;

import java.util.Calendar;
import java.util.Locale;

public class RecordDetailActivity extends AppCompatActivity {

    private CircularImageView homei;
    private TextView name,mobile,address,sqft,balcony,bhk,price,date,des,addedTime,updatedTime;
    private ActionBar actionBar;
    private String recordID;
    MyDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        actionBar=getSupportActionBar();
        actionBar.setTitle("Record Details");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        recordID=intent.getStringExtra("RECORD_ID");

        dbHelper=new MyDbHelper(this);





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
        addedTime=findViewById(R.id.addedTime);
        updatedTime=findViewById(R.id.updatedTime);

        showRecordDetails();

    }

    private void showRecordDetails() {
        String selectquery=" SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID + "=\"" + recordID + "\"";
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectquery,null);
        if (cursor.moveToFirst()){
            do{
                String Id=""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID));
                String image=""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE));
                String Name=""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME));
                String Mobile=""+cursor.getString(cursor.getColumnIndex(Constants.C_MOBILE));
                String Address=""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDRESS));
                String Sqft=""+cursor.getString(cursor.getColumnIndex(Constants.C_AGE));
                String Balcony=""+cursor.getString(cursor.getColumnIndex(Constants.C_BALCONY));
                String Bhk=""+cursor.getString(cursor.getColumnIndex(Constants.C_BHK));
                String Price=""+cursor.getString(cursor.getColumnIndex(Constants.C_PRICE));
                String Date=""+cursor.getString(cursor.getColumnIndex(Constants.C_DATE));
                String Des=""+cursor.getString(cursor.getColumnIndex(Constants.C_DES));
                String AddedTime=""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP));
                String UpdatedTime=""+cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP));

                Calendar calendar1=Calendar.getInstance(Locale.getDefault());
                calendar1.setTimeInMillis(Long.parseLong(AddedTime));
                String timeAdded= ""+DateFormat.format("dd/MM/yyyy  hh:mm:aa",calendar1);

                Calendar calendar2=Calendar.getInstance(Locale.getDefault());
                calendar2.setTimeInMillis(Long.parseLong(UpdatedTime));
                String timeUpdated= ""+DateFormat.format("dd/MM/yyyy  hh:mm:aa",calendar2);

                name.setText(Name);
                mobile.setText(Mobile);
                address.setText(Address);
                sqft.setText(Sqft);
                balcony.setText(Balcony);
                bhk.setText(Bhk);
                price.setText(Price);
                date.setText(Date);
                des.setText(Des);
                addedTime.setText(timeAdded);
                updatedTime.setText(timeUpdated);
                homei.setImageURI(Uri.parse(image));


                if(image.equals("null")){
                    homei.setImageResource(R.drawable.ic_person);

                }
                else {
                    homei.setImageURI(Uri.parse(image));

                }





            }while (cursor.moveToNext());
        }
        db.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
