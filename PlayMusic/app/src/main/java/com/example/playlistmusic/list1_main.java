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

public class list1_main extends ListActivity {
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String[] values = new String[]{"I like me better" , "I'm so tired" , "Mean It" , "Paris in the rain"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        if (position == 0) {
            Intent intent = new Intent(this, ui_list1.class);
            startActivity(intent);
        };
        if (position == 1) {
            Intent intent = new Intent(this, ui_list1.class);
            startActivity(intent);
        };
        if (position == 2) {
            Intent intent = new Intent(this, ui_list1.class);
            startActivity(intent);
        };
        if (position == 3){
            Intent intent = new Intent(this, ui_list1.class);
            startActivity(intent);
        }
    }
}