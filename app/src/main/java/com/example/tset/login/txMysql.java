package com.example.tset.login;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.util.Log;
import android.widget.Toast;


import com.example.tset.MainActivity;
import com.example.tset.MySQLiteQpenHelper;
import com.example.tset.pContract;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class txMysql {


    public static void person_add(String acount, String author, String paw, ImageView ima) throws SQLException {
        Bitmap image = ((BitmapDrawable) ima.getDrawable()).getBitmap();
        //Bitmap imageb = ima.getDrawingCache(true);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        image.compress(Bitmap.CompressFormat.PNG, 100, os);

        byte[] image2 = os.toByteArray();
        InputStream in = new ByteArrayInputStream(image2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://gz-cynosdbmysql-grp-479xrrdr.sql.tencentcdb.com:22811/test", "root", "LHWlhw123");
                    System.out.println("连接成功");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                try {
                    String sql = "INSERT INTO person(image,num,name,pwa) VALUES (?,?,?,?)";
                    //String sql = "INSERT INTO person VALUES('" + os.toByteArray() + "','" + acount + "','" + author + "','" + paw + "')";
                    PreparedStatement st = conn.prepareStatement(sql);
                    //st.setBlob(1,in);
                    st.setBlob(1,in);
                    st.setString(2,acount);
                    st.setString(3,author);
                    st.setString(4,paw);

                    st.executeUpdate();
                    st.close();
                    conn.close();


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        }).start();

    }
    public static void iamge_add(String title, String num, int goods, ImageView ima) throws SQLException {
        Bitmap image = ((BitmapDrawable) ima.getDrawable()).getBitmap();
        //Bitmap imageb = ima.getDrawingCache(true);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        image.compress(Bitmap.CompressFormat.PNG, 100, os);

        byte[] image2 = os.toByteArray();
        InputStream in = new ByteArrayInputStream(image2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://gz-cynosdbmysql-grp-479xrrdr.sql.tencentcdb.com:22811/test", "root", "LHWlhw123");
                    System.out.println("连接成功");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                try {
                    String sql = "INSERT INTO image(num,tilte,goods,p_image) VALUES (?,?,?,?)";
                    //String sql = "INSERT INTO person VALUES('" + os.toByteArray() + "','" + acount + "','" + author + "','" + paw + "')";
                    PreparedStatement st = conn.prepareStatement(sql);
                    //st.setBlob(1,in);
                    st.setBlob(4,in);
                    st.setString(2,title);
                    st.setInt(3,goods);
                    st.setString(1,num);

                    st.executeUpdate();
                    st.close();
                    conn.close();


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        }).start();

    }
    public static void userdb (Context context,String etnum) {
        UserSQLhelpopen.Uesrdbopen usedbhelper3 = new UserSQLhelpopen.Uesrdbopen(context);
        SQLiteDatabase db2 = usedbhelper3.getReadableDatabase();
        usedbhelper3.onupdate(db2);
        db2.close();
        int pwa1 = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://gz-cynosdbmysql-grp-479xrrdr.sql.tencentcdb.com:22811/test", "root", "LHWlhw123");
            System.out.println("连接成功");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {

            String sql = "select * from person ";
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(sql);
                while (res.next()) {
                    ContentValues values = new ContentValues();
                    Blob iamge = res.getBlob(1);
                    InputStream in = iamge.getBinaryStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    BufferedInputStream inBuffered = new BufferedInputStream(in);
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                    String num = res.getString(2);
                    String name = res.getString(3);
                    String pwa = res.getString(4);
                    UserSQLhelpopen.Uesrdbopen usedbhelper = new UserSQLhelpopen.Uesrdbopen(context);
                    SQLiteDatabase db = usedbhelper.getReadableDatabase();

                    values.put(pContract.pEntry.USER, num);
                    values.put(pContract.pEntry.P_AUTHOR, name);
                    values.put(pContract.pEntry.USER_PWR, pwa);
                    values.put(pContract.pEntry.U_IMAGE, os.toByteArray());
                    long r = db.insert(
                            pContract.pEntry.TABLE_ACCOUNT, null, values
                    );
                    db.close();
                    pwa1++;


                }

            st.close();

            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    //加载图片数据
    public static void image_db (Context context) {
        UserSQLhelpopen.Uesrdbopen usedbhelper3 = new UserSQLhelpopen.Uesrdbopen(context);
        SQLiteDatabase db2 = usedbhelper3.getReadableDatabase();
        usedbhelper3.onupdate(db2);
        int pwa1 = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://gz-cynosdbmysql-grp-479xrrdr.sql.tencentcdb.com:22811/test", "root", "LHWlhw123");
            System.out.println("连接成功");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {

            MySQLiteQpenHelper.MyDbOpenHelper myDbOpenHelper = new MySQLiteQpenHelper.MyDbOpenHelper(context);
            SQLiteDatabase db1 = myDbOpenHelper.getReadableDatabase();
            myDbOpenHelper.onupdate(db1);
            String sql = "select * from image";
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()){
                ContentValues values = new ContentValues();
                String num = res.getString(1);
                String title = res.getString(2);
                int goods = res.getInt(3);
                Blob iamge = res.getBlob(4);
                InputStream in = iamge.getBinaryStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                BufferedInputStream inBuffered = new BufferedInputStream(in);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                MySQLiteQpenHelper.MyDbOpenHelper myDbOpenHelper1 = new MySQLiteQpenHelper.MyDbOpenHelper(context);
                SQLiteDatabase db11 = myDbOpenHelper1.getReadableDatabase();
                values.put(pContract.pEntry.P_TITLE,title);
                values.put(pContract.pEntry.P_AUTHOR,num);
                values.put(pContract.pEntry.P_good,goods);
                values.put(pContract.pEntry.P_IMAGE,os.toByteArray());
                long r = db11.insert(
                        pContract.pEntry.TABLE_NAME,null,values
                );

            }
               st.close();
                res.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //点赞记数
    public static void image_good(int goods,String title) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://gz-cynosdbmysql-grp-479xrrdr.sql.tencentcdb.com:22811/test", "root", "LHWlhw123");
            System.out.println("连接成功");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String sql = "UPDATE image SET goods = "+goods+" WHERE tilte = '"+title+"'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.executeUpdate();
        st.close();
        conn.close();
    }
    public static Connection getConnection(String dbName) {
        Connection conn = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver"); //加载驱动
            String ip = "gz-cynosdbmysql-grp-479xrrdr.sql.tencentcdb.com";
                     conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":22811/" + dbName,
                             "root", "LHWlhw123");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return conn;
    }
}
