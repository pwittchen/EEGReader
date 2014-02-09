package com.pwittchen.eegreader.menu.item;

import com.pwittchen.eegreader.activity.MainActivity;
import com.pwittchen.eegreader.menu.GenericMenuItem;
import com.pwittchen.eegreader.menu.MenuItemSettable;
import com.pwittchen.eegreader.menu.contract.MainActivityMenuContract;

public class MenuItemBluetooth extends GenericMenuItem implements Runnable, MenuItemSettable {
    @Override
    public void run() {
        super.run();
        MainActivityMenuContract mainActivityMenuContract = (MainActivity) activity;
        mainActivityMenuContract.connectOrDisconnectToDevice(item);
    }
}
