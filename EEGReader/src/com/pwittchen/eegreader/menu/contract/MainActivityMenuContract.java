package com.pwittchen.eegreader.menu.contract;

import com.actionbarsherlock.view.MenuItem;

public interface MainActivityMenuContract {
    void startSettingsActivity(MenuItem item);
    void connectOrDisconnectToDevice(MenuItem item);
}
