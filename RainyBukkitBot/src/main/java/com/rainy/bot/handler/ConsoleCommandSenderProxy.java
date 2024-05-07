package com.rainy.bot.handler;

import com.rainy.bot.websocket.WebSocketClient;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author YiMeng
 * @DateTime: 2024/5/7 下午3:45
 * @Description: TODO
 */
public class ConsoleCommandSenderProxy implements InvocationHandler {

    private final ConsoleCommandSender originalSender;

    public ConsoleCommandSenderProxy(ConsoleCommandSender originalSender) {
        this.originalSender = originalSender;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if ("sendMessage".equals(method.getName())) {

            for (Object arg : args) {
                WebSocketClient.sendMessage(arg.toString());

            }

        } else if ("getServer".equals(method.getName())) {


            WebSocketClient.sendMessage("命令执行成功");
        }

        return method.invoke(originalSender, args);

    }

    public static ConsoleCommandSender createProxy(ConsoleCommandSender originalSender) {
        return (ConsoleCommandSender) Proxy.newProxyInstance(
                ConsoleCommandSender.class.getClassLoader(),
                new Class<?>[]{ConsoleCommandSender.class},
                new ConsoleCommandSenderProxy(originalSender)
        );
    }
}

