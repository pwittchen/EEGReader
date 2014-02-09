package com.pwittchen.eegreader.activity.contract;

import android.app.ProgressDialog;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.widget.CheckBox;
import android.widget.TextView;

import com.pwittchen.eegreader.menu.GenericMenuItem;

import java.util.Map;

public interface ActivitySettingsContract {
    CheckBox getCbKeepScreenTurnedOn();
    CheckBox getCbSaveEegAndEyeBlinkingData();
    CheckBox getCbEnableVoiceFeedback();
    TextView getTvAppAuthorNote();
    Map<Integer, GenericMenuItem> getOptionsMenu();
    TextToSpeech getTextToSpeech();
    ProgressDialog getProgressDialog();
    void setProgressDialog(ProgressDialog progressDialog);
    void setSavedSignalsCount(String count);
    void callStartActivity(Intent intent);
}
