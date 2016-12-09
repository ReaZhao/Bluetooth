package com.reazha.svn_test.util;

public class ConstantUtils {
	public static String UUID_KEY_DATA = "0000ffe1-0000-1000-8000-00805f9b34fb";
	public static String UUID_CHAR1 = "0000fff1-0000-1000-8000-00805f9b34fb";
	public static String UUID_CHAR2 = "0000fff2-0000-1000-8000-00805f9b34fb";
	public static String UUID_CHAR3 = "0000fff3-0000-1000-8000-00805f9b34fb";
	public static String UUID_CHAR4 = "0000fff4-0000-1000-8000-00805f9b34fb";
	public static String UUID_CHAR5 = "0000fff5-0000-1000-8000-00805f9b34fb";
	public static String UUID_CHAR6 = "0000fff6-0000-1000-8000-00805f9b34fb";
	public static String UUID_CHAR7 = "0000fff7-0000-1000-8000-00805f9b34fb";
	public static String UUID_HERATRATE = "00002a37-0000-1000-8000-00805f9b34fb";
	public static String UUID_TEMPERATURE = "00002a1c-0000-1000-8000-00805f9b34fb";
	/**
	 * 每个包的长度
	 */
	public final static int package_length=14;
	/**
	 * 每次发送包的规则开头
	 */
	public final static String head="1400";
	/**
	 * 发送总包的规则开头
	 */
	public final static String head1="1399";
	/**
	 * 发送包规则开头的长度
	 */
	public final static int TOTAL_INDEX=4;
	/**
	 * 包规则开头和总包数的长度
	 */
	public final static int TOTAL_PACK_INDEX=6;
	/**
	 * 返回包的结果错误，发送的内容
	 */
	public final static String ERROR="Error";
	/**
	 * 定时冲洗设置完成
	 */
	public final static String TimingRinse="TimingRinseOK";
	/**
	 * 冲洗时间间隔设置完成
	 */
	public final static String Rinse="RinseOK";
	/**
	 * 电池阀设置完成
	 */
	public final static String DeviceTestON="DeviceTestONOK";
	/**
	 * 电池阀设置完成
	 */
	public final static String DeviceTestOFF="DeviceTestOFFOK";
	/**
	 * 设备ID设置完成
	 */
	public final static String DeviceConf="DeviceConfOK";
	/**
	 * 流量设置完成
	 */
	public final static String FlowConf="FlowConfOK";
	/**
	 * 查询服务器配置命令
	 */
	public final static String GetConfInfo="{\"Cmd\":\"GetConfInfo\"}";
	/**
	 * 设备登录服务器配置完成
	 */
	public final static String Config="ConfigOK";
	/**
	 * 电池阀打开
	 */
	public final static String BATTERY_ON="ON";
	/**
	 * 电池阀关闭
	 */
	public final static String BATTERY_OFF="OFF";
	/**
	 * 设备id配置
	 */
	public final static int DEVICE_ID=1;
	/**
	 * 流量配置
	 */
	public final static int FLOW=2;
	/**
	 * 服务器配置
	 */
	public final static int SERVER=3;
	/**
	 * 定时冲洗配置
	 */
	public final static int TIMING_RINSE=4;
	/**
	 * 间隔冲洗配置
	 */
	public final static int INTERVAL_RINSE=5;
	
	
}
