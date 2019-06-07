package com.quang.duoi_hinh_batchu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.quang.duoi_hinh_batchu.dialog.AnswerDialog;
import com.quang.duoi_hinh_batchu.dialog.ExitDialog;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        fullScreen();

    }

    private void ring() {
        MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.ring);
        ring.start();
    }

    private void fullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initView() {
        findViewById(R.id.iv_choi_thu).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_choi_thu:
                goToPlay();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        ExitDialog exitDialog = new ExitDialog(this);
        exitDialog.show();
    }

    private void goToPlay() {
        Intent intent = new Intent();
        intent.setClass(this, PlayActivity.class);
        startActivity(intent);
    }
}
