package com.yuntao.pluginlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by pengyuntao on 16/8/19.
 */
public class BaseFragment extends Fragment {
    public BaseFragment() {

    }

    public void startFragment(String fragment, String apkPath) {
        startFragment(fragment, apkPath, null);
    }

    public void startFragment(String fragment, String apkPath, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_HOST_ACTIVITY);
        intent.putExtra(Constant.INTENT_KEY_APK_PATH, apkPath);
        intent.putExtra(Constant.INTENT_KEY_FRAGMENT, fragment);
        intent.putExtra(Constant.INTENT_KEY_BUNDLE, bundle);
        startActivity(intent);
    }
}
