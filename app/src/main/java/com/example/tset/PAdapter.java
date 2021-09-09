package com.example.tset;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.tset.login.LoginActivity;
import com.example.tset.login.txMysql;

import java.sql.SQLException;
import java.util.List;

public class PAdapter extends ArrayAdapter<com.example.tset.Ph> {
    private List<com.example.tset.Ph> mPdata;
    private Context mContext;
    private int resourceId;
    private Boolean goodSwitch = false;
    private UPSQLhelpopen.UPdbopen usephelp;
    private MySQLiteQpenHelper.MyDbOpenHelper mysqlhelp;
    static  final  String MESSAGE_STRING_title = "com.example.tset.MESSAGE_title";
    static  final  String MESSAGE_STRING_up = "com.example.tset.MESSAGE_up";
    public  PAdapter(Context context, int resourceId, List<com.example.tset.Ph> data) {
        super(context, resourceId, data);
        this.mContext = context;
        this.mPdata = data;
        this.resourceId = resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        com.example.tset.Ph p = getItem(position);
        View view ;
        view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView tvTitle2 = view.findViewById(R.id.tv_tile_r);
        TextView tvAuthor2 = view.findViewById(R.id.tv_subtitle_r);
        ImageView ivImage2 = view.findViewById(R.id.iv_image_r);
        TextView tvTitle  = view.findViewById(R.id.tv_tile_l);
        TextView tvAuthor = view.findViewById(R.id.tv_subtitle_l);
        ImageView ivImage = view.findViewById(R.id.iv_image_l);
        ImageView good_l = view.findViewById(R.id.good_l);
        ImageView good_r = view.findViewById(R.id.good_r);
        tvTitle.setText(p.getmTitle() +"      ||      收藏人数："+ p.getGoodl());
        tvAuthor.setText(p.getmAuthor());
        ivImage.setImageBitmap(p.getmImageId());
        tvTitle2.setText("   "+p.getmTitle2()+"      ||      收藏人数："+ p.getGoodr());
        tvAuthor2.setText(p.getmAuthor2());
        ivImage2.setImageBitmap(p.getmImageId2());
        if(p.getI1() == 0)
        {
            good_l.setImageResource(R.drawable.ic_baseline_grade_24);
        }
        else
        {
            good_l.setImageResource(R.drawable.ic_baseline_grade_24_red);
        }

        if(p.getI2() == 0)
        {
            good_r.setImageResource(R.drawable.ic_baseline_grade_24);
        }
        else
        {
            good_r.setImageResource(R.drawable.ic_baseline_grade_24_red);
        }
        //点赞收藏
        good_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (p.getI1() == 0){
                    new AlertDialog.Builder(view.getContext()).setTitle("提示").setMessage("加入收藏").setPositiveButton("确定" , null).show();
                    good_l.setImageResource(R.drawable.ic_baseline_grade_24_red);
                    p.setI1(1);
                    usephelp = new UPSQLhelpopen.UPdbopen(view.getContext());
                    SQLiteDatabase db = usephelp.getReadableDatabase();
                    usephelp.add(db,p.getAcount(),p.getmAuthor(),p.getmTitle());
                    mysqlhelp = new MySQLiteQpenHelper.MyDbOpenHelper(view.getContext());
                    db = mysqlhelp.getReadableDatabase();
                    Cursor cursor = db.query(
                            pContract.pEntry.TABLE_NAME,null,null,null,null,null,null
                    );
                    int goodIndex = cursor.getColumnIndex(pContract.pEntry.P_good);
                    int titleIndex = cursor.getColumnIndex(pContract.pEntry.P_TITLE);

                    int i = p.getGoodl();
                    while (cursor.moveToNext()){
                        String title = cursor.getString(titleIndex);
                        if(p.getmTitle().equals(title)){
                            i = cursor.getInt(goodIndex);
                        }
                    }
                    i++;
                    mysqlhelp.add_good(db, p.getmTitle(),i);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int j = p.getGoodl();
                            j++;
                            try {
                                txMysql.image_good(j,p.getmTitle());
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }).start();
                    tvTitle.setText(p.getmTitle() +"      ||      收藏人数："+ i);
                }
                else {
                    new AlertDialog.Builder(view.getContext()).setTitle("提示").setMessage("取消收藏"+p.getmTitle()).setPositiveButton("确定" , null).show();
                    good_l.setImageResource(R.drawable.ic_baseline_grade_24);
                    p.setI1(0);
                    usephelp = new UPSQLhelpopen.UPdbopen(view.getContext());
                    SQLiteDatabase db = usephelp.getReadableDatabase();
                    usephelp.delet(db,p.getAcount(),p.getmTitle());
                    mysqlhelp = new MySQLiteQpenHelper.MyDbOpenHelper(view.getContext());
                    db = mysqlhelp.getReadableDatabase();
                    Cursor cursor = db.query(
                            pContract.pEntry.TABLE_NAME,null,null,null,null,null,null
                    );
                    int goodIndex = cursor.getColumnIndex(pContract.pEntry.P_good);
                    int titleIndex = cursor.getColumnIndex(pContract.pEntry.P_TITLE);

                    int i = p.getGoodl();
                    while (cursor.moveToNext()){
                        String title = cursor.getString(titleIndex);
                        if(p.getmTitle().equals(title)){
                            i = cursor.getInt(goodIndex);
                        }
                    }

                    i--;
                    mysqlhelp.add_good(db, p.getmTitle(),i);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int j = p.getGoodl();
                            j--;
                            try {
                                txMysql.image_good(j,p.getmTitle());
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }).start();
                    tvTitle.setText(p.getmTitle() +"      ||     收藏人数： "+ i);
                }


            }
        });
        good_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (p.getI2() == 0){
                    new AlertDialog.Builder(view.getContext()).setTitle("提示").setMessage("加入收藏"+p.getmTitle2()).setPositiveButton("确定" , null).show();
                    good_r.setImageResource(R.drawable.ic_baseline_grade_24_red);
                    p.setI2(1);
                    usephelp = new UPSQLhelpopen.UPdbopen(view.getContext());
                    SQLiteDatabase db = usephelp.getReadableDatabase();
                    usephelp.add(db,p.getAcount(),p.getmAuthor2(),p.getmTitle2());
                    mysqlhelp = new MySQLiteQpenHelper.MyDbOpenHelper(view.getContext());
                    db = mysqlhelp.getReadableDatabase();
                    Cursor cursor = db.query(
                            pContract.pEntry.TABLE_NAME,null,null,null,null,null,null
                    );
                    int goodIndex = cursor.getColumnIndex(pContract.pEntry.P_good);
                    int titleIndex = cursor.getColumnIndex(pContract.pEntry.P_TITLE);

                    int i = p.getGoodr();
                    while (cursor.moveToNext()){
                        String title = cursor.getString(titleIndex);
                        if(p.getmTitle2().equals(title)){
                            i = cursor.getInt(goodIndex);
                        }
                    }
                    i++;
                    mysqlhelp.add_good(db, p.getmTitle2(),i);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int j = p.getGoodr();
                            j++;
                            try {
                                txMysql.image_good(j,p.getmTitle2());
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }).start();
                    tvTitle2.setText("   "+p.getmTitle2() +"      ||      收藏人数："+ i);

                }
                else {
                    new AlertDialog.Builder(view.getContext()).setTitle("提示").setMessage("取消收藏"+p.getmTitle()).setPositiveButton("确定" , null).show();
                    good_r.setImageResource(R.drawable.ic_baseline_grade_24);
                    p.setI2(0);
                    usephelp = new UPSQLhelpopen.UPdbopen(view.getContext());
                    SQLiteDatabase db = usephelp.getReadableDatabase();
                    usephelp.delet(db,p.getAcount(),p.getmTitle2());
                    mysqlhelp = new MySQLiteQpenHelper.MyDbOpenHelper(view.getContext());
                    db = mysqlhelp.getReadableDatabase();
                    Cursor cursor = db.query(
                            pContract.pEntry.TABLE_NAME,null,null,null,null,null,null
                    );
                    int goodIndex = cursor.getColumnIndex(pContract.pEntry.P_good);
                    int titleIndex = cursor.getColumnIndex(pContract.pEntry.P_TITLE);

                    int i = p.getGoodr();
                    while (cursor.moveToNext()){
                        String title = cursor.getString(titleIndex);
                        if(p.getmTitle2().equals(title)){
                            i = cursor.getInt(goodIndex);
                        }
                    }
                    i--;
                    mysqlhelp.add_good(db,p.getmTitle2(),i);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int j = p.getGoodr();
                            j--;
                            try {
                                txMysql.image_good(j,p.getmTitle2());
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }).start();
                    tvTitle2.setText("   "+p.getmTitle2() +"      ||     收藏人数： "+ i);

                }


            }

        });
        //打开左边图片
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = tvTitle.getText().toString();
                new AlertDialog.Builder(view.getContext()).setTitle("密码错误").setMessage(text).setPositiveButton("确定" , null).show();
                Intent intent = new Intent(view.getContext(), ShowActivity.class);
                intent.putExtra(MESSAGE_STRING_title,p.getmTitle());//发送查询图片标题
                intent.putExtra(MESSAGE_STRING_up,p.getmAuthor());
                mContext.startActivity(intent);
            }
        });
        //打开右边图片
        ivImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = tvTitle2.getText().toString();
                new AlertDialog.Builder(view.getContext()).setTitle("密码错误").setMessage(text).setPositiveButton("确定" , null).show();
                Intent intent = new Intent(view.getContext(), ShowActivity.class);
                intent.putExtra(MESSAGE_STRING_title,p.getmTitle2());//发送查询图片标题
                intent.putExtra(MESSAGE_STRING_up,p.getmAuthor2());
                mContext.startActivity(intent);
            }
        });
        return view;
    }
}
