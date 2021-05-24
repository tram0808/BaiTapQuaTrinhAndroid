package com.example.selfie;

import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class GalleryActivity extends AppCompatActivity {

    ListView list;
    ArrayList<HinhAnh> arrayHinhAnh;
    HinhAnhAdapter adapter;
    CheckBox chB;
    Button btnXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.gallery);

         chB=(CheckBox) findViewById(R.id.checkBox);
         btnXoa=(Button) findViewById(R.id.btnDel);

        list=(ListView) findViewById(R.id.listView);
        arrayHinhAnh= new ArrayList<>();
        adapter=new HinhAnhAdapter(GalleryActivity.this,  arrayHinhAnh);
        list.setAdapter(adapter);

        getData();
        registerForContextMenu(list);

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GalleryActivity.this,"Đã xóa",Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void getData(){
        //lay du lieu
        Cursor cursor=MainActivity.database.GetData("SELECT * FROM HinhAnh");
        arrayHinhAnh.clear();
        while (cursor.moveToNext()){
            arrayHinhAnh.add(new HinhAnh(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getBlob(2)
            ));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater im=getMenuInflater();
        im.inflate(R.menu.menu_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        HinhAnh Img= arrayHinhAnh.get(info.position);
        MainActivity.database.QuerryData("DELETE FROM HinhAnh WHERE Ten='"+Img.getTen()+"'");
        Toast.makeText(this,"Đã xóa", Toast.LENGTH_SHORT).show();
        getData();
        return false;
    }
}
