package com.ym.config;
import java.io.File;
import java.io.IOException;

/**
 * @author YiMeng
 * @DateTime: 2024/4/4 23:57
 * @Description: TODO
 */
public class CreateConfig {

    public static void create() {

        // 定义文件路径（相对路径）
        String relativePath = "./YMLogin/player_passwords.yml";

        // 创建File对象
        File passwordFile = new File(relativePath);

        // 判断文件夹YMLogin是否存在，如果不存在则创建
        File ymLoginDirectory = new File("./YMLogin");
        if (!ymLoginDirectory.exists()) {
            ymLoginDirectory.mkdir(); // 创建目录
        }

        // 判断文件是否存在，如果不存在则创建
        if (!passwordFile.exists()) {
            try {
                passwordFile.createNewFile(); // 创建文件
                System.out.println("文件player_passwords.yml已创建于 ./YMLogin 目录下");
            } catch (IOException e) {
                System.err.println("创建文件player_passwords.yml时发生错误: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("文件player_passwords.yml已存在于 ./YMLogin 目录下");
        }

    }
}
