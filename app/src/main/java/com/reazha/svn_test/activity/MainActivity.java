package com.reazha.svn_test.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.ListView;

import com.reazha.svn_test.R;
import com.reazha.svn_test.contorller.MainContorller;

public class MainActivity extends Activity {

    public ListView listDevices;
    private MainContorller mContorller;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intView();
        mContorller.initBluetooth();

    }

    /**
     * 初始化控件
     */
    private void intView() {
        mContorller =new MainContorller(this);
        listDevices = (ListView) findViewById(R.id.list_devices);

        //添加点击事件
        listDevices.setOnItemClickListener(mContorller.getItemClick());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onResume() {
        super.onResume();
        mContorller.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onPause() {
        super.onPause();
        mContorller.onPause();
    }


}
