package com.yuntao.pluginlib;

/**
 * Created by pengyuntao on 2017/9/8.
 */

public class NativeCore {
    static {
        System.loadLibrary("nativeCore");
    }

    public native static String stringFromJNI();
}
