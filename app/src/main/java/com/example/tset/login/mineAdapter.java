package com.example.tset.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tset.R;

import java.util.List;

public class mineAdapter  extends ArrayAdapter<mine> {
    private List<mine> mineList;
    private Context mContext;
    private int resourceId;
    public  mineAdapter(Context context, int resourceId,List<mine> data){
        super(context, resourceId, data);
        this.mContext = context;
        this.mineList = data;
        this.resourceId = resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        mine mines = getItem(position);
        View view ;
        view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView username = view.findViewById(R.id.username);
        ImageView Imageid = view.findViewById(R.id.userimge);
        username.setText(mines.getUername());
        Imageid.setImageBitmap(mines.getmImageId());
        return view;
    }
}
