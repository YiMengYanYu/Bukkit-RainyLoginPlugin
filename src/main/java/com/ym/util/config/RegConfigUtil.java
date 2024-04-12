package com.ym.util.config;

import com.ym.constants.FileConstant;
import com.ym.util.config.ConfigUtil;
import com.ym.ymlogin.YMLogin;

/**
 * @author YiMeng
 * @DateTime: 2024/4/5 0:17
 * @Description: TODO
 */
public class RegConfigUtil  extends ConfigUtil {



    public static void loadData() {

       createFile(FileConstant.PLAYER_PASSWORDS_YML);
    }


    /**
     * 获取玩家是否注册
     * @param playerName
     * @return 注册了返回true 没注册返回false
     */
    public  static boolean isRegister(String playerName) {
        getData();
        String password = data.getString(playerName + ".password");
        if (password == null || password.length()==0 ) {
            return false;
        }

        return true;
    }



}
