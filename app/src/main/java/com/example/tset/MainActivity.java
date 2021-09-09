package com.example.tset;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tset.add.addp;
import com.example.tset.login.LoginActivity;
import com.example.tset.login.UserSQLhelpopen;
import com.example.tset.login.mine;
import com.example.tset.login.mineAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ListView.OnItemClickListener{
    private BottomNavigationView navigation;
    private static final String NEWS_TITLE = "P_title";
    private static final String NEWS_AUTHOR = "P_author";
    private String[] titles = null;
    private String[] authors = null;
    public static final String NEWS_ID = "P_id";
    private List<com.example.tset.Ph> dataList = new ArrayList<>();
    public static final String MESSAGE_STRING = "com.example.tset.MESSAGE";
    public MySQLiteQpenHelper.MyDbOpenHelper myDbHelper;
    public UserSQLhelpopen.Uesrdbopen usedbhelper;
    public UPSQLhelpopen.UPdbopen usephelp;
    private ListView phlist;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);
        LayoutInflater.from(MainActivity.this).inflate(R.layout.button,navigation,true);
        show();
        init();

    }

    private void init() {
        // 事件注册start
        // 选择
        ImageView mine = (ImageView) findViewById(R.id.main);
        // 提交
        ImageView add = (ImageView) findViewById(R.id.share);
        ImageView mian = (ImageView) findViewById(R.id.Picture);
        ImageView l = (ImageView)findViewById(R.id.iv_image_l);

        Button adv_btn_tijiao = (Button) findViewById(R.id.adv_btn_shangchuan);
        // 返回
        Button adv_btn_fanhui = (Button) findViewById(R.id.adv_btn_fanhui);
                mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mian.setImageResource(R.drawable.ic_baseline_photo_24);
                mine.setImageResource(R.drawable.ic_baseline_account_box_24_back);
                 List<com.example.tset.login.mine> mines = new ArrayList<>();
                Intent intent =getIntent();
                String message = intent.getStringExtra(LoginActivity.MESSAGE_STRING);
                usedbhelper = new UserSQLhelpopen.Uesrdbopen(MainActivity.this);
                SQLiteDatabase db = usedbhelper.getReadableDatabase();
                Cursor cursor = db.query(
                        pContract.pEntry.TABLE_ACCOUNT,null,null,null,null,null,null
                );
                int acount = cursor.getColumnIndex(pContract.pEntry.USER);
                int authorIndex = cursor.getColumnIndex(pContract.pEntry.P_AUTHOR);
                int imageId = cursor.getColumnIndex(pContract.pEntry.U_IMAGE);
                while (cursor.moveToNext()){
                    mine news =new mine();
                    String acountid = cursor.getString(acount);
                    String author = cursor.getString(authorIndex);
                    byte[] image = cursor.getBlob(imageId);
                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(image,0,image.length);

                    if (acountid.equals(message))
                    {
                        news.setUername(author);
                        System.out.println(acountid);
                        System.out.println(author);
                        news.setmImageId(bitmap1);//出错
                        mines.add(news);
                    }





                }

                mineAdapter newsAdapter = new mineAdapter(MainActivity.this,
                        R.layout.main_mine, mines);

                ListView lvNewsList = findViewById(R.id.lv_show);
                lvNewsList.setAdapter(newsAdapter);

                init();
            }



                });

                mian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mian.setImageResource(R.drawable.ic_baseline_photo_24_back);
                        mine.setImageResource(R.drawable.ic_baseline_account_box_24);
                        show();
                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent =getIntent();
                        String message = intent.getStringExtra(LoginActivity.MESSAGE_STRING);
                        Intent intent2 = new Intent( MainActivity.this, addp.class);

                        intent2.putExtra(MESSAGE_STRING,message);//发送账号
                        startActivity(intent2);
                    }
                });


    }
    public void show(){
        myDbHelper = new MySQLiteQpenHelper.MyDbOpenHelper(MainActivity.this);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                pContract.pEntry.TABLE_NAME,null,null,null,null,null,null
        );

        List<Ph> psList = new ArrayList<>();
        int idIndex = cursor.getColumnIndex(pContract.pEntry._ID);
        int titleIndex = cursor.getColumnIndex(pContract.pEntry.P_TITLE);
        int authorIndex = cursor.getColumnIndex(pContract.pEntry.P_AUTHOR);
        int imageIndex = cursor.getColumnIndex(pContract.pEntry.P_IMAGE);
        int goodIndex = cursor.getColumnIndex(pContract.pEntry.P_good);

        Intent intent =getIntent();
        String message = intent.getStringExtra(LoginActivity.MESSAGE_STRING);
        while (cursor.moveToNext())
        {
            usephelp = new UPSQLhelpopen.UPdbopen(MainActivity.this);
            SQLiteDatabase db2 = usephelp.getReadableDatabase();
            Cursor cursor1 = db2.query(
                    pContract.pEntry.TABLE_P_U,null,null,null,null,null,null
            );
            Cursor cursor2 = db2.query(
                    pContract.pEntry.TABLE_P_U,null,null,null,null,null,null
            );
            int tileIndex1 = cursor1.getColumnIndex(pContract.pEntry.P_TITLE);
            int acountIndex = cursor1.getColumnIndex(pContract.pEntry.USER);
            String title = cursor.getString(titleIndex);
            String author = cursor.getString(authorIndex);
            int good = cursor.getInt(goodIndex);
            byte[] image = cursor.getBlob(imageIndex);
            int i = 0;
            int j = 0;
            Ph news =new Ph();
            while (cursor1.moveToNext()){
                String title2 = cursor1.getString(tileIndex1);
                String account = cursor1.getString(acountIndex);
                if (title2.equals(title))
                {

                    if (message.equals(account))
                    {

                        i=1;
                    }

                }
            }

            Bitmap bitmap1 = BitmapFactory.decodeByteArray(image,0,image.length);

            news.setmTitle(title);
            news.setmAuthor(author);
            news.setmImageId(bitmap1);//出错
            news.setI1(i);
            news.setGoodl(good);
            news.setAcount(message);
            if(cursor.moveToNext()) {

                title = cursor.getString(titleIndex);
                author = cursor.getString(authorIndex);
                good = cursor.getInt(goodIndex);
                while (cursor2.moveToNext()){
                    String title2 = cursor2.getString(tileIndex1);
                    String  account= cursor2.getString(acountIndex);
                    if (title2.equals(title))
                    {
                        if (message.equals(account))
                        {
                            j=1;
                        }

                    }
                }
                image = cursor.getBlob(imageIndex);
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(image, 0, image.length);
                news.setI2(j);

                news.setmTitle2(title);
                news.setmAuthor2(author);
                news.setmImageId2(bitmap2);
                news.setGoodr(good);

            }

            psList.add(news);
            i++;


        }
        //显示图片页面
        PAdapter newsAdapter = new PAdapter(MainActivity.this,
                R.layout.list_item,psList);

        ListView lvNewsList = findViewById(R.id.lv_show);
        lvNewsList.setAdapter(newsAdapter);
        db.close();
    }



    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.main){

        }

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



    }
}