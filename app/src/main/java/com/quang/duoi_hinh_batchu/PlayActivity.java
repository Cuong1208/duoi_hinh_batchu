package com.quang.duoi_hinh_batchu;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quang.duoi_hinh_batchu.dialog.AnswerDialog;
import com.quang.duoi_hinh_batchu.dialog.ItemDialog;
import com.quang.duoi_hinh_batchu.dialog.SupportDialog;
import com.quang.duoi_hinh_batchu.dialog.UserDialog;
import com.quang.duoi_hinh_batchu.module.AppData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MAX_KYTU = 16;
    private static final String chars = "ABCDEGHIKLMNOPQRSTUVXY";
    private static final String TAG = "TAG";

    private static final int LEVEL_MUSIC_ON = 0;
    private static final int LEVEL_MUSIC_OFF = 1;
    private static final String SHARED_PREFERENCES_COIN = "SHARED_PREFERENCES_COIN";

    Random random = new Random();
    private String kq = "";
    private int size;
    private int level;
    private int heart = 5;
    private int numberAnswer = 0;
    private int point = 0;
    private String dapan = "";
    private Button mBtnNext;
    private Typeface mTypeface;

    private ImageView mIvMusic, mIvPicture;
    private LinearLayout mAnswer1, mAnswer2, mPlan1, mPlan2;
    private TextView mTvHeart, mTvNumberAnswer, mTvPoint;

    SharedPreferences sharedPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mTypeface = Typeface.createFromAsset(getAssets(), "fonts/UTM_Cookies_0.ttf");
        initView();
        fullScreen();
        ring();
    }

    private void ring() {
        MediaPlayer ring = MediaPlayer.create(PlayActivity.this, R.raw.ring);
            ring.start();
    }

    private void setIvMusic() {
        level = mIvMusic.getDrawable().getLevel();
        mIvMusic.setImageLevel(level == LEVEL_MUSIC_ON
                ? LEVEL_MUSIC_OFF : LEVEL_MUSIC_ON);
    }

    private void fullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        mIvPicture = findViewById(R.id.iv_picture);
        mAnswer1 = findViewById(R.id.anwser1);
        mAnswer2 = findViewById(R.id.anwser2);
        mPlan1 = findViewById(R.id.plan1);
        mPlan2 = findViewById(R.id.plan2);

        mBtnNext = findViewById(R.id.btn_tiep);
        mBtnNext.setOnClickListener(this);

        mTvHeart = findViewById(R.id.txt_heart);
        mTvHeart.setText(String.valueOf(heart));

        mTvPoint = findViewById(R.id.txt_point);
        findViewById(R.id.txt_point).setOnClickListener(this);

        mTvNumberAnswer = findViewById(R.id.tv_numberAnswer);

        mIvMusic = findViewById(R.id.iv_music);
        findViewById(R.id.iv_music).setOnClickListener(this);

        findViewById(R.id.btn_tiep).setOnClickListener(this);
        findViewById(R.id.fm_user).setOnClickListener(this);
        findViewById(R.id.txt_heart).setOnClickListener(this);
        findViewById(R.id.txt_suggest).setOnClickListener(this);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_COIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        mLvDrawable = (LevelListDrawable) mIvMusic.getDrawable();
        showInfor();

    }

    private void showInfor() {
        try {
            AppData appData = new AppData(this);
            appData.toNextQuestion();
            mIvPicture.setImageDrawable(appData.getImageDrawable());
            ArrayList<String> answer = appData.getShortAnswer();
            kq = appData.getKQ();
            size = answer.size();
            showAnswer(size);
            // Thêm các ký tự trong 16 ký tự
            for (int i = 0; i < MAX_KYTU - size; i++) {
                int numberRandom = random.nextInt(20);
                String kytu = RandomString(numberRandom);
                answer.add(kytu);
            }
            // Hiển thị 16 ký tự
            Collections.shuffle(answer);
            viewAnswer(answer);
            Toast.makeText(this, appData.getSuggest(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAnswer(int size) {
        for (int i = 0; i < size; i++) {
            final Button button = new Button(this);
            button.setBackgroundResource(R.drawable.tile_empty);
            button.setText("");
            button.setId(i);
            button.setTypeface(mTypeface);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120, 135);
            button.setLayoutParams(params);

            if (i < 8) {
                mAnswer1.addView(button);
                setvisible(button, mAnswer1);
            } else {
                mAnswer2.addView(button);
                setvisible(button, mAnswer2);
            }
        }
    }

    private Button setvisible(final Button button, final LinearLayout mAnswer) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText() != "") {
                    String bt = (String) button.getText();
                    button.setText("");

                    for (int i = 0; i < mAnswer.getChildCount(); i++) {
                        Button button1 = (Button) mAnswer.getChildAt(i);
                        button1.setBackgroundResource(R.drawable.tile_empty);
                    }
                    for (int i = 0; i < mPlan1.getChildCount(); i++) {
                        Button button1 = (Button) mPlan1.getChildAt(i);
                        findViewById(button1.getId());

                        if (bt == button1.getText()) {
                            button1.setVisibility(View.VISIBLE);
                        }
                    }

                    for (int i = 0; i < mPlan2.getChildCount(); i++) {
                        Button button1 = (Button) mPlan2.getChildAt(i);
                        if (bt == button1.getText()) {
                            button1.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
        return button;
    }


    private String RandomString(int length) {
        return chars.substring(length, length + 1);
    }

    private void viewAnswer(final ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            final Button button = new Button(this);
            button.setBackgroundResource(R.drawable.tile_hover);
            button.setText(list.get(i));
            button.setId(i);
            button.setTypeface(mTypeface);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120, 135);
            button.setLayoutParams(params);
            if (i < 8) {
                mPlan1.addView(button);
                setinvisible(button);
            } else {
                mPlan2.addView(button);
                setinvisible(button);
            }
        }
    }

    private Button setinvisible(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String da = "";
                for (int i = 0; i < mAnswer1.getChildCount(); i++) {
                    Button button1 = (Button) mAnswer1.getChildAt(i);
                    if (button1.getText() == "") {
                        dapan = button.getText().toString();
                        button1.setText(dapan);
                        button.setVisibility(View.INVISIBLE);
                        checkAnswer(da, mAnswer1);
                        return;
                    }

                }
                for (int i = 0; i < mAnswer2.getChildCount(); i++) {
                    Button button1 = (Button) mAnswer2.getChildAt(i);
                    if (button1.getText() == "") {
                        dapan = button.getText().toString();
                        button1.setText(dapan);
                        button.setVisibility(View.INVISIBLE);
                        checkAnswer(da, mAnswer2);
                        return;
                    }

                }
            }
        });
        return button;
    }

    private void checkAnswer(String da, LinearLayout answer) {
        //lay dap an
        for (int k = 0; k < answer.getChildCount(); k++) {
            Button btn = (Button) answer.getChildAt(k);
            String text = btn.getText().toString();
            if (text != "") {
                da += text;
            }
        }
        //check KQ
        if (da.length() == size) {
            if (da.equals(kq)) {
                Toast.makeText(this, "Thiên tài!!!", Toast.LENGTH_SHORT).show();
                numberAnswer++;
                point += 100;


                mTvNumberAnswer.setText(String.valueOf(numberAnswer));
                mTvPoint.setText(String.valueOf(point));
                mBtnNext.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Bạn đã trả lời sai!", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < answer.getChildCount(); i++) {
                    Button button1 = (Button) answer.getChildAt(i);
                    button1.setBackgroundResource(R.drawable.tile_false);
                }
                heart--;
                mTvHeart.setText(String.valueOf(heart));
                if (heart == 0) {
                    Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void newQuestion() {
        mAnswer1.removeAllViews();
        mAnswer2.removeAllViews();
        mPlan1.removeAllViews();
        mPlan2.removeAllViews();
        mBtnNext.setVisibility(View.GONE);
        showInfor();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tiep:
                newQuestion();
                break;
            case R.id.fm_user:
                UserDialog userDialog = new UserDialog(this);
                userDialog.show();
                break;
            case R.id.txt_heart:
                AnswerDialog answerDialog = new AnswerDialog(this);
                answerDialog.show();
                break;
            case R.id.txt_suggest:
                SupportDialog supportDialog = new SupportDialog(this);
                supportDialog.show();
                break;
            case R.id.txt_point:
                ItemDialog itemDialog = new ItemDialog(this);
                itemDialog.show();
                break;
            case R.id.iv_music:
                setIvMusic();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }


}