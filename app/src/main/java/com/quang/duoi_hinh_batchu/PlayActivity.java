package com.quang.duoi_hinh_batchu;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.quang.duoi_hinh_batchu.module.AppData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MAX_KYTU = 16;
    private static final String chars = "ABCDEGHIKLMNOPQRSTUVXY";
    private static final String TAG = "TAG";
    Random random = new Random();

    private Typeface mTypeface;

    private ImageView mIvPicture;
    private LinearLayout mAnswer1;
    private LinearLayout mAnswer2;
    private LinearLayout mPlan1;
    private LinearLayout mPlan2;
    private String dapan = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mTypeface = Typeface.createFromAsset(getAssets(), "fonts/UTM_Cookies_0.ttf");
        initView();
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
        showInfor();
    }

    private void showInfor() {
        try {
            AppData appData = new AppData(this);
            appData.toNextQuestion();
            mIvPicture.setImageDrawable(appData.getImageDrawable());
            ArrayList<String> answer = appData.getShortAnswer();
            int size = answer.size();
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
                setvisible(button);
            } else {
                mAnswer2.addView(button);
                setvisible(button);
            }
        }
    }

    private Button setvisible(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText() != "") {
                    String bt = (String) button.getText();
                    button.setText("");
                    for (int i = 0; i < mPlan1.getChildCount(); i++) {
                        Button button1 = (Button) mPlan1.getChildAt(i
                        );
                        findViewById(button1.getId());
                        if (bt == button1.getText()) {
                            button1.setVisibility(View.VISIBLE);
                            Log.i(TAG, "childAt 1 : " + button.getId());
                        }
                    }
                    for (int i = 0; i < mPlan2.getChildCount(); i++) {
                        Button button1 = (Button) mPlan2.getChildAt(i);
                        findViewById(button1.getId());
                        if (bt == button1.getText()) {
                            button1.setVisibility(View.VISIBLE);
                        }
                        Log.i(TAG, "childAt 2 : " + button.getId());
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
                for (int i = 0; i < mAnswer1.getChildCount(); i++) {
                    Button button1 = (Button) mAnswer1.getChildAt(i);
                    if (button1.getText() == "") {
                        dapan = button.getText().toString();
                        button1.setText(dapan);
                        button.setVisibility(View.INVISIBLE);
                        return;
                    }
                }
                for (int i = 0; i < mAnswer2.getChildCount(); i++) {
                    Button button1 = (Button) mAnswer2.getChildAt(i);
                    if (button1.getText() == "") {
                        dapan = button.getText().toString();
                        button1.setText(dapan);
                        button.setVisibility(View.INVISIBLE);
                        return;
                    }
                }
            }
        });
        return button;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tiep:
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }


}
