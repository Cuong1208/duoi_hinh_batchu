package com.quang.duoi_hinh_batchu.module;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public interface IAppData {
    void toNextQuestion();

    ArrayList<String> getFullAnswer();

    Drawable getImageDrawable();

    String getDescription();

    int getId();

    String getSuggest();

    String getKQ();

    ArrayList<String> getShortAnswer();

    ArrayList<String> getSelectedCharacter();

    int getResCharNum();
}
