package com.pwittchen.eegreader.database.asynctask;

import android.os.AsyncTask;

import com.pwittchen.eegreader.R;
import com.pwittchen.eegreader.activity.controller.SettingsController;
import com.pwittchen.eegreader.database.DatabaseHandler;
import com.pwittchen.eegreader.generics.GenericApplication;
import com.pwittchen.eegreader.utils.StringUtils;

public class DeleteAllSignalsTask extends AsyncTask<Void, Void, Void> {

    private DatabaseHandler databaseHandler;
    private SettingsController settingsController;
    private int dismissProgressDialogDelayInMilSeconds = 2500;
    private String toastMessage;

    public DeleteAllSignalsTask() {
    }

    public DeleteAllSignalsTask(SettingsController settingsController) {
        this.databaseHandler = new DatabaseHandler(GenericApplication.getContext());
        this.settingsController = settingsController;
        this.toastMessage = StringUtils.getStringFromResources(R.string.data_cleared);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        settingsController.showProgressDialog(StringUtils.getStringFromResources(R.string.clearing_data), StringUtils.getStringFromResources(R.string.clearing_in_progress));
    }

    @Override
    protected Void doInBackground(Void... voids) {
        databaseHandler.getSignalTableController().deleteAll();
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        settingsController.dismissProgressDialogWithDelay(dismissProgressDialogDelayInMilSeconds, toastMessage);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        settingsController.dismissProgressDialogWithDelay(dismissProgressDialogDelayInMilSeconds, toastMessage);
        settingsController.resetSavedSignalsCountWithDelay(dismissProgressDialogDelayInMilSeconds);
    }
}
