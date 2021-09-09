package com.example.tset.add;

import java.io.FileNotFoundException;
import java.sql.SQLException;


import android.app.Activity;
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

import com.example.tset.MainActivity;
import com.example.tset.MySQLiteQpenHelper;
import com.example.tset.R;
import com.example.tset.login.txMysql;
import com.example.tset.pContract;

public class addp extends Activity {
    public MySQLiteQpenHelper.MyDbOpenHelper myDbHelper;
    String index = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);
        myDbHelper = new MySQLiteQpenHelper.MyDbOpenHelper(addp.this);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                pContract.pEntry.TABLE_NAME,null,null,null,null,null,null
        );
        init();



    }

    /**
     * 初始化方法 http://www.my400800.cn
     */
    private void init() {
        // 事件注册start
        // 选择
        Button adv_btn_xuanze = (Button) findViewById(R.id.adv_btn_xuanze);
        // 提交
        Button adv_btn_tijiao = (Button) findViewById(R.id.adv_btn_shangchuan);
        // 返回
        Button adv_btn_fanhui = (Button) findViewById(R.id.adv_btn_fanhui);

        // 返回按钮按下处理事件
        adv_btn_fanhui.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();// 这个是关键

            }
        });

        // 事件注册end
        adv_btn_xuanze.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                index = Intent.ACTION_GET_CONTENT;
                startActivityForResult(intent, 1);

            }
        });

        // /* 开启Pictures画面Type设定为image */
        // intent.setType("image/*");
        // /* 使用Intent.ACTION_GET_CONTENT这个Action */
        // intent.setAction(Intent.ACTION_GET_CONTENT);
        // /* 取得相片后返回本画面 */
        // startActivityForResult(intent, 1);

        adv_btn_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context mContext = null;
                Intent intent =getIntent();
                String message = intent.getStringExtra(MainActivity.MESSAGE_STRING);
                myDbHelper = new MySQLiteQpenHelper.MyDbOpenHelper(addp.this);
                SQLiteDatabase db = myDbHelper.getReadableDatabase();
                final EditText etMessage = findViewById(R.id.editTextDate2);
                final ImageView ima = (ImageView) findViewById(R.id.adv_img_show);
                String title = etMessage.getText().toString();
                String author = message;//账号
                int goods = 0;
                MySQLiteQpenHelper.MyDbOpenHelper pinput = new MySQLiteQpenHelper.MyDbOpenHelper(addp.this);
                pinput.add(db,title,author,goods,ima);
                try {
                    txMysql.iamge_add(title,author,goods,ima);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        });
        adv_btn_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();// 这个是关键
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
                ImageView adv_img_show = (ImageView) findViewById(R.id.adv_img_show);
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
