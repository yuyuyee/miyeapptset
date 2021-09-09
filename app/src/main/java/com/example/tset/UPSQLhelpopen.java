package com.example.tset;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class UPSQLhelpopen {

    private static final String SQL_CREATE_USER = "CREATE TABLE "+  pContract.pEntry.TABLE_P_U +
            "( "+ pContract.pEntry.USER + " VARCHAR(100), " +
            pContract.pEntry.P_TITLE + " VARCHAR(100),"+
            pContract.pEntry.P_AUTHOR + " VARCHAR(100) "+")";//用户保存

    private  static String SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS " + pContract.pEntry.TABLE_P_U;
    public  static  final  int DATABASE_VERSION = 1;
    public  static  final  String DATABASE_ACCOUT = "user_p.db";


    public static class UPdbopen extends SQLiteOpenHelper {
        public Context mContext;
        public UPdbopen (android.content.Context context){
            super(context, DATABASE_ACCOUT,null, DATABASE_VERSION);
            mContext = context;
        }

        public UPdbopen(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_CREATE_USER);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES2);
            onCreate(sqLiteDatabase);
        }

        public void  add(SQLiteDatabase sqLiteDatabase,String acount, String author, String title){
            ContentValues values = new ContentValues();
            //Bitmap imageb = ima.getDrawingCache(true);

            values.put(pContract.pEntry.USER,acount);
            values.put(pContract.pEntry.P_AUTHOR,author);
            values.put(pContract.pEntry.P_TITLE,title);
            long r = sqLiteDatabase.insert(
                    pContract.pEntry.TABLE_P_U,null,values
            );
        }

        public void delet(SQLiteDatabase sqLiteDatabase,String accout,String title){
             String SQL_DELETE_p = "DELETE FROM "+pContract.pEntry.TABLE_P_U+
                    " WHERE " +pContract.pEntry.USER + "= "+ accout+" AND "+
                    pContract.pEntry.P_TITLE +" = '"+ title +"' ;";
            sqLiteDatabase.execSQL(SQL_DELETE_p);

        }

    }
}
