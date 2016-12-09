package com.reazha.svn_test.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.reazha.svn_test.R;
import com.reazha.svn_test.bean.DeviceBean;

import java.util.List;

/**
 * @author ReaZhao
 * @date 2016/12/8
 * @FileName DeviceListAdapter
 * @E-mail 377742053qq.com
 * @desc 类的说明介绍
 */

public class DeviceListAdapter extends ArrayAdapter<DeviceBean> {

    public DeviceListAdapter(Context context, int resource, List<DeviceBean> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        // 如果View是空，则实例化viewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.binding_device_list_item, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        DeviceBean bean = getItem(position);
        TextView deviceAddress = vh.getView(R.id.device_address);
        TextView deviceName = vh.getView(R.id.device_name);
        // 设置设备名称和地址
        try {
            if (null != bean) {
                deviceAddress.setText(bean.getAddress());
                deviceName.setText(bean.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
