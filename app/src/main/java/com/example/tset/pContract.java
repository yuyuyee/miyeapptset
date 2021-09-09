package com.example.tset;

import android.provider.BaseColumns;

public final class pContract {
    private pContract(){}
    public static class pEntry implements BaseColumns{
        public static final String TABLE_NAME = "tbl_p";
        public static final String P_TITLE = "TITLE";//标题
        public static final String P_AUTHOR = "author";//上传用户
        public static final String P_good = "good";//点赞数量
        public static final String P_IMAGE = "image";

        public static final  String TABLE_ACCOUNT = "tbl_use";
        public static final  String USER = "user";//账号
        public static final  String USER_PWR = "user_pwr";//密码
        public static final  String U_IMAGE = "user_image";//头像

        public static final  String TABLE_P_U = "tbl_use_P";



    }
}
