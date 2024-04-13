package com.ym.entity;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YiMeng
 * @DateTime: 2024/4/14 3:18
 * @Description: 存放与密码验证规则和设置相关的变量
 */
@ToString
public class PasswordConfig {


    /**
     * 开关->开
     */
    public final static String PASSWORD_STATUS_TRUE = "true";

    /**
     * 开关->关
     */
    public final static String PASSWORD_STATUS_FALSE = "false";

    /**
     * 密码检测开关
     */
    public static volatile String PASSWORD_CHECK_SWITCH;
    /**
     * 是否检测密码口令长度标识
     */
    public static volatile String CHECK_PASSWORD_LENGTH;
    /**
     * 密码最小长度
     */
    public static volatile String LIMIT_PASSWORD_MIN_LENGTH;
    /**
     * 密码最大长度
     */
    public static volatile String LIMIT_PASSWORD_MAX_LENGTH;
    /**
     * 是否包含数字
     */
    public static volatile String CHECK_CONTAIN_DIGIT;
    /**
     * 是否区分大小写
     */
    public static volatile String CHECK_CONTAIN_UPPER_LOWER_CASE;
    /**
     * 是否包含小写字母
     */
    public static volatile String CHECK_CONTAIN_LOWER_CASE;
    /**
     * 是否包含大写字母
     */
    public static volatile String CHECK_CONTAIN_UPPER_CASE;
    /**
     * 是否包含特殊符号
     */
    public static volatile String CHECK_CONTAIN_SPECIAL_CHAR;
    /**
     * 特殊符号集合
     */
    public static volatile String DEFAULT_SPECIAL_CHAR;
    /**
     * 是否检测键盘按键横向连续
     */
    public static volatile String CHECK_HORIZONTAL_KEY_SEQUENTIAL;
    /**
     * 键盘物理位置横向不允许最小的连续个数
     */
    public static volatile String LIMIT_HORIZONTAL_KEY_NUMBER;
    /**
     * 是否检测键盘按键斜向连续
     */
    public static volatile String CHECK_SLOPE_KEY_SEQUENTIAL;
    /**
     * 键盘物理位置斜向不允许最小的连续个数
     */
    public static volatile String LIMIT_SLOPE_KEY_NUMBER;
    /**
     * 是否检测逻辑位置连续
     */
    public static volatile String CHECK_LOGIC_SEQUENTIAL;
    /**
     * 密码口令中字符在逻辑位置上不允许最小的连续个数
     */
    public static volatile String LIMIT_LOGIC_NUMBER;
    /**
     * 是否检测连续字符相同
     */
    public static volatile String CHECK_SEQUENTIAL_CHAR_SAME;
    /**
     * 密码口令中相同字符不允许最小的连续个数
     */
    public static volatile String LIMIT_SEQUENTIAL_CHAR_NUMBER;
    /**
     * 是否校验自定义密码集合
     */
    public static volatile String CHECK_CUSTOM_PASSWORD_ARRAY;
    /**
     * 自定义密码集合
     */
    public static volatile List<String> CUSTOM_PASSWORD_ARRAY = new ArrayList<>();
    /**
     * 键盘横向方向规则
     */
    public static volatile String[] KEYBOARD_HORIZONTAL_ARR = {"01234567890", "qwertyuiop", "asdfghjkl", "zxcvbnm"};
    /**
     * 键盘斜线方向规则
     */
    public static volatile String[] KEYBOARD_SLOPE_ARR = {
            "1qaz", "2wsx", "3edc", "4rfv", "5tgb", "6yhn", "7ujm", "8ik,", "9ol.", "0p;/", "=[;.", "-pl,", "0okm", "9ijn", "8uhb", "7ygv", "6tfc", "5rdx", "4esz",
            "!QAZ", "@WSX", "#EDC", "$RFV", "%TGB", "^YHN", "&UJM", "*IK<", "(OL>", ")P:?", "+{:>", "_PL<", ")OKM", "(IJN", "*UHB", "&YGV", "^TFC", "%RDX", "$ESZ"
    };



}
