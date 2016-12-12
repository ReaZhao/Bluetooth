package com.reazha.svn_test.contorller;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.reazha.svn_test.R;
import com.reazha.svn_test.activity.OperateBluetoothActivity;
import com.reazha.svn_test.bean.RinseIntervalBean;
import com.reazha.svn_test.bean.TimingTimeBean;
import com.reazha.svn_test.util.ConstantUtils;
import com.reazha.svn_test.util.GsonUtils;
import com.reazha.svn_test.util.LogUtils;
import com.reazha.svn_test.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author ReaZhao
 * @date 2016/12/8
 * @FileName OperateBluetoothContorller
 * @E-mail 377742053qq.com
 * @desc OperateBluetoothActivity
 */

public class OperateBluetoothContorller {
    private OperateBluetoothActivity mActivity;
    // 总包数
    private int pack_total_number = 0;
    // 发送命令 返回结果 每个包最多发送20长度的字节，result 接收全部包的结果拼接
    private String result = "";
    private boolean isonResume = true;
    // 发送命令返回的结果
    private String is_send = "";
    // 用于重发 命令失败后的重发
    private Button btn_send;
    // 把发送的命令保存数据，一个最多20字节
    private String[] pack;
    // 第几个包，小于10 前面加0 比如 01
    private String pack_number;
    // 用于发送 发送哪个按钮的内容
    private int index = 0;
    private BluetoothDevice bluetoothDevice;
    private BluetoothGatt bluetoothGatt;
    @SuppressWarnings("unused")
    private BluetoothGattCharacteristic gattCharacteristic_char1 = null;
    private BluetoothGattCharacteristic gattCharacteristic_char6 = null;
    @SuppressWarnings("unused")
    private BluetoothGattCharacteristic gattCharacteristic_heartrate = null;
    @SuppressWarnings("unused")
    private BluetoothGattCharacteristic gattCharacteristic_keydata = null;
    @SuppressWarnings("unused")
    private BluetoothGattCharacteristic gattCharacteristic_temperature = null;

