package com.pwittchen.eegreader.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import com.pwittchen.eegreader.R;

public class PromptDialog {
    private Activity activity;

    public PromptDialog activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public Dialog createDialog(int questionResourceId, final Runnable runnable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(questionResourceId)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        runnable.run();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

}
