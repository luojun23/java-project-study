package com.njtech.controller;

/**
 * @ClassName : RabbitMQExample
 * @Description :
 * @Author : 罗君
 * @Date: 2026/5/19
 */
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author heyunlin
 * @version 1.0
 */
public class RabbitMQDirect {

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
        testPutMessage();
    }

    private static void testPutMessage() {
        Connection conn = null;
        Channel channel = null;

        try {
            conn = getConnection();
            channel = conn.createChannel();
            String exchange = "test_direct";

            channel.exchangeDeclare(exchange, BuiltinExchangeType.DIRECT, true, false, null);
            //创建队列
            channel.queueDeclare("test_queue1", true, false, false, null);
            channel.queueDeclare("test_queue2", true, false, false, null);
            //绑定队列和交换机
            /**queueBind(String var1, String var2, String var3)
             * 参数1：队列名称
             * 参数2：交换机名称
             * 参数3：路由键
             */
            channel.queueBind("test_queue1", exchange, "error");
            channel.queueBind("test_queue2", exchange, "info");

            /**发布消息
             * 交换机名称,简单情况下默认使用“”
             * routingKey：路由键
             * 消息属性
             * 消息体
             */
            channel.basicPublish(exchange, "error", null, "error message".getBytes("UTF-8"));



        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }

    }

    private static void testGet() {
        Connection conn = null;
        Channel channel = null;

        try {
            conn = getConnection();
            channel = conn.createChannel();

            channel.basicConsume("test", true, (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(message);
            }, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}