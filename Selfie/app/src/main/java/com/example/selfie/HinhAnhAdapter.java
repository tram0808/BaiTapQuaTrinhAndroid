package com.example.selfie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

//import com.java2blog.androidrestjsonexample.Country;
//import com.java2blog.androidrestjsonexample.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HinhAnhAdapter extends BaseAdapter {


    private Context context;
    ArrayList<HinhAnh> images;


    public HinhAnhAdapter(Context context, ArrayList<HinhAnh> images) {
        this.context = context;
        this.images=images;

    }

    private class ViewHolder {
        TextView txtName;
        ImageView imgAnh;
        CheckBox check;

        public ViewHolder(View view) {
            txtName = (TextView) view.findViewById(R.id.nameImg);
            imgAnh = (ImageView) view.findViewById(R.id.hinhRow);
           check =(CheckBox) view.findViewById(R.id.checkBox);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtName.setText(images.get(position).getTen());

        //chuyen byte[] -> bitmap
        byte[] img= images.get(position).getHinh();
        Bitmap bitmap= BitmapFactory.decodeByteArray(img, 0, img.length);

        viewHolder.imgAnh.setImageBitmap(bitmap);

        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return position;
    }

    public int getCount() {

        if(images.size()<=0)
            return 1;
        return images.size();
    }
}

