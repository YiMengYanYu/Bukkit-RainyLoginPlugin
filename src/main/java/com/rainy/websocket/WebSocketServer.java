package com.rainy.websocket;

import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

/**
 * @author YiMeng
 * @DateTime: 2024/4/16 上午3:05
 * @Description: TODO
 */
@ServerEndpoint("/websocket")
public class WebSocketServer {

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("接收到" + message);
        // 在这里可以对接收到的消息进行处理
    }
}