    public OperateBluetoothContorller(OperateBluetoothActivity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 点击事件
     *
     * @return
     */
    public OnClickListener getOnClick() {
        return new OnClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                String msg = "";
                btn_send = null;
                is_send = "";
                result = "";
                pack = null;
                pack_number = null;
                switch (v.getId()) {
                    // 查询
                    case R.id.btn_select:
                        btn_send = mActivity.btn_server;
                        msg = "{\"Cmd\":\"GetConfInfo\"}";
                        break;
                    // 设备id设置
                    case R.id.btn_device:
                        btn_send = mActivity.btn_device;
                        index = ConstantUtils.DEVICE_ID;
                        // 隐藏其他按钮
                        mActivity.btn_flow.setVisibility(View.GONE);
                        mActivity.btn_server.setVisibility(View.GONE);
                        mActivity.btn_timing.setVisibility(View.GONE);
                        mActivity.btn_interval.setVisibility(View.GONE);
                        mActivity.btn_send.setVisibility(View.VISIBLE);
                        // 隐藏其他输入框
                        mActivity.layout_device.setVisibility(View.VISIBLE);
                        mActivity.layout_flow.setVisibility(View.GONE);
                        mActivity.layout_server.setVisibility(View.GONE);
                        mActivity.layout_timing.setVisibility(View.GONE);
                        mActivity.layout_interval.setVisibility(View.GONE);

                        break;
                    // 流量配置
                    case R.id.btn_flow:
                        btn_send = mActivity.btn_flow;
                        index = ConstantUtils.FLOW;
                        // 隐藏其他按钮
                        mActivity.btn_device.setVisibility(View.GONE);
                        mActivity.btn_server.setVisibility(View.GONE);
                        mActivity.btn_timing.setVisibility(View.GONE);
                        mActivity.btn_interval.setVisibility(View.GONE);
                        mActivity.btn_send.setVisibility(View.VISIBLE);
                        // 隐藏其他输入框
                        mActivity.layout_device.setVisibility(View.GONE);
                        mActivity.layout_flow.setVisibility(View.VISIBLE);
                        mActivity.layout_server.setVisibility(View.GONE);
                        mActivity.layout_timing.setVisibility(View.GONE);
                        mActivity.layout_interval.setVisibility(View.GONE);

                        break;
                    // 服务器配置
                    case R.id.btn_server:
                        btn_send = mActivity.btn_server;
                        index = ConstantUtils.SERVER;
                        // 隐藏其他按钮
                        mActivity.btn_device.setVisibility(View.GONE);
                        mActivity.btn_flow.setVisibility(View.GONE);
                        mActivity.btn_timing.setVisibility(View.GONE);
                        mActivity.btn_interval.setVisibility(View.GONE);
                        mActivity.btn_send.setVisibility(View.VISIBLE);
                        // 隐藏其他输入框
                        mActivity.layout_device.setVisibility(View.GONE);
                        mActivity.layout_flow.setVisibility(View.GONE);
                        mActivity.layout_server.setVisibility(View.VISIBLE);
                        mActivity.layout_timing.setVisibility(View.GONE);
                        mActivity.layout_interval.setVisibility(View.GONE);

                        break;
                    // 定时冲洗时间设置
                    case R.id.btn_timing:
                        btn_send = mActivity.btn_timing;
                        index = ConstantUtils.TIMING_RINSE;
                        // 隐藏其他按钮
                        mActivity.btn_device.setVisibility(View.GONE);
                        mActivity.btn_flow.setVisibility(View.GONE);
                        mActivity.btn_server.setVisibility(View.GONE);
                        mActivity.btn_interval.setVisibility(View.GONE);
                        mActivity.btn_send.setVisibility(View.VISIBLE);
                        // 隐藏其他输入框
                        mActivity.layout_device.setVisibility(View.GONE);
                        mActivity.layout_flow.setVisibility(View.GONE);
                        mActivity.layout_server.setVisibility(View.GONE);
                        mActivity.layout_timing.setVisibility(View.VISIBLE);
                        mActivity.layout_interval.setVisibility(View.GONE);

                        break;
                    // 冲洗时间间隔设置
                    case R.id.btn_interval:
                        btn_send = mActivity.btn_interval;
                        index = ConstantUtils.INTERVAL_RINSE;
                        // 隐藏其他按钮
                        mActivity.btn_device.setVisibility(View.GONE);
                        mActivity.btn_flow.setVisibility(View.GONE);
                        mActivity.btn_server.setVisibility(View.GONE);
                        mActivity.btn_timing.setVisibility(View.GONE);
                        mActivity.btn_send.setVisibility(View.VISIBLE);
                        // 隐藏其他输入框
                        mActivity.layout_device.setVisibility(View.GONE);
                        mActivity.layout_flow.setVisibility(View.GONE);
                        mActivity.layout_server.setVisibility(View.GONE);
                        mActivity.layout_timing.setVisibility(View.GONE);
                        mActivity.layout_interval.setVisibility(View.VISIBLE);

                        break;
                    // 返回
                    case R.id.btn_back:
                        // 按钮全部显示
                        mActivity.btn_device.setVisibility(View.VISIBLE);
                        mActivity.btn_flow.setVisibility(View.VISIBLE);
                        mActivity.btn_server.setVisibility(View.VISIBLE);
                        mActivity.btn_timing.setVisibility(View.VISIBLE);
                        mActivity.btn_interval.setVisibility(View.VISIBLE);
                        mActivity.btn_send.setVisibility(View.GONE);
                        // 清空输入框
                        mActivity.edit_device_id.setText("");
                        mActivity.edit_flow.setText("");
                        mActivity.edit_domain.setText("");
                        mActivity.edit_path.setText("");
                        mActivity.edit_timing.setText("");
                        mActivity.edit_timing_rinse.setText("");
                        mActivity.edit_interval.setText("");
                        mActivity.edit_interval_rinse.setText("");
                        // 隐藏其他输入框
                        mActivity.layout_device.setVisibility(View.GONE);
                        mActivity.layout_flow.setVisibility(View.GONE);
                        mActivity.layout_server.setVisibility(View.GONE);
                        mActivity.layout_timing.setVisibility(View.GONE);
                        mActivity.layout_interval.setVisibility(View.GONE);

                        break;
                    case R.id.btn_send:
                        switch (index) {
                            case ConstantUtils.DEVICE_ID:
                                if (mActivity.edit_device_id.getText().toString()
                                        .length() <= 0) {
                                    Toast.makeText(
                                            mActivity,
                                            mActivity
                                                    .getString(R.string.hint_device_id),
                                            Toast.LENGTH_LONG).show();
                                    return;
                                }
                                msg = "{\"Cmd\":\"DeviceConf\",\"DeviceId\":\""
                                        + mActivity.edit_device_id.getText().toString()
                                        + "\"}";
                                break;
                            case ConstantUtils.FLOW:
                                if (mActivity.edit_flow.getText().toString().length() <= 0) {
                                    Toast.makeText(mActivity,
                                            mActivity.getString(R.string.hint_flow),
                                            Toast.LENGTH_LONG).show();
                                    return;
                                }
                                try {
                                    int flow = Integer.valueOf(mActivity.edit_flow
                                            .getText().toString());
                                    msg = "{\"Cmd\":\"FlowConf\",\"FlowH\":\""
                                            + (flow * 1100 / 10000) + "\",\"FlowL\":\""
                                            + (flow * 1100 % 10000) + "\"}";
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case ConstantUtils.SERVER:
                                if (mActivity.edit_domain.getText().toString().length() <= 0) {
                                    Toast.makeText(mActivity,
                                            mActivity.getString(R.string.hint_domain),
                                            Toast.LENGTH_LONG).show();
                                    return;
                                }
                                if (mActivity.edit_path.getText().toString().length() <= 0) {
                                    Toast.makeText(mActivity,
                                            mActivity.getString(R.string.hint_path),
                                            Toast.LENGTH_LONG).show();
                                    return;
                                }
                                msg = "{\"Cmd\":\"Config\",\"Domain\":\""
                                        + mActivity.edit_domain.getText().toString()
                                        + "\",\"Path\":\""
                                        + mActivity.edit_path.getText().toString()
                                        + "\"}";
                                break;
                            case ConstantUtils.TIMING_RINSE:
                                if (mActivity.edit_timing.getText().toString().length() <= 0) {
                                    Toast.makeText(mActivity,
                                            mActivity.getString(R.string.hint_timing),
                                            Toast.LENGTH_LONG).show();
                                    return;
                                }
                                if (mActivity.edit_timing_rinse.getText().toString()
                                        .length() <= 0) {
                                    Toast.makeText(mActivity,
                                            mActivity.getString(R.string.hint_rinse),
                                            Toast.LENGTH_LONG).show();
                                    return;
                                }
                                msg = "{\"Cmd\":\"TimingRinse\",\"TimingTime\":\""
                                        + mActivity.edit_timing.getText().toString()
                                        + "\",\"RinseTime\":\""
                                        + mActivity.edit_timing_rinse.getText()
                                        .toString() + "\"}";
                                break;
                            case ConstantUtils.INTERVAL_RINSE:
                                if (mActivity.edit_interval.getText().toString()
                                        .length() <= 0) {
                                    Toast.makeText(
                                            mActivity,
                                            mActivity.getString(R.string.hint_interval),
                                            Toast.LENGTH_LONG).show();
                                    return;
                                }
                                if (mActivity.edit_interval_rinse.getText().toString()
                                        .length() <= 0) {
                                    Toast.makeText(mActivity,
                                            mActivity.getString(R.string.hint_rinse),
                                            Toast.LENGTH_LONG).show();
                                    return;
                                }
                                msg = "{\"Cmd\":\"Rinse\",\"RinseInterval\":\""
                                        + mActivity.edit_interval.getText().toString()
                                        + "\",\"RinseTime\":\""
                                        + mActivity.edit_interval_rinse.getText()
                                        .toString() + "\"}";
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                if (StringUtils.isEmpty(msg)) {
                    return;
                }
                pack_number = StringUtils.getPackageNumber(msg);
                pack = StringUtils.getPackage(msg);
                if (bluetoothGatt != null && gattCharacteristic_char6 != null) {
                    if (msg.length() % ConstantUtils.package_length == 0) {
                        gattCharacteristic_char6.setValue(ConstantUtils.head1
                                + pack_number);
                        bluetoothGatt
                                .writeCharacteristic(gattCharacteristic_char6);
                        System.out.println(msg.length() + ConstantUtils.head1
                                + pack_number);
                    } else {
                        gattCharacteristic_char6.setValue(ConstantUtils.head1
                                + pack_number);
                        bluetoothGatt
                                .writeCharacteristic(gattCharacteristic_char6);
                        System.out.println(msg.length() + ConstantUtils.head1
                                + pack_number);
                    }
                }
            }
        };
    }

    /**
     * 烧水与电池阀操作
     *
     * @return
     */
    public OnCheckedChangeListener getOnCheckedChange() {
        return new OnCheckedChangeListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @SuppressLint("NewApi")
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                String msg = "";
                pack_number = null;
                pack = null;
                switch (buttonView.getId()) {
                    case R.id.battery_value:
                        if (isChecked) {
                            // 电池阀打开
                            msg = "{\"Cmd\":\"DeviceTest\",\"Throd\":\""
                                    + ConstantUtils.BATTERY_ON + "\"}";
                        } else {
                            // 电池阀关闭
                            msg = "{\"Cmd\":\"DeviceTest\",\"Throd\":\""
                                    + ConstantUtils.BATTERY_OFF + "\"}";
                        }
                        break;

                    default:
                        break;
                }
                pack_number = StringUtils.getPackageNumber(msg);
                pack = StringUtils.getPackage(msg);
                if (bluetoothGatt != null && gattCharacteristic_char6 != null) {
                    if (msg.length() % ConstantUtils.package_length == 0) {
                        gattCharacteristic_char6.setValue(ConstantUtils.head1
                                + pack_number);
                        bluetoothGatt
                                .writeCharacteristic(gattCharacteristic_char6);
                         LogUtils.d(msg.length() + ConstantUtils.head1
                                + pack_number);
                    } else {
                        gattCharacteristic_char6.setValue(ConstantUtils.head1
                                + pack_number);
                        bluetoothGatt
                                .writeCharacteristic(gattCharacteristic_char6);
                         LogUtils.d(msg.length() + ConstantUtils.head1
                                + pack_number);
                    }
                }
            }
        };
    }

