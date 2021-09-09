package com.example.tset.login;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.tset.R;
import com.example.tset.pContract;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;




public class UserSQLhelpopen {
    private static final String SQL_CREATE_USER = "CREATE TABLE " + pContract.pEntry.TABLE_ACCOUNT +
            "(" + pContract.pEntry.USER + " VARCHAR(100) PRIMARY KEY," +
            pContract.pEntry.P_AUTHOR + " VARCHAR(100)," +
            pContract.pEntry.USER_PWR + " VARCHAR(100)," +
            pContract.pEntry.U_IMAGE + " BLOB "+")";//用户数据

    private  static String SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS " + pContract.pEntry.TABLE_ACCOUNT;
    public  static  final  int DATABASE_VERSION = 1;
    private  static String SQL_DELETE_ENTRIES = "DELETE FROM "+pContract.pEntry.TABLE_ACCOUNT;
    public  static  final  String DATABASE_ACCOUT = "user.db";

    public UserSQLhelpopen(Context context, String name, Object o, int databaseVersion) {
    }


    public static class Uesrdbopen extends SQLiteOpenHelper {
        public Context mContext;
        public Uesrdbopen (android.content.Context context){
            super(context, DATABASE_ACCOUT,null, DATABASE_VERSION);
            mContext = context;
        }

        public Uesrdbopen(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void  onCreate(SQLiteDatabase sqLiteDatabase){
            sqLiteDatabase.execSQL(SQL_CREATE_USER);
            initDb(sqLiteDatabase);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES2);
            onCreate(sqLiteDatabase);

        }

        public void onupdate(SQLiteDatabase sqLiteDatabase){
            sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
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
        public void  add(SQLiteDatabase sqLiteDatabase,String acount, String author, String paw, ImageView ima) {
            ContentValues values = new ContentValues();
            Bitmap image = ((BitmapDrawable) ima.getDrawable()).getBitmap();
            //Bitmap imageb = ima.getDrawingCache(true);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, os);
            values.put(pContract.pEntry.USER, acount);
            values.put(pContract.pEntry.P_AUTHOR, author);
            values.put(pContract.pEntry.USER_PWR, paw);
            values.put(pContract.pEntry.U_IMAGE, os.toByteArray());
            long r = sqLiteDatabase.insert(
                    pContract.pEntry.TABLE_ACCOUNT, null, values
            );
            //sqlite数据库保存


        }


    }

}
