package com.ym.util;

import com.ym.entity.PasswordConfig;

import java.util.Iterator;

/**
 * @author YiMeng
 * @DateTime: 2024/4/14 3:27
 * @Description: 密码检查工具类
 */
public class PasswordCheckUtil {


    public static boolean checkPassword(String password) {
        if (password == null || "".equals(password)) {
            return false;
        }
        //密码校验总开关
        if (PasswordConfig.PASSWORD_STATUS_FALSE.equals(PasswordConfig.PASSWORD_CHECK_SWITCH)){
            return true;
        }
        //是否在自定义集合中
        if (PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_CUSTOM_PASSWORD_ARRAY) && checkCustomPasswordArray(password)){
            return false;
        }
        //检测长度
        if (PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_PASSWORD_LENGTH) && !checkPasswordLength(password, PasswordConfig.LIMIT_PASSWORD_MAX_LENGTH, PasswordConfig.LIMIT_PASSWORD_MIN_LENGTH)){
            return false;
        }
        //检测包含数字
        if (PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_CONTAIN_DIGIT) && !password.matches("(.*[0-9].*)")){
            return false;
        }
        //检测包含字母(区分大小写)
        if (PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_CONTAIN_UPPER_LOWER_CASE)){
            //检测包含小写字母
            if (PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_CONTAIN_LOWER_CASE) && !password.matches("(.*[a-z].*)")){
                return false;
            }
            //检测包含大写字母
            if (PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_CONTAIN_UPPER_CASE) && !password.matches("(.*[A-Z].*)")){
                return false;
            }
        } else {
            if (!checkContainCase(password)) {
                return false;
            }
        }
        //检测包含特殊符号
        if (PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_CONTAIN_SPECIAL_CHAR) && !password.matches("(.*[`~!@#$%^&*()+=|{}':;',//[//].<>/?].*)")){
            return false;
        }
        //检测键盘横向连续
        if (PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_HORIZONTAL_KEY_SEQUENTIAL) && checkLateralKeyboardSite(password)){
            return false;
        }
        //检测键盘斜向连续
        if (PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_SLOPE_KEY_SEQUENTIAL) && checkKeyboardSlantSite(password)){
            return false;
        }
        //检测逻辑位置连续
        if (PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_LOGIC_SEQUENTIAL) && checkSequentialChars(password)){
            return false;
        }
        //检测相邻字符是否相同
        if (PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_SEQUENTIAL_CHAR_SAME) && checkSequentialSameChars(password)){
            return false;
        }
        return true;
    }

    public static boolean checkPasswordForUpdate(String password) {
        if (password == null || "".equals(password)) {
            return false;
        }
        if (!checkPasswordLength(password, "", "8")) {
            return false;
        }
        if (!password.matches("(.*[0-9].*)")) {
            return false;
        }
        if (!password.matches("(.*[a-z].*)")) {
            return false;
        }
        if (!password.matches("(.*[A-Z].*)")) {
            return false;
        }
        return true;
    }

    public static boolean checkCustomPasswordArray(String password) {
        Iterator iterator = PasswordConfig.CUSTOM_PASSWORD_ARRAY.iterator();
        while(iterator.hasNext()) {
            if(password.equals(iterator.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkPasswordLength(String password, String max, String min) {
        boolean flag = false;
        if("".equals(max)) {
            if (password.length() >= Integer.parseInt(min)) {
                flag = true;
            }
        } else {
            if (password.length() >= Integer.parseInt(min) && password.length() <= Integer.parseInt(max)) {
                flag = true;
            }
        }
        return flag;
    }

    public static boolean checkContainDigit(String password) {
        boolean flag = false;
        char[] charArray = password.toCharArray();
        int numberCount = 0;
        for (char c : charArray) {
            if (Character.isDigit(c)) {
                numberCount++;
            }
        }
        if (numberCount >= 1) {
            flag = true;
        }
        return flag;
    }

    public static boolean checkContainCase(String password) {
        boolean flag = false;
        char[] charArray = password.toCharArray();
        int charCount = 0;
        for (char c : charArray) {
            if (Character.isLetter(c)) {
                charCount++;
            }
        }
        if (charCount >= 1) {
            flag = true;
        }
        return flag;
    }

    public static boolean checkContainLowerCase(String password) {
        boolean flag = false;
        char[] charArray = password.toCharArray();
        int charCount = 0;
        for (char c : charArray) {
            if (Character.isLowerCase(c)) {
                charCount++;
            }
        }
        if (charCount >= 1) {
            flag = true;
        }
        return flag;
    }

    public static boolean checkContainUpperCase(String password) {
        boolean flag = false;
        char[] charArray = password.toCharArray();
        int charCount = 0;
        for (char c : charArray) {
            if (Character.isUpperCase(c)) {
                charCount++;
            }
        }
        if (charCount >= 1) {
            flag = true;
        }
        return flag;
    }

    public static boolean checkContainSpecialChar(String password) {
        boolean flag = false;
        char[] charArray = password.toCharArray();
        int specialCount = 0;
        for (char c : charArray) {
            if (PasswordConfig.DEFAULT_SPECIAL_CHAR.indexOf(c) != -1) {
                specialCount++;
            }
        }
        if (specialCount >= 1) {
            flag = true;
        }
        return flag;
    }

    public static boolean checkLateralKeyboardSite(String password) {
        //将所有输入字符转为小写
        String lowerCasePassword = password.toLowerCase();
        //键盘横向规则检测
        int arrLength = PasswordConfig.KEYBOARD_HORIZONTAL_ARR.length;
        int limitNumKey = Integer.parseInt(PasswordConfig.LIMIT_HORIZONTAL_KEY_NUMBER) ;
        for (int i = 0; i + limitNumKey <= lowerCasePassword.length(); i++) {
            String str = lowerCasePassword.substring(i, i+limitNumKey);
            String distinguishStr = password.substring(i, i+limitNumKey);
            String revOrderStr = null;
            for (int j = 0; j < arrLength; j++) {
                String configStr = PasswordConfig.KEYBOARD_HORIZONTAL_ARR[j];
                revOrderStr = new StringBuffer(PasswordConfig.KEYBOARD_HORIZONTAL_ARR[j]).reverse().toString();
                //检测包含字母(区分大小写)
                if (PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_CONTAIN_UPPER_LOWER_CASE)) {
                    //考虑 大写键盘匹配的情况
                    String UpperStr = PasswordConfig.KEYBOARD_HORIZONTAL_ARR[j].toUpperCase();
                    if(configStr.contains(distinguishStr) || UpperStr.contains(distinguishStr)) {
                        return true;
                    }
                    //考虑逆序输入情况下 连续输入
                    String revUpperStr = new StringBuffer(UpperStr).reverse().toString();
                    if(revOrderStr.contains(distinguishStr) || revUpperStr.contains(distinguishStr)) {
                        return true;
                    }
                } else {
                    if(configStr.contains(str)) {
                        return true;
                    }
                    //考虑逆序输入情况下 连续输入
                    if(revOrderStr.contains(str)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkKeyboardSlantSite(String password) {
        //将所有输入字符转为小写
        String lowerCasePassword = password.toLowerCase();
        //键盘斜线方向规则检测
        int arrLength = PasswordConfig.KEYBOARD_SLOPE_ARR.length;
        int limitNumKey = Integer.parseInt(PasswordConfig.LIMIT_SLOPE_KEY_NUMBER);
        for (int i = 0; i + limitNumKey <= lowerCasePassword.length(); i++) {
            String str = lowerCasePassword.substring(i, i + limitNumKey);
            String distinguishStr = password.substring(i, i + limitNumKey);
            String revOrderStr = null;
            for (int j = 0; j < arrLength; j++) {
                String configStr = PasswordConfig.KEYBOARD_SLOPE_ARR[j];
                revOrderStr = new StringBuffer(PasswordConfig.KEYBOARD_SLOPE_ARR[j]).reverse().toString();
                //检测包含字母(区分大小写)
                if (PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_CONTAIN_UPPER_LOWER_CASE)) {
                    //考虑 大写键盘匹配的情况
                    String UpperStr = PasswordConfig.KEYBOARD_SLOPE_ARR[j].toUpperCase();
                    if(configStr.contains(distinguishStr) || UpperStr.contains(distinguishStr)) {
                        return true;
                    }
                    //考虑逆序输入情况下 连续输入
                    String revUpperStr = new StringBuffer(UpperStr).reverse().toString();
                    if(revOrderStr.contains(distinguishStr) || revUpperStr.contains(distinguishStr)) {
                        return true;
                    }
                } else {
                    if(configStr.contains(str)) {
                        return true;
                    }
                    //考虑逆序输入情况下 连续输入
                    if(revOrderStr.contains(str)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkSequentialChars(String password) {
        int limitNumber = Integer.parseInt(PasswordConfig.LIMIT_LOGIC_NUMBER);
        int normalCount,reversedCount;
        //检测包含字母(区分大小写)
        if (!PasswordConfig.PASSWORD_STATUS_TRUE.equals(PasswordConfig.CHECK_CONTAIN_UPPER_LOWER_CASE)) {
            password = password.toLowerCase();
        }
        char[] passwordArray = password.toCharArray();
        for (int i = 0; i + limitNumber <= password.length(); i++) {
            normalCount = 0;
            reversedCount = 0;
            for (int j = 0; j < limitNumber - 1; j++) {
                if (passwordArray[i + j + 1] - passwordArray[i + j] == 1) {
                    normalCount++;
                    if(normalCount == limitNumber - 1){
                        return true;
                    }
                }
                if (passwordArray[i + j] - passwordArray[i + j + 1] == 1) {
                    reversedCount++;
                    if(reversedCount == limitNumber - 1){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkSequentialSameChars(String password) {
        char[] passwordArray = password.toCharArray();
        int limitNumber = Integer.parseInt(PasswordConfig.LIMIT_SEQUENTIAL_CHAR_NUMBER);
        int count;
        for (int i = 0; i + limitNumber <= password.length(); i++) {
            count = 0;
            for (int j = 0; j < limitNumber - 1; j++) {
                if(passwordArray[i + j] == passwordArray[i + j + 1]) {
                    count++;
                    if (count == limitNumber - 1){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
