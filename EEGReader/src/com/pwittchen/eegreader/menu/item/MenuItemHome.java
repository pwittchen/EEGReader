package com.pwittchen.eegreader.menu.item;

import com.pwittchen.eegreader.menu.GenericMenuItem;
import com.pwittchen.eegreader.menu.MenuItemSettable;

public class MenuItemHome extends GenericMenuItem implements Runnable, MenuItemSettable {
    @Override
    public void run() {
        super.run();
        activity.finish();
    }
}