    // 界面显示
    public void onResume() {
        if (StringUtils.isEmpty(mActivity.name)) {
            ((TextView) mActivity.findViewById(R.id.buletooth_name))
                    .setText(mActivity.name);
        }
        if (!StringUtils.isEmpty(mActivity.address)) {
            ((TextView) mActivity.findViewById(R.id.buletooth_address))
                    .setText(mActivity.address);
            connect(mActivity.address);
            Toast.makeText(mActivity, "正在连接设备并获取服务中", Toast.LENGTH_SHORT)
                    .show();
        } else {
            mActivity.finish();
        }

    }

    /**
     * @param address
     */
    @SuppressLint("NewApi")
    private void connect(String address) {
        bluetoothDevice = MainContorller.bluetoothAdapter
                .getRemoteDevice(address);
        bluetoothGatt = bluetoothDevice.connectGatt(mActivity, false,
                bluetoothGattCallback);
    }

    @SuppressLint({"InlinedApi", "NewApi"})
    public void setCharacteristicNotification(
            BluetoothGattCharacteristic characteristic, boolean enabled) {
        // 仅仅有这一句是不够的
        // bluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        // 需要为指定特征的特定的描述符设置启用才行
         LogUtils.d("write descriptor");

        bluetoothGatt.setCharacteristicNotification(characteristic, true);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID
                .fromString("00002902-0000-1000-8000-00805f9b34fb"));
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        bluetoothGatt.writeDescriptor(descriptor);

    }

    @SuppressLint("NewApi")
    private BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        /**
         * 返回链接状态
         *
         * @param gatt
         * @param status
         *            链接或者断开连接是否成功 {@link BluetoothGatt#GATT_SUCCESS}
         * @param newState
         *            返回一个新的状态{@link BluetoothProfile#STATE_DISCONNECTED} or
         *            {@link BluetoothProfile#STATE_CONNECTED}
         */
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                            int newState) {
            super.onConnectionStateChange(gatt, status, newState);
             LogUtils.d("onConnectionStateChange");

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                // 连接成功 因为是异步调用的 所以刷新UI的操作要放在主线程中，当然也可以使用hanlder Eventbus等 随便
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mActivity, "连接成功", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                // 链接成功
                gatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                // 断开连接
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mActivity, "断开连接", Toast.LENGTH_SHORT)
                                .show();
                        mActivity.finish();
                    }
                });
            }
        }

        /**
         * 获取到链接设备的GATT服务时的回调
         *
         * @param gatt
         * @param status
         *            成功返回{@link BluetoothGatt#GATT_SUCCESS}
         */
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            /*
             * 一个GATT服务表现为一个 BluetoothGattService 对象，我们需要通过适当的UUID从
			 * BluetoothGatt 实例中获得； 一个GATT特征表示为一个 BluetoothGattCharacteristic
			 * 对象，我们可以通过适当的UUID从 BluetoothGattService 中得到；
			 * 相当于一个数据类型，它包括一个value和0~n个value的描述（BluetoothGattDescriptor）
			 * 一个GATT描述符表现为一个 BluetoothGattDescriptor
			 * 对象，我们可以通过适当的UUID从BluetoothGattCharacteristic 对象中获得：
			 * 描述符，对Characteristic的描述，包括范围、计量单位等
			 */
             LogUtils.d("onServicesDiscovered");
            if (BluetoothGatt.GATT_SUCCESS == status) {
                List<BluetoothGattService> services = gatt.getServices();
                for (BluetoothGattService bluetoothGattService : services) {
                    List<BluetoothGattCharacteristic> gattCharacteristics = bluetoothGattService
                            .getCharacteristics();
                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : gattCharacteristics) {
                        if (bluetoothGattCharacteristic.getUuid().toString()
                                .equals(ConstantUtils.UUID_CHAR6)) {
                            // 把char1 保存起来�?以方便后面读写数据时使用
                            gattCharacteristic_char6 = bluetoothGattCharacteristic;
                            setCharacteristicNotification(
                                    bluetoothGattCharacteristic, true);
                        }
                        if (bluetoothGattCharacteristic.getUuid().toString()
                                .equals(ConstantUtils.UUID_HERATRATE)) {
                            // 把heartrate 保存起来�?以方便后面读写数据时使用
                            gattCharacteristic_heartrate = bluetoothGattCharacteristic;
                            setCharacteristicNotification(
                                    bluetoothGattCharacteristic, true);
                        }
                        if (bluetoothGattCharacteristic.getUuid().toString()
                                .equals(ConstantUtils.UUID_KEY_DATA)) {
                            // 把heartrate 保存起来�?以方便后面读写数据时使用
                            gattCharacteristic_keydata = bluetoothGattCharacteristic;
                            setCharacteristicNotification(
                                    bluetoothGattCharacteristic, true);
                        }
                        if (bluetoothGattCharacteristic.getUuid().toString()
                                .equals(ConstantUtils.UUID_TEMPERATURE)) {
                            // 把heartrate 保存起来�?以方便后面读写数据时使用
                            gattCharacteristic_temperature = bluetoothGattCharacteristic;
                            setCharacteristicNotification(
                                    bluetoothGattCharacteristic, true);
                        }
                    }
                }
            }

        }

        /**
         * 读特征的时候的回调 回调报告一个特征读取操作的结果。
         *
         * @param gatt
         * @param characteristic
         *            从相关设备上面读取到的特征值
         * @param status
         */
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            // 读取到的数据存在characteristic当中，可以通过characteristic.getValue();函数取出。然后再进行解析操作。
             LogUtils.d("onCharacteristicRead");
            if (BluetoothGatt.GATT_SUCCESS == status) {
                final byte[] data = characteristic.getValue();
                // 和通知一样也是通过字节码的形式传递数据 这里省略不写
                 LogUtils.d("onCharacteristicRead" + new String(data));
            }

        }

        /**
         * 指定特征写入操作的回调结果
         *
         * @param gatt
         * @param characteristic
         * @param status
         */
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt,
                                          BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            // write回调失败 status=128， read回调失败status=128.
            // status=0,回调成功；status=9，数组超长**
            Log.d("onCharacteristicWrite", "status=" + status);
            if (BluetoothGatt.GATT_SUCCESS == status) {
                Log.d("Activity", "写入成功");
            }

        }

        /**
         * 设备发出通知时会调用到该接口 由于远程特性通知引发的回调。
         *
         * @param gatt
         * @param characteristic
         */
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
             LogUtils.d("onCharacteristicChanged");
            // 以字节码数组的形式接收到数据
            final byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
               LogUtils.d("result = " + new String(data));
                is_send = new String(data);
                mActivity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        try {

                            if (is_send.equals("Error")) {
                                btn_send.performClick();
                                return;
                            } else if (is_send
                                    .equals(ConstantUtils.TimingRinse)) {
                                Toast.makeText(mActivity, "定时冲洗配置成功",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            } else if (is_send.equals(ConstantUtils.Config)) {
                                Toast.makeText(mActivity, "服务配置成功",
                                        Toast.LENGTH_SHORT).show();
                                // mActivity.text_server
                                // .setText(mActivity.edit_path.getText()
                                // .toString());
                                return;
                            } else if (is_send.equals(ConstantUtils.DeviceConf)) {
                                Toast.makeText(mActivity, "设备ID配置成功",
                                        Toast.LENGTH_SHORT).show();
                                // mActivity.text_device
                                // .setText(mActivity.edit_device_id
                                // .getText().toString());
                                return;
                            } else if (is_send
                                    .equals(ConstantUtils.DeviceTestOFF)) {
                                Toast.makeText(mActivity, "电池阀关闭成功",
                                        Toast.LENGTH_SHORT).show();
                                mActivity.text_battery.setText("关闭");
                                return;
                            } else if (is_send
                                    .equals(ConstantUtils.DeviceTestON)) {
                                Toast.makeText(mActivity, "电池阀打开成功",
                                        Toast.LENGTH_SHORT).show();
                                mActivity.text_battery.setText("打开");
                                return;
                            } else if (is_send.equals(ConstantUtils.FlowConf)) {
                                Toast.makeText(mActivity, "流量设置配置成功",
                                        Toast.LENGTH_SHORT).show();
                                // mActivity.text_flow.setText(mActivity.edit_flow
                                // .getText().toString());
                                return;
                            } else if (is_send.equals(ConstantUtils.Rinse)) {
                                Toast.makeText(mActivity, "冲洗间隔配置成功",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            } else if (is_send.contains(ConstantUtils.head1)) {
                                try {
                                    pack_total_number = Integer.valueOf(is_send
                                            .substring(
                                                    ConstantUtils.TOTAL_INDEX,
                                                    is_send.length()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return;
                            } else if (is_send.contains(ConstantUtils.head)) {
                                try {
                                    result += is_send;
                                    if (result.contains("{")
                                            && result.contains("}")) {
                                        for (int i = 1; i <= pack_total_number; i++) {
                                            result = result.replaceAll(
                                                    ConstantUtils.head
                                                            + StringUtils
                                                            .getPackNumber(i),
                                                    "");
                                        }
                                         LogUtils.d("result=" + result);
                                        if (result.contains("TimingTime")) {
                                            TimingTimeBean bean=GsonUtils.getPerson(
                                                    result, TimingTimeBean.class);
                                            // 设置值，显示
                                            if (StringUtils.isEmpty(bean.getThrod())) {
                                                bean.setThrod("0");
                                            }
                                            if (bean.getThrod().equals("1")) {
                                                mActivity.text_battery
                                                        .setText("打开");
                                            } else {
                                                mActivity.text_battery
                                                        .setText("关闭");
                                            }

                                            if (!StringUtils.isEmpty(bean
                                                    .getDeviceId())) {
                                                mActivity.text_device.setText(bean
                                                        .getDeviceId());
                                            }
                                            try {
                                                if (StringUtils.isEmpty(bean
                                                        .getFlowH())) {
                                                    bean.setFlowH("0");
                                                }
                                                int flow = (Integer.valueOf(bean
                                                        .getFlowH()) * 10000)
                                                        + Integer.valueOf(bean
                                                        .getFlowL());
                                                 LogUtils.d("flow=" + flow);
                                                mActivity.text_flow.setText(String
                                                        .valueOf((flow / 1100)));

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            if (bean.getLeakWater().equals("1")) {
                                                mActivity.text_leak.setText("漏水");
                                            } else {
                                                mActivity.text_leak.setText("未漏水");
                                            }
                                            if (!StringUtils.isEmpty(bean.getFlowH())) {
                                                mActivity.text_server.setText(bean
                                                        .getPath());
                                            }
                                            mActivity.text_tds.setText(bean
                                                    .getTDS());
                                        } else {
                                            RinseIntervalBean bean = GsonUtils.getPerson(
                                                    result, RinseIntervalBean.class);
                                            // 设置值，显示
                                            if (StringUtils.isEmpty(bean.getThrod())) {
                                                bean.setThrod("0");
                                            }
                                            if (bean.getThrod().equals("1")) {
                                                mActivity.text_battery
                                                        .setText("打开");
                                            } else {
                                                mActivity.text_battery
                                                        .setText("关闭");
                                            }

                                            if (!StringUtils.isEmpty(bean
                                                    .getDeviceId())) {
                                                mActivity.text_device.setText(bean
                                                        .getDeviceId());
                                            }
                                            try {
                                                if (StringUtils.isEmpty(bean
                                                        .getFlowH())) {
                                                    bean.setFlowH("0");
                                                }
                                                int flow = (Integer.valueOf(bean
                                                        .getFlowH()) * 10000)
                                                        + Integer.valueOf(bean
                                                        .getFlowL());
                                                 LogUtils.d("flow=" + flow);
                                                mActivity.text_flow.setText(String
                                                        .valueOf((flow / 1100)));

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            if (bean.getLeakWater().equals("1")) {
                                                mActivity.text_leak.setText("漏水");
                                            } else {
                                                mActivity.text_leak.setText("未漏水");
                                            }
                                            if (!StringUtils.isEmpty(bean.getFlowH())) {
                                                mActivity.text_server.setText(bean
                                                        .getPath());
                                            }
                                            mActivity.text_tds.setText(bean
                                                    .getTDS());
                                        }

                                    }
                                } catch (Exception e) {
                                    btn_send.performClick();
                                    e.printStackTrace();
                                }
                                return;
                            } else if (is_send.equals("OK")) {

                                return;
                            }
                            int pack_index = Integer.valueOf(is_send);
                            if (bluetoothGatt != null
                                    && gattCharacteristic_char6 != null) {
                                try {
                                    gattCharacteristic_char6.setValue(ConstantUtils.head
                                            + StringUtils
                                            .getPackNumber(pack_index)
                                            + pack[(pack_index - 1)]);
                                    bluetoothGatt
                                            .writeCharacteristic(gattCharacteristic_char6);
                                     LogUtils.d("send_context="
                                            + ConstantUtils.head
                                            + StringUtils
                                            .getPackNumber(pack_index)
                                            + pack[(pack_index - 1)]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

        }

        /**
         * 指定描述符的读操作的回调
         *
         * @param gatt
         * @param descriptor
         * @param status
         */
        @Override
        public void onDescriptorRead(BluetoothGatt gatt,
                                     BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
             LogUtils.d("onDescriptorRead");
        }

        /**
         * 指定描述符的写操作
         *
         * @param gatt
         * @param descriptor
         * @param status
         */
        @Override
        public void onDescriptorWrite(BluetoothGatt gatt,
                                      BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
             LogUtils.d("onDescriptorWrite");
        }

        /**
         * 当一个写入事物完成时的回调
         *
         * @param gatt
         * @param status
         */
        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
             LogUtils.d("onReliableWriteCompleted");
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
             LogUtils.d("onReadRemoteRssi");
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
             LogUtils.d("onMtuChanged");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                 LogUtils.d("onMtuChanged=" + mtu);
            }
        }

    };

    // 界面隐藏（暂停）
    @SuppressLint("NewApi")
    public void onPause() {
        try {
            bluetoothGatt.close();
            bluetoothGatt = null;
            mActivity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回键监听
     */
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("是否退出当前界面");
        builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mActivity.finish();
            }
        });
        builder.setNeutralButton("取消", new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
