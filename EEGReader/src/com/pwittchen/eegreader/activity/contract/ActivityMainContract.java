package com.pwittchen.eegreader.activity.contract;

import android.speech.tts.TextToSpeech;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.pwittchen.eegreader.activity.viewholder.MainActivityViewHolder;
import com.pwittchen.eegreader.menu.GenericMenuItem;

import java.util.Map;

public interface ActivityMainContract {
    String getConnectedToDevice();
    String getDisconnectedFromDevice();
    Map<Integer, GenericMenuItem> getOptionsMenu();
    TextToSpeech getTextToSpeech();
    MainActivityViewHolder getViewHolder();
}
