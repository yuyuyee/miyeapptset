package com.example.tset.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tset.MainActivity;
import com.example.tset.MySQLiteQpenHelper;
import com.example.tset.R;
import com.example.tset.pContract;

import java.util.ArrayList;
import java.util.List;

public  class LoginActivity extends AppCompatActivity
        implements View.OnClickListener {
    private EditText etPwd;
    private EditText etAccount;
    private CheckBox cbRememberPwd;
    private Boolean bPwdSwitch = false;
    private MySQLiteQpenHelper myDbHelper;
    public UserSQLhelpopen.Uesrdbopen usedbhelper;
    public  static  final  String MESSAGE_STRING = "com.example.tset.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        final ImageView ivPwdSwitch = findViewById(R.id.iv_pwd_switch);
        etPwd = findViewById(R.id.et_pwd);
        ivPwdSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bPwdSwitch = !bPwdSwitch;
                if(bPwdSwitch){
                    ivPwdSwitch.setImageResource(
                            R.drawable.ic_baseline_fiber_smart_record_24
                    );
                    etPwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    );
                }else{
                    ivPwdSwitch.setImageResource(
                            R.drawable.ic_baseline_fiber_manual_record_24
                    );
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|
                            InputType.TYPE_CLASS_TEXT);
                    etPwd.setTypeface(Typeface.DEFAULT);
                }
            }
        });
        etAccount = findViewById(R.id.et_account);
        cbRememberPwd = findViewById(R.id.cb_remember_pwd);
        Button btLogin = findViewById(R.id.bt_login);
        TextView sign = findViewById(R.id.tv_sign_up);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignActivity.class);
                startActivity(intent);
            }
        });

        btLogin.setOnClickListener(this);
        String spFileName = getResources().getString(R.string.shared_prefernces_file_name);
        String accountKey = getResources().getString(R.string.login_account_name);
        String passwordKey = getResources().getString(R.string.login_password);
        String rememberPasswordKey = getResources().getString(R.string.login_remember_password);
        SharedPreferences spFile = getSharedPreferences(spFileName, MODE_PRIVATE);
        String account = spFile.getString(accountKey, null);
        String password = spFile.getString(passwordKey, null);
        Boolean rememberPassword = spFile.getBoolean(
                rememberPasswordKey, false
        );
        if (account != null && !TextUtils.isEmpty(account)) {
            etAccount.setText(account);
        }
        if (password != null && !TextUtils.isEmpty(password)) {
            etPwd.setText(password);
            List<mine> mines = new ArrayList<>();
            ImageView Imageid = findViewById(R.id.touxiang);
            usedbhelper = new UserSQLhelpopen.Uesrdbopen(LoginActivity.this);
            SQLiteDatabase db = usedbhelper.getReadableDatabase();
            Cursor cursor = db.query(
                    pContract.pEntry.TABLE_ACCOUNT,null,null,null,null,null,null
            );
            int imageId = cursor.getColumnIndex(pContract.pEntry.U_IMAGE);
            while (cursor.moveToNext()){
                mine news =new mine();
                int acount = cursor.getColumnIndex(pContract.pEntry.USER);
                byte[] image = cursor.getBlob(imageId);
                String acountid = cursor.getString(acount);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(image,0,image.length);
                if (acountid.equals(etAccount.getText().toString()))
                {

                    Imageid.setImageBitmap(bitmap1);
                }


            }
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        txMysql.userdb(LoginActivity.this,etAccount.getText().toString());
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        txMysql.image_db(LoginActivity.this);
                    }
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cbRememberPwd.setChecked(rememberPassword);
    }




    @Override
    public void onClick(View view) {


        String spFileName = getResources().getString(R.string.shared_prefernces_file_name);
        String accountKey = getResources().getString(R.string.login_account_name);
        String passwordKey = getResources().getString(R.string.login_password);
        String rememPasswordKey = getResources().getString(R.string.login_remember_password);
        SharedPreferences spFile = getSharedPreferences(
                spFileName,
                Context.MODE_PRIVATE
        );
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        usedbhelper = new UserSQLhelpopen.Uesrdbopen(LoginActivity.this);
        SQLiteDatabase db = usedbhelper.getReadableDatabase();
        Cursor cursor = db.query(
                pContract.pEntry.TABLE_ACCOUNT,null,null,null,null,null,null
        );

        int acount = cursor.getColumnIndex(pContract.pEntry.USER);
        int pwat = cursor.getColumnIndex(pContract.pEntry.USER_PWR);
        SharedPreferences.Editor editor = spFile.edit();
        while (cursor.moveToNext()){
            String acountid = cursor.getString(acount);
            String pwa = cursor.getString(pwat);
            if (acountid.equals(etAccount.getText().toString())){
                if (pwa.equals(etPwd.getText().toString())){
                    if (cbRememberPwd.isChecked()) {
                        String password = etPwd.getText().toString();
                        String account = etAccount.getText().toString();

                        editor.putString(accountKey, account);
                        editor.putString(passwordKey, password);
                        editor.putBoolean(rememPasswordKey, true);
                        editor.apply();


                    } else {
                        editor.remove(accountKey);
                        editor.remove(passwordKey);
                        editor.remove(rememPasswordKey);
                        editor.apply();
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(MESSAGE_STRING,acountid);
                    startActivity(intent);


                }
                else
                {
                    editor.remove(accountKey);
                    editor.remove(passwordKey);
                    editor.remove(rememPasswordKey);
                    editor.apply();
                    new AlertDialog.Builder(LoginActivity.this).setTitle("密码错误").setMessage("重新输入密码").setPositiveButton("确定" , null).show();
                }

            }
        }



    }


}