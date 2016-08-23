package com.yuntao.plugin3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuntao.pluginlib.BaseFragment;

/**
 * Created by pengyuntao on 16/8/23.
 */
public class MainFragment extends BaseFragment {

    private CustomTextView mTitle;

    public MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        mTitle = (CustomTextView) view.findViewById(R.id.title);
        return view;
    }
}
