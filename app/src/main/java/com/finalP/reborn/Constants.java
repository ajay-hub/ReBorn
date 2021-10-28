package com.finalP.reborn;

public class Constants {
    public static final String DB_NAME="MY_RECORD_DB";
    public static final int DB_VERSION=1;
    public static final String TABLE_NAME="MY_RECORD_TABLE";
    public static final String C_ID="ID";
    public static final String C_IMAGE="IMAGE";
    public static final String C_NAME="NAME";
    public static final String C_MOBILE="MOBILE";
    public static final String C_ADDRESS="ADDRESS";
    public static final String C_AGE="SQFT";  //C_SQFT -> C_AGE
    public static final String C_BALCONY="BALCONY";
    public static final String C_BHK="BHK";
    public static final String C_PRICE="PRICE";
    public static final String C_DATE="DATE";
    public static final String C_DES="DES";
    public static final String C_ADDED_TIMESTAMP="ADDED_TIME_STAMP";
    public static final String C_UPDATED_TIMESTAMP="UPDATED_TIME_STAMP";
    public static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_IMAGE +" TEXT,"
            + C_NAME +" TEXT,"
            + C_MOBILE +" TEXT,"
            + C_ADDRESS +" TEXT,"
            + C_AGE +" TEXT,"
            + C_BALCONY +" TEXT,"
            + C_BHK +" TEXT,"
            + C_PRICE +" TEXT,"
            + C_DATE +" TEXT,"
            + C_DES +" TEXT,"
            + C_ADDED_TIMESTAMP +" TEXT,"
            + C_UPDATED_TIMESTAMP +" TEXT"
            +")";


}
