package com.finalP.reborn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper {
    public MyDbHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);


    }





    public long insertRecord(String image,String name,String mobile,String address,String age,String bloodGrp,String preExist,String aadhar,String date,
                             String des,String addTime,String updateTime){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(Constants.C_IMAGE,image);
        values.put(Constants.C_NAME,name);
        values.put(Constants.C_MOBILE,mobile);
        values.put(Constants.C_ADDRESS,address);
        values.put(Constants.C_AGE,age);
        values.put(Constants.C_BALCONY,bloodGrp);
        values.put(Constants.C_BHK,preExist);
        values.put(Constants.C_PRICE,aadhar);
        values.put(Constants.C_DATE,date);
        values.put(Constants.C_DES,des);
        values.put(Constants.C_ADDED_TIMESTAMP,addTime);
        values.put(Constants.C_UPDATED_TIMESTAMP,updateTime);
        long id=db.insert(Constants.TABLE_NAME,null,values);
        db.close();
        return id;



    }



    public void updateRecord(String id,String image,String name,String mobile,String address,String age,String bloodGrp,String preExist,String aadhar,String date,
                             String des,String addTime,String updateTime){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(Constants.C_IMAGE,image);
        values.put(Constants.C_NAME,name);
        values.put(Constants.C_MOBILE,mobile);
        values.put(Constants.C_ADDRESS,address);
        values.put(Constants.C_AGE,age);
        values.put(Constants.C_BALCONY,bloodGrp);
        values.put(Constants.C_BHK,preExist); //C_BHK
        values.put(Constants.C_PRICE,aadhar);
        values.put(Constants.C_DATE,date);
        values.put(Constants.C_DES,des);
        values.put(Constants.C_ADDED_TIMESTAMP,addTime);
        values.put(Constants.C_UPDATED_TIMESTAMP,updateTime);
        db.update(Constants.TABLE_NAME,values, Constants.C_ID +" = ? ", new String[]{id});
        db.close();

    }




    public ArrayList<ModelRecord> getAllRecord(String orderBy){
        ArrayList<ModelRecord> recordslist=new ArrayList<>();
        String selectQuery=" SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_MOBILE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDRESS)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_AGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_BALCONY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_BHK)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_PRICE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_DATE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_DES)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP)));
                recordslist.add(modelRecord);
            }while (cursor.moveToNext());

        }

        db.close();
        return recordslist;
    }




    public ArrayList<ModelRecord> searchRecord(String query){
        ArrayList<ModelRecord> recordslist=new ArrayList<>();
//        String selectQuery=" SELECT * FROM " + Constants.TABLE_NAME + " WHERE "+ Constants.C_NAME + " LIKE '%" + query + "%'";
        String selectQuery=" SELECT * FROM " + Constants.TABLE_NAME + " WHERE "+ Constants.C_NAME + " LIKE '%" + query + "%'" + " OR "+ Constants.C_PRICE + " LIKE '%" + query + "%'" ;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_MOBILE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDRESS)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_AGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_BALCONY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_BHK)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_PRICE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_DATE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_DES)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP)));
                recordslist.add(modelRecord);
            }while (cursor.moveToNext());

        }

        db.close();
        return recordslist;
    }


    public void deleteData(String id){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.C_ID + " = ? ", new String[]{id});
        db.close();

    }

    public void deleteAllData(){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM " + Constants.TABLE_NAME);
        db.close();


    }








    public  int getRecordsCount(){
        String countQuery=" SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(countQuery,null);
        int count=cursor.getCount();
        cursor.close();
        return count;


    }

}
