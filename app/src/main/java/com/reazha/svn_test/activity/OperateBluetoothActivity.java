package com.reazha.svn_test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reazha.svn_test.R;
import com.reazha.svn_test.contorller.OperateBluetoothContorller;

public class OperateBluetoothActivity extends Activity {
    private OperateBluetoothContorller mContorller;
    // 烧水与电池阀开关
    public CheckBox battery_value;
    // 查询，设备id、流量、服务器、定时、时间间隔的配置  发送
    public Button btn_select, btn_device, btn_flow, btn_server, btn_timing,
            btn_interval, btn_back,btn_send;
    // 显示  电池阀状态、 设备id、当前流量、漏水状态、当前流量、服务器路径、tds值、
    public TextView text_battery,text_device, text_leak, text_flow, text_server, text_tds;
    // 设备id、流量、服务器、定时、时间间隔的配置输入框
    public EditText edit_device_id, edit_flow, edit_domain, edit_path,
            edit_timing, edit_timing_rinse, edit_interval, edit_interval_rinse;
    // 设备id、流量、服务器、定时、时间间隔的配置输入框的隐藏显示
    public LinearLayout layout_device, layout_flow, layout_server,
            layout_timing, layout_interval;
    // ble蓝牙的mac地址和蓝牙名称
    public String address, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate_bluetooth);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        // 加载控件
        mContorller = new OperateBluetoothContorller(this);
        address=getIntent().getStringExtra("address");
        name=getIntent().getStringExtra("name");
        battery_value = (CheckBox) findViewById(R.id.battery_value);
        btn_select = (Button) findViewById(R.id.btn_select);
        btn_device = (Button) findViewById(R.id.btn_device);
        btn_flow = (Button) findViewById(R.id.btn_flow);
        btn_server = (Button) findViewById(R.id.btn_server);
        btn_timing = (Button) findViewById(R.id.btn_timing);
        btn_interval = (Button) findViewById(R.id.btn_interval);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_send=(Button) findViewById(R.id.btn_send);
        text_battery=(TextView) findViewById(R.id.text_battery);
        text_device = (TextView) findViewById(R.id.text_device);
        text_flow = (TextView) findViewById(R.id.text_flow);
        text_leak = (TextView) findViewById(R.id.text_leak);
        text_server = (TextView) findViewById(R.id.text_server);
        text_tds = (TextView) findViewById(R.id.text_tds);
        edit_device_id = (EditText) findViewById(R.id.edit_device_id);
        edit_flow = (EditText) findViewById(R.id.edit_flow);
        edit_domain = (EditText) findViewById(R.id.edit_domain);
        edit_path = (EditText) findViewById(R.id.edit_path);
        edit_timing = (EditText) findViewById(R.id.edit_timing);
        edit_timing_rinse = (EditText) findViewById(R.id.edit_timing_rinse);
        edit_interval = (EditText) findViewById(R.id.edit_interval);
        edit_interval_rinse = (EditText) findViewById(R.id.edit_interval_rinse);
        layout_device = (LinearLayout) findViewById(R.id.layout_device);
        layout_flow = (LinearLayout) findViewById(R.id.layout_flow);
        layout_server = (LinearLayout) findViewById(R.id.layout_server);
        layout_timing = (LinearLayout) findViewById(R.id.layout_timing);
        layout_interval = (LinearLayout) findViewById(R.id.layout_interval);
        // 添加事件
        battery_value.setOnCheckedChangeListener(mContorller.getOnCheckedChange());
        btn_select.setOnClickListener(mContorller.getOnClick());
        btn_device.setOnClickListener(mContorller.getOnClick());
        btn_flow.setOnClickListener(mContorller.getOnClick());
        btn_server.setOnClickListener(mContorller.getOnClick());
        btn_timing.setOnClickListener(mContorller.getOnClick());
        btn_interval.setOnClickListener(mContorller.getOnClick());
        btn_back.setOnClickListener(mContorller.getOnClick());
        btn_send.setOnClickListener(mContorller.getOnClick());
    }
    @Override
    protected void onResume() {
        super.onResume();
        mContorller.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mContorller.onPause();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mContorller.onBackPressed();
    }
}
