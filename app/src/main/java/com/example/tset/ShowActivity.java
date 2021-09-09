package com.example.tset;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tset.login.UserSQLhelpopen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShowActivity extends AppCompatActivity {
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
    };
    public MySQLiteQpenHelper.MyDbOpenHelper myDbHelper;
    public UserSQLhelpopen.Uesrdbopen usedbhelper;
    private String pname;
    private Bitmap bitmap1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent intent =getIntent();
        String message = intent.getStringExtra(PAdapter.MESSAGE_STRING_title);
        TextView title = findViewById(R.id.title_up);
        TextView author = findViewById(R.id.upname);//作者姓名
        ImageView p_show = findViewById(R.id.up_show);//展示图片
        ImageView upp =findViewById(R.id.upp);//作者头像

        title.setText(message);

        myDbHelper = new MySQLiteQpenHelper.MyDbOpenHelper(ShowActivity.this);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                pContract.pEntry.TABLE_NAME,null,null,null,null,null,null
        );

        int titleIndex = cursor.getColumnIndex(pContract.pEntry.P_TITLE);
        int author2Index = cursor.getColumnIndex(pContract.pEntry.P_AUTHOR);
        int imageIndex = cursor.getColumnIndex(pContract.pEntry.P_IMAGE);
        while (cursor.moveToNext()){
            String mtitle = cursor.getString(titleIndex);
            if (message.equals(mtitle)){
                String mauthor =cursor.getString(author2Index);//账号
                usedbhelper = new UserSQLhelpopen.Uesrdbopen(ShowActivity.this);
                SQLiteDatabase db1 = usedbhelper.getReadableDatabase();
                Cursor cursor1 = db1.query(
                        pContract.pEntry.TABLE_ACCOUNT,null,null,null,null,null,null
                );
                int acount = cursor1.getColumnIndex(pContract.pEntry.USER);
                int authorIndex = cursor1.getColumnIndex(pContract.pEntry.P_AUTHOR);
                while (cursor1.moveToNext()){
                    String mcount = cursor1.getString(acount);
                    if (mauthor.equals(mcount)){
                        String idmessage = cursor1.getString(authorIndex);
                        author.setText("分享者："+ idmessage);
                    }
                }
                byte[] image = cursor.getBlob(imageIndex);
                bitmap1 = BitmapFactory.decodeByteArray(image,0,image.length);
                p_show.setImageBitmap(bitmap1);

                usedbhelper = new UserSQLhelpopen.Uesrdbopen(ShowActivity.this);
                SQLiteDatabase db2 = usedbhelper.getReadableDatabase();
                Cursor cursor2 = db2.query(
                        pContract.pEntry.TABLE_ACCOUNT,null,null,null,null,null,null
                );
                while (cursor2.moveToNext()){
                    int countIndex2 = cursor2.getColumnIndex(pContract.pEntry.USER);
                    String author2 = cursor2.getString(countIndex2);//作者
                    if (mauthor.equals(author2)){
                        int UPP = cursor2.getColumnIndex(pContract.pEntry.U_IMAGE);
                        byte[] up_image = cursor2.getBlob(UPP);
                        Bitmap bitmap2 = BitmapFactory.decodeByteArray(up_image,0,up_image.length);
                        upp.setImageBitmap(bitmap2);
                        pname = message+mauthor+"PNG";
                    }
                }

            }
        }
        String[] PERMISSIONS = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.MOUNT_UNMOUNT_FILESYSTEMS"};
//检测是否有写的权限
        int permission = ContextCompat.checkSelfPermission(this,
                "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 没有写的权限，去申请写的权限，会弹出对话框
            ActivityCompat.requestPermissions(this, PERMISSIONS,1);
        }
        int permission2 = ContextCompat.checkSelfPermission(this,
                "android.permission.MOUNT_UNMOUNT_FILESYSTEMS");
        if (permission2 != PackageManager.GET_SHARED_LIBRARY_FILES){
            ActivityCompat.requestPermissions(this,PERMISSIONS,2);
        }
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //SD卡已装入
            init();
        }



    }

    private void init() {
        ImageView down = findViewById(R.id.downward);
        ImageView good = findViewById(R.id.good_show);

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File file = new File("/sdcard/Pictures/"+pname+".jpg");
                if (file.exists()){
                    file.delete();
                }
                if (!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap1.compress(Bitmap.CompressFormat.PNG,100,out);
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                }
                break;
            default:
                break;
        }
    }
}