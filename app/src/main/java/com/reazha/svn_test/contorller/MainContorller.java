package com.reazha.svn_test.contorller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;

import com.reazha.svn_test.R;
import com.reazha.svn_test.activity.MainActivity;
import com.reazha.svn_test.activity.OperateBluetoothActivity;
import com.reazha.svn_test.adapter.DeviceListAdapter;
import com.reazha.svn_test.bean.DeviceBean;
import com.reazha.svn_test.util.LogUtils;
import com.reazha.svn_test.util.StringUtils;
import com.reazha.svn_test.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ReaZhao
 * @date 2016/12/8
 * @FileName MainContorller
 * @E-mail 377742053qq.com
 * @desc MainActivity的控制层
 */

public class MainContorller {

    public static BluetoothAdapter bluetoothAdapter;
    private MainActivity mActivity;
    private DeviceListAdapter listAdapter;
    private List<DeviceBean> devices = new ArrayList<>();
    private boolean isResume;

    public MainContorller(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    public AdapterView.OnItemClickListener getItemClick() {
        return new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeviceBean bean = devices.get(position);
                if (bean == null) {
                    return;
                }
                if (isResume) {
                    // 停止设备扫描
                    bluetoothAdapter.stopLeScan(mLeScanCallback);
                    isResume = false;
                }
                if (StringUtils.isEmpty(bean.getAddress())) {
                    return;
                }
//                Intent intent = new Intent(mActivity,
//                        HomeActivity.class);
                Intent intent = new Intent(mActivity,
                        OperateBluetoothActivity.class);
                intent.putExtra("address", bean.getAddress());
                intent.putExtra("name", bean.getName());
                mActivity.startActivity(intent);
            }
        };
    }

    /**
     * activity 显示
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void onResume() {
        isResume = true;
        // 开始扫描设备：
        if (isResume) {
            bluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            bluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    /**
     * activity 隐藏
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void onPause() {
        if (isResume) {
            bluetoothAdapter.stopLeScan(mLeScanCallback);
            isResume = false;
        }
        devices.clear();
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取BluetoothAdapter:
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void initBluetooth() {
        BluetoothManager bluetoothManager = (BluetoothManager) mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        //判断蓝牙是否打开 没有打开就强制打开，还可以跳转到蓝牙打开界面
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
        //判断手机是否支持BLE蓝牙（低功耗蓝牙）
        if (!mActivity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtils.showShortToast(mActivity, R.string.ble_not_supported);
            return;
        }
        //初始化显示蓝牙列表的适配器
        listAdapter = new DeviceListAdapter(mActivity, R.layout.binding_device_list_item, devices);
        mActivity.listDevices.setAdapter(listAdapter);
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            // 显示了一个列表，点击进入具体设备页面 BLEDeviceTestActivity 并且将设备的名称和地址传递过去
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DeviceBean bean = new DeviceBean(device.getAddress(),
                            device.getName());

                    devices.add(bean);
                    LogUtils.d(devices.toString());

                    devices = StringUtils.removeDuplicate(devices);
                    if (devices.size() > 0 && listAdapter != null) {
                        listAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    };
}
