package com.njtech.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName : TestController
 * @Description :
 * @Author : 罗君
 * @Date: 2026/4/9
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test() {
        return "hello world";
    }

    /**
     * 获取RabbitMQ连接
     * @return com.rabbitmq.client.Connection
     * @throws IOException
     * @throws TimeoutException
     */
    private static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5672);

        return factory.newConnection();
    }

    public static void main(String[] args) {
        Connection conn = null;
        Channel channel = null;

        try {
            conn = getConnection();
            channel = conn.createChannel();

            channel.basicConsume("test_queue1", true, (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(message);
            }, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
