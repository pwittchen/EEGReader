package com.pwittchen.eegreader.database.asynctask;

import android.os.AsyncTask;

import com.pwittchen.eegreader.R;
import com.pwittchen.eegreader.database.DatabaseHandler;
import com.pwittchen.eegreader.generics.GenericApplication;
import com.pwittchen.eegreader.utils.ConfigUtils;
import com.pwittchen.eegreader.utils.StringUtils;

import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

public class ExportAllSignalsToCSVTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = makeLogTag(ExportAllSignalsToCSVTask.class);

    private String toastMessage;

    public ExportAllSignalsToCSVTask() {
        this.toastMessage = StringUtils.getStringFromResources(R.string.data_exported);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        DatabaseHandler databaseHandler = new DatabaseHandler(GenericApplication.getContext());
        return databaseHandler.exportTableToCSVFile(databaseHandler.getSignalTableController().getAllCursor(), ConfigUtils.EXPORT_CSV_FILE_NAME);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
    }
}
