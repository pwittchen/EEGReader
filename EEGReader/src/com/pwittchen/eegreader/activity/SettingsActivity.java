package com.pwittchen.eegreader.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.pwittchen.eegreader.R;
import com.pwittchen.eegreader.activity.contract.ActivitySettingsContract;
import com.pwittchen.eegreader.activity.controller.SettingsController;
import com.pwittchen.eegreader.generics.GenericActivity;
import com.pwittchen.eegreader.generics.GenericApplication;
import com.pwittchen.eegreader.menu.GenericMenuItem;
import com.pwittchen.eegreader.utils.ConfigUtils;

import java.util.HashMap;
import java.util.Map;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

/**
 * Functionality responsible for saving data was turned off in this project.
 * If you want to enable saving signals into SQLite database,
 * uncomment commented lines in this class and proper fields activity_settings.xml layout.
 */
@ContentView(R.layout.activity_settings)
public class SettingsActivity extends GenericActivity implements ActivitySettingsContract, TextToSpeech.OnInitListener {

    private static final String TAG = makeLogTag(SettingsActivity.class);
    private Map<Integer, GenericMenuItem> optionsMenu = new HashMap<Integer, GenericMenuItem>();
    private SettingsController settingsController;
    private ProgressDialog progressDialog;

    @InjectView(R.id.cb_keep_screen_turned_on)
    private CheckBox cbKeepScreenTurnedOn;
//    @InjectView(R.id.cb_save_eeg_and_eye_blinking_data)
//    private CheckBox cbSaveEegAndEyeBlinkingData;
    @InjectView(R.id.cb_enable_voice_feedback)
    private CheckBox cbEnableVoiceFeedback;
    @InjectView(R.id.tv_app_author_note)
    private TextView tvAppAuthorNote;
//    @InjectView(R.id.tv_saved_signals_count)
//    private TextView tvSavedSignalsCount;

    private TextToSpeech textToSpeech;

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(ConfigUtils.TTSLocale);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsController = new SettingsController(this);
        textToSpeech = new TextToSpeech(this, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_settings, menu);
        getSherlock().getActionBar().setDisplayHomeAsUpEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (optionsMenu.containsKey(item.getItemId())) {
            optionsMenu.get(item.getItemId()).activity(this).item(item).run();
        }
        return super.onMenuItemSelected(featureId, item);
    }

    public void setSavedSignalsCount(String count) {
//        tvSavedSignalsCount.setText(count);
    }

    public void callStartActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public CheckBox getCbKeepScreenTurnedOn() {
        return cbKeepScreenTurnedOn;
    }

    @Override
    public CheckBox getCbSaveEegAndEyeBlinkingData() {
//        return cbSaveEegAndEyeBlinkingData; // uncomment this line if you want to enable database
        return new CheckBox(GenericApplication.getContext()); // comment this field if you want to enable database
    }

    @Override
    public CheckBox getCbEnableVoiceFeedback() {
        return cbEnableVoiceFeedback;
    }

    public TextView getTvAppAuthorNote() {
        return tvAppAuthorNote;
    }

    public Map<Integer, GenericMenuItem> getOptionsMenu() {
        return optionsMenu;
    }

    public TextToSpeech getTextToSpeech() {
        return textToSpeech;
    }

    public void clearDataOnDevice(View view) {
        settingsController.clearDataOnDevice();
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }
}
