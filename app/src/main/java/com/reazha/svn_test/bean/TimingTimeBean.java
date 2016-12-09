package com.reazha.svn_test.bean;

/**
 * @author ReaZhao
 * @date 2016/12/8
 * @FileName TimingTimeBean
 * @E-mail 377742053qq.com
 * @desc 定时冲洗
 */

public class TimingTimeBean {

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
     * TimingTime : 定时冲洗
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
    private String TimingTime;
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

    public String getTimingTime() {
        return TimingTime;
    }

    public void setTimingTime(String TimingTime) {
        this.TimingTime = TimingTime;
    }

    public String getRinseTime() {
        return RinseTime;
    }

    public void setRinseTime(String RinseTime) {
        this.RinseTime = RinseTime;
    }
}
