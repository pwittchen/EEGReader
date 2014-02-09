package com.pwittchen.eegreader.activity.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.pwittchen.eegreader.R;
import com.pwittchen.eegreader.activity.contract.ActivitySettingsContract;
import com.pwittchen.eegreader.database.DatabaseHandler;
import com.pwittchen.eegreader.database.asynctask.DeleteAllSignalsTask;
import com.pwittchen.eegreader.dialog.PromptDialog;
import com.pwittchen.eegreader.generics.GenericApplication;
import com.pwittchen.eegreader.menu.item.MenuItemHome;
import com.pwittchen.eegreader.preferences.SettingsSharedPreferences;
import com.pwittchen.eegreader.utils.StringUtils;

import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

public class SettingsController {

    private static final String TAG = makeLogTag(SettingsController.class);

    private ActivitySettingsContract activity;
    private SettingsSharedPreferences settingsSharedPreferences = new SettingsSharedPreferences();

    public SettingsController(ActivitySettingsContract activity) {
        setActivity(activity);
        setOptionsMenu();
        setCheckboxes();
        setListeners();
        setSavedSignalsCount();
    }

    private void setSavedSignalsCount() {
        activity.setSavedSignalsCount(StringUtils.getStringFromResources(R.string.number_of_saved_signals) + " " + getSavedSignalsCount());
    }

    public void resetSavedSignalsCount() {
        activity.setSavedSignalsCount(StringUtils.getStringFromResources(R.string.number_of_saved_signals) + " 0");
    }

    public void resetSavedSignalsCountWithDelay(int delayInMilSeconds) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetSavedSignalsCount();
            }
        }, delayInMilSeconds);
    }

    private void setOptionsMenu() {
        activity.getOptionsMenu().put(android.R.id.home, new MenuItemHome());
    }

    private void setCheckboxes() {
        activity.getCbKeepScreenTurnedOn().setChecked(settingsSharedPreferences.get(SettingsSharedPreferences.keepScreenTurnedOn));
        activity.getCbSaveEegAndEyeBlinkingData().setChecked(settingsSharedPreferences.get(SettingsSharedPreferences.saveEegAndEyeBlinkingData));
        activity.getCbEnableVoiceFeedback().setChecked(settingsSharedPreferences.get(SettingsSharedPreferences.enableVoiceFeedback));
    }

    private void setListeners() {
        setKeepScreenTurnedOnCheckedListener();
        setSaveEegAndEyeBlinkingDataCheckedListener();
        setEnableVoiceFeedbackCheckedListener();
        setAppAuthorNoteOnClickListener();
    }

    private void setKeepScreenTurnedOnCheckedListener() {
        activity.getCbKeepScreenTurnedOn().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                settingsSharedPreferences.put(SettingsSharedPreferences.keepScreenTurnedOn, checked);
            }
        });
    }

    private void setSaveEegAndEyeBlinkingDataCheckedListener() {
        activity.getCbSaveEegAndEyeBlinkingData().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                settingsSharedPreferences.put(SettingsSharedPreferences.saveEegAndEyeBlinkingData, checked);
            }
        });
    }

    private void setAppAuthorNoteOnClickListener() {
        activity.getTvAppAuthorNote().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(StringUtils.getStringFromResources(R.string.app_author_website)));
                activity.callStartActivity(intent);
            }
        });
    }

    private String getVoiceFeedbackActivatedString() {
        return StringUtils.getStringFromResources(R.string.voice_feedback_activated);
    }

    private void setEnableVoiceFeedbackCheckedListener() {
        activity.getCbEnableVoiceFeedback().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                settingsSharedPreferences.put(SettingsSharedPreferences.enableVoiceFeedback, checked);
                if (checked) {
                    activity.getTextToSpeech().speak(getVoiceFeedbackActivatedString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    public void clearDataOnDevice() {
        PromptDialog clearDataDialog = new PromptDialog();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                new DeleteAllSignalsTask(getSettingsControllerInstance()).execute();
            }
        };

        clearDataDialog.activity((Activity) activity).createDialog(R.string.clear_data_question, runnable).show();
    }

    public void showProgressDialog(String title, String message) {
        activity.setProgressDialog(ProgressDialog.show((Activity) activity, title, message));
    }

    private boolean isProgressDialogShowing() {
        return (activity.getProgressDialog() != null && activity.getProgressDialog().isShowing());
    }


    public void dismissProgressDialogWithDelay(final int delayInMilSeconds, final String toastMessage) {
        if (isProgressDialogShowing()) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.getProgressDialog().dismiss();
                    if (!TextUtils.isEmpty(toastMessage)) {
                        Toast.makeText(GenericApplication.getContext(), toastMessage, Toast.LENGTH_LONG).show();
                    }
                }
            }, delayInMilSeconds);
        }
    }

    public void setActivity(ActivitySettingsContract activity) {
        this.activity = activity;
    }

    public int getSavedSignalsCount() {
        DatabaseHandler databaseHandler = new DatabaseHandler(GenericApplication.getContext());
        return databaseHandler.getSignalTableController().getCount();
    }

    private SettingsController getSettingsControllerInstance() {
        return this;
    }

}
