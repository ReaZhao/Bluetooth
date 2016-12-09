package com.reazha.svn_test.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ReaZhao
 * @date 2016/12/8
 * @FileName RinseIntervalBean
 * @E-mail 377742053qq.com
 * @desc 类的说明介绍
 */

public class RinseIntervalBean implements Parcelable {

    /**
     * Cmd : Config
     * Domain : 域名
     * DeviceId : 设备id
     * Path : 服务器路径
     * TDS : TDS
     * FlowH : 高脉冲（相当于流量万位以上，包含万位）
     * FlowL : 低脉冲（相当于流量万位一下）
     * LeakWater : 1/0  是否漏水
     * Throd : 1/0   电池阀是否打开
     * RinseInterval : 间隔冲洗
     * RinseTime : 冲洗时间
     */

    private String Cmd;
    private String Domain;
    private String DeviceId;
    private String Path;
    private String TDS;
    private String FlowH;
    private String FlowL;
    private String LeakWater;
    private String Throd;
    private String RinseInterval;
    private String RinseTime;

    public String getCmd() {
        return Cmd;
    }

    public void setCmd(String Cmd) {
        this.Cmd = Cmd;
    }

    public String getDomain() {
        return Domain;
    }

    public void setDomain(String Domain) {
        this.Domain = Domain;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String DeviceId) {
        this.DeviceId = DeviceId;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String Path) {
        this.Path = Path;
    }

    public String getTDS() {
        return TDS;
    }

    public void setTDS(String TDS) {
        this.TDS = TDS;
    }

    public String getFlowH() {
        return FlowH;
    }

    public void setFlowH(String FlowH) {
        this.FlowH = FlowH;
    }

    public String getFlowL() {
        return FlowL;
    }

    public void setFlowL(String FlowL) {
        this.FlowL = FlowL;
    }

    public String getLeakWater() {
        return LeakWater;
    }

    public void setLeakWater(String LeakWater) {
        this.LeakWater = LeakWater;
    }

    public String getThrod() {
        return Throd;
    }

    public void setThrod(String Throd) {
        this.Throd = Throd;
    }

    public String getRinseInterval() {
        return RinseInterval;
    }

    public void setRinseInterval(String RinseInterval) {
        this.RinseInterval = RinseInterval;
    }

    public String getRinseTime() {
        return RinseTime;
    }

    public void setRinseTime(String RinseTime) {
        this.RinseTime = RinseTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Cmd);
        dest.writeString(this.Domain);
        dest.writeString(this.DeviceId);
        dest.writeString(this.Path);
        dest.writeString(this.TDS);
        dest.writeString(this.FlowH);
        dest.writeString(this.FlowL);
        dest.writeString(this.LeakWater);
        dest.writeString(this.Throd);
        dest.writeString(this.RinseInterval);
        dest.writeString(this.RinseTime);
    }

    public RinseIntervalBean() {
    }

    protected RinseIntervalBean(Parcel in) {
        this.Cmd = in.readString();
        this.Domain = in.readString();
        this.DeviceId = in.readString();
        this.Path = in.readString();
        this.TDS = in.readString();
        this.FlowH = in.readString();
        this.FlowL = in.readString();
        this.LeakWater = in.readString();
        this.Throd = in.readString();
        this.RinseInterval = in.readString();
        this.RinseTime = in.readString();
    }

    public static final Parcelable.Creator<RinseIntervalBean> CREATOR = new Parcelable.Creator<RinseIntervalBean>() {
        @Override
        public RinseIntervalBean createFromParcel(Parcel source) {
            return new RinseIntervalBean(source);
        }

        @Override
        public RinseIntervalBean[] newArray(int size) {
            return new RinseIntervalBean[size];
        }
    };
}
