package com.reazha.svn_test.util;

import com.reazha.svn_test.bean.DeviceBean;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ReaZhao
 * @date 2016/12/8
 * @FileName StringUtils
 * @E-mail 377742053qq.com
 * @desc 字符串相关工具类
 */
public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (a == b) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 去除beans里重复的对象  并且不乱排序
     * @param beans
     * @return
     */
    public static List<DeviceBean> removeDuplicate(List<DeviceBean> beans) {
        if (beans == null) {
            return null;
        }
        Set<DeviceBean> set = new LinkedHashSet<DeviceBean>();
        set.addAll(beans);
        beans.clear();
        beans.addAll(set);
        return beans;
    }

    /**
     *  蓝牙BLE通讯，每次最多发送20个字符
     *  把发送的内容分包发送，每次最多发送20个字符
     * @param msg
     * @return
     */
    public static String[] getPackage(String msg) {
        String[] pack;
        if (msg.length() % ConstantUtils.package_length == 0) {
            pack = new String[(msg.length() / ConstantUtils.package_length)];
            for (int i = 0; i < (msg.length() / ConstantUtils.package_length); i++) {
                pack[i] = msg
                        .substring(
                                (ConstantUtils.package_length * i),
                                (ConstantUtils.package_length * i + ConstantUtils.package_length));
            }
        } else {
            pack = new String[((msg.length() / ConstantUtils.package_length) + 1)];
            for (int i = 0; i <= (msg.length() / ConstantUtils.package_length); i++) {
                if ((ConstantUtils.package_length * i + ConstantUtils.package_length) > msg
                        .length()) {
                    pack[i] = msg.substring((ConstantUtils.package_length * i),
                            msg.length());
                } else {
                    pack[i] = msg
                            .substring(
                                    (ConstantUtils.package_length * i),
                                    (ConstantUtils.package_length * i + ConstantUtils.package_length));
                }
            }
        }
        return pack;
    }

    /**
     * 获得每次发送内容总共有多少包的数
     * @param msg
     * @return
     */
    public static String getPackageNumber(String msg) {
        if (isEmpty(msg)) {
            return "00";
        }
        int number;
        if (msg.length() % ConstantUtils.package_length == 0) {
            number = msg.length() / ConstantUtils.package_length;
        } else {
            number = (msg.length() / ConstantUtils.package_length) + 1;
        }
        if (msg.length() / ConstantUtils.package_length < 10) {
            return "0" + number;
        } else {
            return String.valueOf(number);
        }

    }

    /**
     * 判断每次包是否大于10，没有就在前面加0
     * 比如   1 ->01   10->10
     * @param number
     * @return
     */
    public static String getPackNumber(int number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return String.valueOf(number);
        }
    }
}