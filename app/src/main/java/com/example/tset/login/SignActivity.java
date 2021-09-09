package com.example.tset.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tset.R;
import com.example.tset.pContract;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SignActivity extends AppCompatActivity {
    public UserSQLhelpopen.Uesrdbopen usedbhelper;
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    String index = null;
    public txMysql txmysql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        usedbhelper = new UserSQLhelpopen.Uesrdbopen(SignActivity.this);
        SQLiteDatabase db = usedbhelper.getReadableDatabase();
        Cursor cursor = db.query(
                pContract.pEntry.TABLE_ACCOUNT,null,null,null,null,null,null
        );
        init();

    }
private  String uri;
    Uri uri1;
    Context context;
    private void init() {
        ImageView touxiang = findViewById(R.id.sigentouxiang);
        Button adv_btn_sign = (Button) findViewById(R.id.bt_sign);
        Button adv_btn_back = (Button) findViewById(R.id.bt_back);

        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                index = Intent.ACTION_GET_CONTENT;
                startActivityForResult(intent, 1);



            }



            private void resizeImage(Uri uri1) {
                Intent intent = new Intent("com.example.tset");
                intent.setDataAndType(uri1, "image/*");
                intent.putExtra("crop", "true");//能够裁剪
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 150);
                intent.putExtra("outputY", 150);
                intent.putExtra("return-data", true);
            }
        });
        adv_btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usedbhelper = new UserSQLhelpopen.Uesrdbopen(SignActivity.this);
                SQLiteDatabase db = usedbhelper.getReadableDatabase();
                final EditText etname = findViewById(R.id.username);
                String et_nmae = etname.getText().toString();
                final EditText etacount = findViewById(R.id.et_s_acount);
                String et_acount = etacount.getText().toString();
                final EditText etpaw1 = findViewById(R.id.et_pwd);
                String pwa1 = etpaw1.getText().toString();
                final EditText etpaw2 = findViewById(R.id.et_pwd2);
                String pwa2 = etpaw2.getText().toString();
                final ImageView ima = (ImageView) findViewById(R.id.sigentouxiang);
                System.out.println(pwa2);

                if (pwa2.equals(pwa1)) {
                    usedbhelper.add(db, et_acount, et_nmae, pwa1, ima);
                    try {
                        txmysql.person_add(et_acount, et_nmae, pwa1, ima);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    finish();// 这个是关键
                } else {
                    new AlertDialog.Builder(SignActivity.this).setTitle("两次密码不一样").setMessage("重新输入密码").setPositiveButton("确定" , null).show();
                }
            }
        });
    }

    /**
     * 文件选取完成回调函数
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                ImageView adv_img_show = (ImageView) findViewById(R.id.sigentouxiang);
                /* 将Bitmap设定到ImageView */
                adv_img_show.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
                Toast.makeText(getApplicationContext(), "选择文件没有发现", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}