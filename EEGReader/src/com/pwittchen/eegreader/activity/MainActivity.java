package com.pwittchen.eegreader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.pwittchen.eegreader.R;
import com.pwittchen.eegreader.activity.contract.ActivityMainContract;
import com.pwittchen.eegreader.activity.contract.GenericActivitySignalContract;
import com.pwittchen.eegreader.activity.controller.MainController;
import com.pwittchen.eegreader.activity.viewholder.MainActivityViewHolder;
import com.pwittchen.eegreader.dialog.PromptDialog;
import com.pwittchen.eegreader.generics.GenericActivity;
import com.pwittchen.eegreader.generics.GenericApplication;
import com.pwittchen.eegreader.menu.GenericMenuItem;
import com.pwittchen.eegreader.menu.contract.MainActivityMenuContract;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;

import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

@ContentView(R.layout.activity_main)
public class MainActivity extends GenericActivity implements GenericActivitySignalContract, ActivityMainContract, MainActivityMenuContract, TextToSpeech.OnInitListener {

    private static final String TAG = makeLogTag(MainActivity.class);

    @InjectResource(R.string.bluetooth_is_not_available)
    private String bluetoothIsNotAvailable;
    @InjectResource(R.string.connected_to_device)
    private String connectedToDevice;
    @InjectResource(R.string.disconnected_from_device)
    private String disconnectedFromDevice;
    @InjectResource(R.string.attention_level)
    private String attentionLevel;
    @InjectResource(R.string.meditation_level)
    private String meditationLevel;
    @InjectResource(R.string.blink_level)
    private String blinkLevel;
    @InjectResource(R.string.raw_data)
    private String rawData;

    private Map<Integer, GenericMenuItem> optionsMenu = new HashMap<Integer, GenericMenuItem>();
    private ActionBar actionBar;
    private FragmentManager fragmentManager;
    private MainController mainController;
    private TextToSpeech textToSpeech;
    private MainActivityViewHolder viewHolder;

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(Locale.US);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textToSpeech = new TextToSpeech(this, this);
        fragmentManager = getSupportFragmentManager();
        actionBar = getSupportActionBar();
        mainController = new MainController(this);
        viewHolder = new MainActivityViewHolder(getCurrentView());
    }

    private View getCurrentView() {
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!GenericApplication.getTGDeviceUtils().initializeBlueToothAdapter()) {
            Toast.makeText(this, bluetoothIsNotAvailable, Toast.LENGTH_LONG).show();
        } else {
            GenericApplication.getTGDeviceUtils().setActivityViewContract(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (optionsMenu.containsKey(item.getItemId())) {
            optionsMenu.get(item.getItemId()).activity(this).item(item).run();
        }
        return super.onMenuItemSelected(featureId, item);
    }

    public void startSettingsActivity(MenuItem item) {
        startActivity(new Intent(GenericApplication.getContext(), SettingsActivity.class));
    }

    public void connectOrDisconnectToDevice(MenuItem item) {
        mainController.connectOrDisconnectToDevice(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GenericApplication.getTGDeviceUtils().disconnectFromDevice();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GenericApplication.getTGDeviceUtils().disconnectFromDevice();
    }

    @Override
    public void onBackPressed() {
        PromptDialog applicationCloseDialog = new PromptDialog();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };

        applicationCloseDialog.activity(this).createDialog(R.string.close_application_question, runnable).show();
    }

    @Override
    public void setMessageFromDevice(String messageFromDevice) {
    }

    @Override
    public void setAttentionLevel(int level) {
        mainController.setAttentionLevel(level);
    }

    @Override
    public void setMeditationLevel(int level) {
        mainController.setMeditationLevel(level);
    }

    @Override
    public void setBlinkLevel(int level) {
        mainController.setBlinkLevel(level);
    }

    @Override
    public void setRawData(int level) {
        mainController.setRawData(level);
    }

    @Override
    public void setDelta(int level) {
        mainController.setWaveDelta(level);
    }

    @Override
    public void setTheta(int level) {
        mainController.setWaveTheta(level);
    }

    @Override
    public void setLowAlpha(int level) {
        mainController.setWaveAlphaLow(level);
    }

    @Override
    public void setHighAlpha(int level) {
        mainController.setWaveAlphaHigh(level);
    }

    @Override
    public void setLowBeta(int level) {
        mainController.setWaveBetaLow(level);
    }

    @Override
    public void setHighBeta(int level) {
        mainController.setWaveBetaHigh(level);
    }

    @Override
    public void setLowGamma(int level) {
        mainController.setWaveGammaLow(level);
    }

    @Override
    public void setMidGamma(int level) {
        mainController.setWaveGammaMid(level);
    }

    @Override
    public MainActivityViewHolder getViewHolder() {
        return viewHolder;
    }

    public String getConnectedToDevice() {
        return connectedToDevice;
    }

    public String getDisconnectedFromDevice() {
        return disconnectedFromDevice;
    }

    public Map<Integer, GenericMenuItem> getOptionsMenu() {
        return optionsMenu;
    }

    public TextToSpeech getTextToSpeech() {
        return textToSpeech;
    }
}