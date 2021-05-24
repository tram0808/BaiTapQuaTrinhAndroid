package com.example.playlistmusic;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.SimpleTimeZone;

import static com.example.playlistmusic.MainActivity.mediaPlayer;

public class ui_list0 extends AppCompatActivity {

    TextView txtTitle, txtTimeSong, txtTimeTotal;
    SeekBar skSong;
    ImageButton btnPre, btnNext, btnList, btnPlay;

    ImageView imgHinh;
    ArrayList <Song> arraySong;
    int positon = 0;
    Animation animation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        AnhXa();
        AddSong();

        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
        mediaPlayer.release();
        KhoiTaoMediaPlay();
        mediaPlayer.start();

        imgHinh.startAnimation(animation);
        skSong.setProgress(mediaPlayer.getCurrentPosition());
        btnPlay.setImageResource(R.drawable.pause);
        SetTimeTotal();
        UpdateTimeSong();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positon++;
                if(positon>arraySong.size()-1){
                    positon=0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlay();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positon--;
                if(positon<0){
                    positon=arraySong.size()-1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlay();
                mediaPlayer.isPlaying();
                btnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });

        btnList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ui_list0.this, ListSong.class);
                startActivity(intent);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    //Nếu đang phát nhạc --> pause --> đổi hình play
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                }else {
                    //Nếu đang ngừng phát --> play --> đổi hình pause
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                }
                SetTimeTotal();
                UpdateTimeSong();
                if(mediaPlayer.isPlaying()){
                    imgHinh.startAnimation(animation);
                } else{
                    imgHinh.clearAnimation();
                }
            }
        });

        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //kéo liên tục, cập nhật liên tục
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //chạm vào seekbar cập nhật liền, kéo thì ko
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //di chuyển ko có gì, vừa bỏ chạm mới lấy giá trị
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });
    }

    private void SetTimeTotal(){
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(dinhDangGio.format(mediaPlayer.getDuration()));
        //gán max của skSong = mediaPlayer.getDuration()
        skSong.setMax(mediaPlayer.getDuration());
    }

    private void UpdateTimeSong(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));
                //update progress song
                skSong.setProgress(mediaPlayer.getCurrentPosition());
                //kiểm tra xem nếu kết thúc bài hát -->next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        positon++;
                        if(positon>arraySong.size()-1){
                            positon=0;
                        }
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        KhoiTaoMediaPlay();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                        SetTimeTotal();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this,500);
            }
        },100);
    }

    private void KhoiTaoMediaPlay(){
        mediaPlayer=MediaPlayer.create(ui_list0.this,arraySong.get(positon).getFile());
        txtTitle.setText(arraySong.get(positon).getTitle());
    }

    private void AddSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Dành cho em - Hoàng Tôn",R.raw.danh_cho_em));
        arraySong.add(new Song("Đôi lời - Hoàng Dũng",R.raw.doi_loi));
        arraySong.add(new Song("Đừng chờ anh nữa - Tăng Phúc",R.raw.dung_cho_anh_nua));
        arraySong.add(new Song("Người và ta - Rhymastic",R.raw.nguoi_va_ta));
        arraySong.add(new Song("Về phía mưa - Thế Bảo",R.raw.ve_phia_mua));
    }

    private void AnhXa() {
        txtTimeSong=findViewById(R.id.textviewTimeSong);
        txtTimeTotal=findViewById(R.id.textviewTimeTotal);
        txtTitle=findViewById(R.id.textviewTitle);
        skSong=findViewById(R.id.seekBarSong);
        btnNext=findViewById(R.id.next);
        btnPlay=findViewById(R.id.play);
        btnPre=findViewById(R.id.pre);
        btnList=findViewById(R.id.list);
        imgHinh=findViewById(R.id.cd);

    }
}
