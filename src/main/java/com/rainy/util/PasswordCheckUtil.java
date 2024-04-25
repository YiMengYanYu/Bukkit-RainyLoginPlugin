package com.rainy.util;

import com.rainy.entity.PasswordConfigEntity;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Iterator;

/**
 * @author YiMeng
 * @DateTime: 2024/4/14 3:27
 * @Description: 密码检查工具类
 */
public class PasswordCheckUtil {


    public static boolean checkPassword(String password, Player player) {
        if (password == null || "".equals(password)) {
            player.sendMessage(ChatColor.RED+"密码不能为空");
            return false;
        }

        //密码校验如果是关闭的就直接return true
        if (PasswordConfigEntity.PASSWORD_STATUS_FALSE.equals(PasswordConfigEntity.PASSWORD_CHECK_SWITCH)){
            return true;
        }

        //是否在自定义集合中 如果配置的CHECK_CUSTOM_PASSWORD_ARRAY为true 并且 密码在集合中就返回false
        if (PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_CUSTOM_PASSWORD_ARRAY) && checkCustomPasswordArray(password)){
            player.sendMessage(ChatColor.RED+"密码中有不支持的字符串");
            return false;
        }

        //检测长度  如果CHECK_PASSWORD_LENGTH为true 然后检测长度是否符合要求
        if (PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_PASSWORD_LENGTH) && !checkPasswordLength(password, PasswordConfigEntity.LIMIT_PASSWORD_MAX_LENGTH, PasswordConfigEntity.LIMIT_PASSWORD_MIN_LENGTH)){

            player.sendMessage(ChatColor.RED+"密码长度不符合要求，密码最小长度应为："+PasswordConfigEntity.LIMIT_PASSWORD_MIN_LENGTH+" 最大应为："+PasswordConfigEntity.LIMIT_PASSWORD_MAX_LENGTH);

            return false;
        }
        //检测包含数字   如果配置的 CHECK_CONTAIN_DIGIT为true 并且 密码不包含数字就返回false
        if (PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_CONTAIN_DIGIT) && !password.matches("(.*[0-9].*)")){
           player.sendMessage(ChatColor.RED+"密码中必须包含数字");
            return false;
        }
        //是否包含字母
        if (PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_CONTAIN_UPPER_LOWER_CASE)){
            if (!checkContainCase(password)) {
                player.sendMessage(ChatColor.RED+"密码中必须包含字母");
                return false;
            }
        }

        //检测包含小写字母
        if (PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_CONTAIN_LOWER_CASE) && !password.matches("(.*[a-z].*)")){
            player.sendMessage(ChatColor.RED+"密码中必须包含小写字母");
            return false;
        }
        //检测包含大写字母
        if (PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_CONTAIN_UPPER_CASE) && !password.matches("(.*[A-Z].*)")){
            player.sendMessage(ChatColor.RED+"密码中必须包含大写字母");
            return false;
        }

        //检测包含特殊符号
        if (PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_CONTAIN_SPECIAL_CHAR) && checkContainSpecialChar(password)){
            player.sendMessage(ChatColor.RED+"密码中不能包含特殊符号");
            return false;
        }
        //检测键盘横向连续
        if (PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_HORIZONTAL_KEY_SEQUENTIAL) && checkLateralKeyboardSite(password)){
            player.sendMessage(ChatColor.RED+"密码中不能包含横向连续字符 如 qwer asdf");
            return false;
        }
        //检测键盘斜向连续
        if (PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_SLOPE_KEY_SEQUENTIAL) && checkKeyboardSlantSite(password)){
            player.sendMessage(ChatColor.RED+"密码中不能包含斜向连续字符 如qaz wsx edc");
            return false;
        }
        //检测逻辑位置连续
        if (PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_LOGIC_SEQUENTIAL) && checkSequentialChars(password)){
            player.sendMessage(ChatColor.RED+"密码中不能包含逻辑位置连续"+PasswordConfigEntity.LIMIT_LOGIC_NUMBER+"个字符 如1234、abcd等");
            return false;
        }
        //检测相邻字符是否相同
        if (PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_SEQUENTIAL_CHAR_SAME) && checkSequentialSameChars(password)){
            player.sendMessage(ChatColor.RED+"密码中不能包含"+PasswordConfigEntity.LIMIT_SEQUENTIAL_CHAR_NUMBER+"个相同相邻字符");
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
        Iterator iterator = PasswordConfigEntity.CUSTOM_PASSWORD_ARRAY.iterator();
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

    /**
     * 检查密码中是否包含字母（大小写任意）
     *
     * @param password 需要检查的密码字符串
     * @return 返回true如果密码中至少包含一个字母，否则返回false
     */
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
    /**
     * 检查密码中是否包含特殊字符
     * @param password 待检查的密码字符串
     * @return boolean 如果密码中至少包含一个特殊字符返回true，否则返回false
     */
    public static boolean checkContainSpecialChar(String password) {
        boolean flag = false;
        char[] charArray = password.toCharArray();
        int specialCount = 0;
        for (char c : charArray) {
            if (PasswordConfigEntity.DEFAULT_SPECIAL_CHAR.indexOf(c) != -1) {
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
        int arrLength = PasswordConfigEntity.KEYBOARD_HORIZONTAL_ARR.length;
        int limitNumKey = Integer.parseInt(PasswordConfigEntity.LIMIT_HORIZONTAL_KEY_NUMBER) ;
        for (int i = 0; i + limitNumKey <= lowerCasePassword.length(); i++) {
            String str = lowerCasePassword.substring(i, i+limitNumKey);
            String distinguishStr = password.substring(i, i+limitNumKey);
            String revOrderStr = null;
            for (int j = 0; j < arrLength; j++) {
                String configStr = PasswordConfigEntity.KEYBOARD_HORIZONTAL_ARR[j];
                revOrderStr = new StringBuffer(PasswordConfigEntity.KEYBOARD_HORIZONTAL_ARR[j]).reverse().toString();
                //检测包含字母(区分大小写)
                if (PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_CONTAIN_UPPER_LOWER_CASE)) {
                    //考虑 大写键盘匹配的情况
                    String UpperStr = PasswordConfigEntity.KEYBOARD_HORIZONTAL_ARR[j].toUpperCase();
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
        int arrLength = PasswordConfigEntity.KEYBOARD_SLOPE_ARR.length;
        int limitNumKey = Integer.parseInt(PasswordConfigEntity.LIMIT_SLOPE_KEY_NUMBER);
        for (int i = 0; i + limitNumKey <= lowerCasePassword.length(); i++) {
            String str = lowerCasePassword.substring(i, i + limitNumKey);
            String distinguishStr = password.substring(i, i + limitNumKey);
            String revOrderStr = null;
            for (int j = 0; j < arrLength; j++) {
                String configStr = PasswordConfigEntity.KEYBOARD_SLOPE_ARR[j];
                revOrderStr = new StringBuffer(PasswordConfigEntity.KEYBOARD_SLOPE_ARR[j]).reverse().toString();
                //检测包含字母(区分大小写)
                if (PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_CONTAIN_UPPER_LOWER_CASE)) {
                    //考虑 大写键盘匹配的情况
                    String UpperStr = PasswordConfigEntity.KEYBOARD_SLOPE_ARR[j].toUpperCase();
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
        int limitNumber = Integer.parseInt(PasswordConfigEntity.LIMIT_LOGIC_NUMBER);
        int normalCount,reversedCount;
        //检测包含字母(区分大小写)
        if (!PasswordConfigEntity.PASSWORD_STATUS_TRUE.equals(PasswordConfigEntity.CHECK_CONTAIN_UPPER_LOWER_CASE)) {
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
        int limitNumber = Integer.parseInt(PasswordConfigEntity.LIMIT_SEQUENTIAL_CHAR_NUMBER);
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
