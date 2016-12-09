package com.reazha.svn_test.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * @author ReaZhao
 * @date 2016/12/8
 * @FileName ViewHolder
 * @E-mail 377742053qq.com
 * @desc 优化ListView
 */

public class ViewHolder {
    private SparseArray<View> views = new SparseArray<>();
    private View view;

    public ViewHolder(View view) {
        this.view = view;
    }

    public <T extends View> T getView(int resId) {
        View v = views.get(resId);
        if (null != v) {
            v = view.findViewById(resId);
            views.put(resId, v);
        }
        return (T) v;
    }
}
