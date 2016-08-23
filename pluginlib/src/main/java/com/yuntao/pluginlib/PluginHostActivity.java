package com.yuntao.pluginlib;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class PluginHostActivity extends BaseActivity {

    private String localPath;
    private boolean isInstall;
    protected LinearLayout viewRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localPath = getIntent().getStringExtra(Constant.INTENT_KEY_APK_PATH);
        String fragment = getIntent().getStringExtra(Constant.INTENT_KEY_FRAGMENT);
        setContentView(getContentView());
        isInstall = installRunEnv(localPath);
        installPluginFragment(fragment);
    }

    protected View getContentView() {
        viewRoot = new LinearLayout(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            viewRoot.setFitsSystemWindows(true);
        }
        viewRoot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        viewRoot.setId(android.R.id.primary);
        return viewRoot;
    }

    // 获取插件的运行环境
    protected PluginEnv getPluginRunEnv() {
        if (null == localPath) {
            return null;
        }
        PluginEnv env = PluginInstallUtils.mPackagesHolder.get(localPath);
        return env;
    }

    /**
     * 装载插件的运行环境,这个函数需要在Activity中运行，不能移动到Application中去
     *
     * @param localPath
     * @return
     */
    protected boolean installRunEnv(final String localPath) {
        PluginInstallUtils.getInstance(this).installRunEnv(localPath);
        return true;
    }

    @Override
    public AssetManager getAssets() {
        PluginEnv env = getPluginRunEnv();
        if (isInstall && env != null) {
            return env.pluginAsset;
        } else {
            return super.getAssets();
        }
    }

    @Override
    public Resources getResources() {
        PluginEnv env = getPluginRunEnv();
        if (isInstall && env != null) {
            return env.pluginRes;
        } else {
            return super.getResources();
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        PluginEnv env = getPluginRunEnv();
        if (isInstall && env != null) {
            return env.pluginClassLoader;
        } else {
            return super.getClassLoader();
        }
    }

    @Override
    public Resources.Theme getTheme() {
        PluginEnv env = getPluginRunEnv();
        if (isInstall && env != null) {
            return env.pluginTheme;
        } else {
            return super.getTheme();
        }
    }

    protected void installPluginFragment(String fragClass) {
        try {
            if (isFinishing()) {
                return;
            }
            ClassLoader classLoader = getClassLoader();
            Fragment fg = (Fragment) classLoader.loadClass(fragClass).newInstance();
            Bundle bundle = getIntent().getExtras();
            fg.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.primary, fg).commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}