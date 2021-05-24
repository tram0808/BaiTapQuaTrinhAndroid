package com.example.selfie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;


import android.os.Bundle;

import android.provider.MediaStore;

import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    int RESULT_LOAD_IMAGE = 200;
    private TextureView textureView;
    private Button btnCam;
    private Button btnThem;
    private ImageView image;
    private Button btnThuVien;
    public static Database database;

    int REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        image= (ImageView) findViewById(R.id.imageView);
        btnCam= (Button) findViewById(R.id.btn);
        btnThem=(Button) findViewById(R.id.btnAdd);
        btnThuVien=(Button) findViewById(R.id.btnGallery);
        btnThuVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GalleryActivity.class));
            }
        });

        database= new Database(this, "Gallery.sqlite",null,1);
        database.QuerryData("CREATE TABLE IF NOT EXISTS HinhAnh(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Ten VARCHAR(150), Anh BLOG)");


        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
                clickStartService();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_.jpg";

                //chuyen data cua imageview -> byte[]
                BitmapDrawable bitmapDrawable= (BitmapDrawable) image.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArray);
                byte[] hinhAnh=byteArray.toByteArray();

                database.Add_Image(imageFileName, hinhAnh);
                Toast.makeText(MainActivity.this,"Đã thêm ảnh",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,GalleryActivity.class));
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE && grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,REQUEST_CODE);

        }
        else
            Toast.makeText(MainActivity.this,"You can't use camera without permission",Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void clickStartService() {
        Bitmap bitma= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        Notification notification =new NotificationCompat.Builder(this, MyApp.CHANNEL_ID)
                .setContentTitle("Chụp ảnh mới")
                .setContentText("Mời bạn chụp ảnh mới")
                .setSmallIcon(R.drawable.download)
//                    .setLargeIcon(bitma)
                .setColor(getResources().getColor(R.color.white))
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager!=null){
            notificationManager.notify(getNotificationId(),notification);
        }
    }
    private int getNotificationId(){
        return (int) new Date().getTime();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE  && resultCode == RESULT_OK && data != null){
            //setPic();
            //galleryAddPic();
            Bitmap bitmap= (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}