package com.yuntao.host;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuntao.pluginlib.AppUtils;
import com.yuntao.pluginlib.BaseActivity;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private PluginAdapter mPluginAdapter;
    private ArrayList<PluginItem> mPluginItems = new ArrayList<PluginItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        //Toast.makeText(getApplicationContext(), NativeCore.stringFromJNI(), Toast.LENGTH_LONG).show();
    }

    private void initView() {
        mPluginAdapter = new PluginAdapter();
        mListView = (ListView) findViewById(R.id.plugin_list);
    }

    private void initData() {
        String pluginFolder = Environment.getExternalStorageDirectory() + "/yuntao-plugin";
        File file = new File(pluginFolder);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file.canRead() || !file.canWrite()) {
            Toast.makeText(getApplicationContext(), "请授予sd卡读写权限", Toast.LENGTH_SHORT).show();
            return;
        }

        File[] plugins = file.listFiles();
        if (plugins != null) {
            for (File plugin : plugins) {
                if (plugin.getName().endsWith(".apk")) {
                    PluginItem item = new PluginItem();
                    item.pluginPath = plugin.getAbsolutePath();
                    item.rootFragment = getRootFragment(item.pluginPath);
                    item.appLogo = AppUtils.getAppIcon(this, item.pluginPath);
                    item.name = AppUtils.getAppLabel(this, item.pluginPath);
                    mPluginItems.add(item);
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "请把插件放到/sdcard/yuntao-plugin目录下", Toast.LENGTH_SHORT).show();
        }

        mListView.setAdapter(mPluginAdapter);
        mListView.setOnItemClickListener(this);
        mPluginAdapter.notifyDataSetChanged();
    }

    private String getRootFragment(String apkPath) {
        PackageInfo info = AppUtils.getPackageInfo(this, apkPath);
        ApplicationInfo appInfo = info.applicationInfo;
        String root = appInfo.metaData.getString("root_fragment");
        return root;
    }

    private class PluginAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public PluginAdapter() {
            mInflater = MainActivity.this.getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mPluginItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mPluginItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.plugin_item, parent, false);
                holder = new ViewHolder();
                holder.appPath = (TextView) convertView.findViewById(R.id.app_path);
                holder.appRoot = (TextView) convertView.findViewById(R.id.app_root);
                holder.appLogo = (ImageView) convertView.findViewById(R.id.icon_logo);
                holder.appName = (TextView) convertView.findViewById(R.id.app_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PluginItem item = mPluginItems.get(position);
            holder.appPath.setText("插件路径:" + item.pluginPath);
            holder.appRoot.setText("打开:" + item.rootFragment);
            holder.appName.setText(item.name);
            holder.appLogo.setImageDrawable(item.appLogo);
            return convertView;
        }

    }

    public static class PluginItem {
        public CharSequence name;
        public String rootFragment;
        public String pluginPath;
        public Drawable appLogo;
    }

    private static class ViewHolder {
        public TextView appName;
        public TextView appRoot;
        public TextView appPath;
        public ImageView appLogo;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PluginItem item = mPluginItems.get(position);
        startFragment(item.rootFragment, item.pluginPath, null);
    }
}
