package com.pwittchen.eegreader.menu;

import android.app.Activity;

import com.actionbarsherlock.view.MenuItem;

public abstract class GenericMenuItem implements Runnable, MenuItemSettable {
    protected MenuItem item;
    protected static Activity activity;

    public GenericMenuItem activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public GenericMenuItem item(MenuItem item) {
        this.item = item;
        return this;
    }

    @Override
    public void run() {
    }
}