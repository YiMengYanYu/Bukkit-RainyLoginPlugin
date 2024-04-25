package com.rainy.websocket;

import com.alibaba.fastjson2.JSON;
import com.rainy.entity.LoginConfig;
import com.rainy.entity.Result;
import jakarta.websocket.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Callable;


/**
 * @author YiMeng
 * @DateTime: 2024/4/15 下午11:34
 * @Description: TODO
 */
@ClientEndpoint
public class WebSocketClient {


    private static Session session;
    private  static JavaPlugin javaPlugin;
    public  static void  setJavaPlugin(JavaPlugin javaPlugin){
        WebSocketClient.javaPlugin = javaPlugin;
    }
    @OnOpen
    public void onOpen(Session session) {
        WebSocketClient.session = session;
        System.out.println("Connected to server");
    }



    @OnMessage
    public void onMessage(String message) {


        Result result = JSON.parseObject(message, Result.class);

        switch (result.getMsgType()){

            case msg:
                Bukkit.broadcastMessage(result.getSenderName()+":"+result.getMsg());
                break;
            case command:
                Callable taskCommand = () -> {

                    if (Bukkit.dispatchCommand(Bukkit.getConsoleSender(),result.getMsg())) {
                        sendMessage("执行命令成功");
                    }
                    return null;
                };
                Bukkit.getScheduler().callSyncMethod(javaPlugin,taskCommand);

                break;
            default:
                Bukkit.getLogger().info("未知请求");
                break;
        }

    }

    public static void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void  ws(){
        //判断WebSocket总开关
        if (!LoginConfig.webSocketCheckSwitch){

            return;
        }

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = LoginConfig.webSocketUrl+"message/"; // WebSocket服务器地址

        try {
            Session  session = container.connectToServer(WebSocketClient.class, URI.create(uri));
            session.getBasicRemote().sendText("机器人已经连接成功");


        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("ws连接失败");
        }

    }

}
