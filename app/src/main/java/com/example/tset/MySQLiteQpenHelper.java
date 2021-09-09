package com.example.tset;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class MySQLiteQpenHelper {
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + pContract.pEntry.TABLE_NAME +
            "(" + pContract.pEntry._ID + "INTEGET PRIMARY KEY," +
            pContract.pEntry.P_TITLE + " VARCHAR(200), " +
            pContract.pEntry.P_AUTHOR + " VARCHAR(100), " +
            pContract.pEntry.P_good + " INTEGER, "+
            pContract.pEntry.P_IMAGE + " BLOB "+")";


    private  static  final String SQL_P_U_CREATE = "CREATE TABLE " +pContract.pEntry.TABLE_P_U +
            "(" + pContract.pEntry.USER + "VARCHAR(100), " +
            pContract.pEntry._ID + "INTEGET" + ")";//用户保存图片数据库
    private  static String SQL_DELETE_ENTRIES3 = "DELETE FROM "+pContract.pEntry.TABLE_NAME;
    private  static String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + pContract.pEntry.TABLE_NAME;
    private  static String SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS " + pContract.pEntry.TABLE_ACCOUNT;
    public  static  final  int DATABASE_VERSION = 1;
    public  static final  String DATABASE_NAME = "com.example.tset.Bmobsever.p.db";
    public  static  final  String DATABASE_ACCOUT = "user.db";
    public  static  final  int DATABASE_VERSION2 = 1;

    public static class MyDbOpenHelper extends SQLiteOpenHelper{
        public Context mContext;
        public MyDbOpenHelper (Context context){
            super(context, DATABASE_NAME,null, DATABASE_VERSION);
            mContext = context;
        }

        public MyDbOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void  onCreate(SQLiteDatabase sqLiteDatabase){
            sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
            initDb(sqLiteDatabase);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
            onCreate(sqLiteDatabase);

        }

        private void initDb(SQLiteDatabase sqLiteDatabase) {//插入数据
            Resources resources = mContext.getResources();
            String[] username = resources.getStringArray(R.array.user_count);
            String[] author = resources.getStringArray(R.array.authors);
            String[] userpaw = resources.getStringArray(R.array.userpaw);
            String[] userimage = resources.getStringArray(R.array.userimage);
            int length = 0;
            length = Math.min(userimage.length,username.length);
            length = Math.min(userimage.length,userpaw.length);
            for (int i = 0 ; i< length ; i++)
            {
                    ContentValues values = new ContentValues();
                    values.put(pContract.pEntry.USER,username[i]);
                    values.put(pContract.pEntry.USER_PWR,userpaw[i]);
                    values.put(pContract.pEntry.U_IMAGE,userimage[i]);
                    values.put(pContract.pEntry.P_AUTHOR,author[i]);

                    long r = sqLiteDatabase.insert(pContract.pEntry.TABLE_ACCOUNT,null,values);

            }


        }

        public void onupdate(SQLiteDatabase sqLiteDatabase){
            //sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
            sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES3);
        }

        public void  add(SQLiteDatabase sqLiteDatabase,String title, String author, int goods, ImageView ima){
            ContentValues values = new ContentValues();
            Bitmap image = ((BitmapDrawable)ima.getDrawable()).getBitmap();
            //Bitmap imageb = ima.getDrawingCache(true);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, os);
            values.put(pContract.pEntry.P_TITLE,title);
            values.put(pContract.pEntry.P_AUTHOR,author);
            values.put(pContract.pEntry.P_good,goods);
            values.put(pContract.pEntry.P_IMAGE,os.toByteArray());
            long r = sqLiteDatabase.insert(
                    pContract.pEntry.TABLE_NAME,null,values
            );
        }

        public  void  add_good(SQLiteDatabase sqLiteDatabase,String title, int goods){
            ContentValues values = new ContentValues();
            String SQL_ADD_good = "UPDATE "+pContract.pEntry.TABLE_NAME +
                    " SET "+pContract.pEntry.P_good + " = '"+ goods + "' WHERE "+
                    pContract.pEntry.P_TITLE +" = '"+title+"' ;";
            sqLiteDatabase.execSQL(SQL_ADD_good);

        }




    }

}

