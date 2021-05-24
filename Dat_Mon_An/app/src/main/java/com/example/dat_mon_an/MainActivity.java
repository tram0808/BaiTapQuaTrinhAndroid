package com.example.dat_mon_an;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        final ImageView imageView= findViewById(R.id.image_ts);
        final Button callButton= findViewById(R.id.btn);
        final RadioGroup rgSize= findViewById(R.id.size);
        final RadioGroup rgSugar= findViewById(R.id.sugar);
        final RadioGroup rgIce= findViewById(R.id.ice);
        final Button btnMap =findViewById(R.id.btnchi);
        final CheckBox ck1= findViewById(R.id.chk1);
        final CheckBox ck2= findViewById(R.id.chk2);
        final CheckBox ck3= findViewById(R.id.chk3);
        final CheckBox ck4= findViewById(R.id.chk4);
        final CheckBox ck5= findViewById(R.id.chk5);

        callButton.setOnClickListener(v -> {
            RadioButton rbSize=findViewById(rgSize.getCheckedRadioButtonId());
            RadioButton rbSugar=findViewById(rgSugar.getCheckedRadioButtonId());
            RadioButton rbIce=findViewById(rgIce.getCheckedRadioButtonId());
            String phoneNumber = "0706974677";
            String topping="\n";
            if (ck1.isChecked())
                topping=topping+ck1.getText();
            if (ck2.isChecked())
                topping=topping+"-"+ck2.getText();
            if (ck3.isChecked())
                topping=topping+"-"+ck3.getText();
            if (ck4.isChecked())
                topping=topping+"-"+ck4.getText();
            if (ck5.isChecked())
                topping=topping+"-"+ck5.getText();

            String message = "Tôi muốn đặt một ly: \nTRÀ SỮA OOLONG NƯỚNG"+"\n-"+rbSize.getText()+"\n-"+rbSugar.getText()+"\n-"+rbIce.getText()+topping+".";
            Intent intent = new Intent(Intent.ACTION_SENDTO,

                    Uri.parse("sms:" + phoneNumber));

            intent.putExtra("sms_body", message);
            startActivity(intent);
        });

        imageView.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this,Detail_Product.class);
            startActivity(intent);
        });

        btnMap.setOnClickListener(v -> {
            String url= "https://goo.gl/maps/wvvEteyi3xauEyzp8";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

    }
}