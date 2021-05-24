package com.example.playlistmusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListSong extends ListActivity {
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String[] values = new String[]{"Âu Mỹ" , "Nhạc Việt" , "Nhạc Hoa"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        if (position == 0){
            Intent intent = new Intent(this, list1_main.class);
            startActivity(intent);
        }
        else if (position == 1){
            Intent intent = new Intent(this, list0_main.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, list2_main.class);
            startActivity(intent);
        }
    }
}