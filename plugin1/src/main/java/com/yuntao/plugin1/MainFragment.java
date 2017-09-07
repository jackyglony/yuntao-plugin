package com.yuntao.plugin1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuntao.pluginlib.BaseFragment;
import com.yuntao.pluginlib.NativeCore;


public class MainFragment extends BaseFragment {

    private Button mBtnOpenPlugin2;
    private TextView mTextNative;

    public MainFragment() {
        // Required empty public constructor
    }

    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mBtnOpenPlugin2 = (Button) view.findViewById(R.id.btn_open_plugin2);
        mBtnOpenPlugin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlugin2();
            }
        });
        mTextNative = (TextView) view.findViewById(R.id.text_native);
        mTextNative.setText(stringFromJNI());
        Toast.makeText(getContext(), NativeCore.stringFromJNI(), Toast.LENGTH_LONG).show();
        return view;
    }

    public void openPlugin2() {
        startFragment("com.yuntao.plugin2.MainFragment",
                "/storage/emulated/0/yuntao-plugin/plugin2-debug.apk");
    }

}
