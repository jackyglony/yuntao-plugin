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

    /**
     * 打开插件fragment
     *
     * @param fragment
     * @param apkPath
     */
    public void startFragment(String fragment, String apkPath) {
        startFragment(fragment, apkPath, null);
    }

    /**
     * 打开插件fragment,通过Intent把插件信息传递给宿主PluginHostActivity,让其去加载插件环境,并打开fragment
     *
     * @param fragment fragment类名
     * @param apkPath apk路径
     * @param bundle 附近信息
     */
    public void startFragment(String fragment, String apkPath, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_HOST_ACTIVITY);
        intent.putExtra(Constant.INTENT_KEY_APK_PATH, apkPath);
        intent.putExtra(Constant.INTENT_KEY_FRAGMENT, fragment);
        intent.putExtra(Constant.INTENT_KEY_BUNDLE, bundle);
        startActivity(intent);
    }
}
