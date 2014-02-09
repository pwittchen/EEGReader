package com.pwittchen.eegreader.database.model;

public class Signal {
    public int _id;
    public int _type;
    public int _level;
    public long _miliseconds;

    public  Signal() {
    }

    public Signal(int _id, int _type, int _level, long _miliseconds) {
        this._id = _id;
        this._type = _type;
        this._level = _level;
        this._miliseconds = _miliseconds;
    }

    public Signal(int _type, int _level) {
        this._type = _type;
        this._level = _level;
    }

}
