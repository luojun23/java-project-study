package com.njtech.controller;

/**
 * @ClassName : RabbitMQExample
 * @Description :
 * @Author : 罗君
 * @Date: 2026/5/19
 */
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author heyunlin
 * @version 1.0
 */
public class RabbitMQExample2 {

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

    private static void sendMessage() {
        String queue = "test";
        String exchange = "test_exchange";

        try (Connection conn = getConnection(); Channel channel = conn.createChannel()) {
            // 创建队列
            /**
             * 队列名称
             * 是否持久化
             * 是否独占
             * 是否自动删除
             * 队列参数
             */
            AMQP.Queue.DeclareOk testQueue = channel.queueDeclare(queue, true, false, false, null);
            System.out.println(testQueue.getQueue());

            // 队列和交换机绑定
            channel.queueBind(queue, exchange, "");

            // 删除队列
            channel.queueDelete(queue);

            // 清空队列
            channel.queuePurge(queue);

            // 创建交换机
            channel.exchangeDeclare(exchange, "direct");

            // 取消交换机和队列的绑定关系
            channel.queueUnbind(queue, exchange, "");

            // 发布消息
            channel.basicPublish(exchange, "", null, "send message from exchange test_exchange.".getBytes());

            // 交换机和交换机/队列绑定
            channel.exchangeBind(queue, exchange, "");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static void testPutMessage() {
        Connection conn = null;
        Channel channel = null;

        try {
            conn = getConnection();
            channel = conn.createChannel();

            // 获取队列信息，如果队列不存在则抛出异常
            AMQP.Queue.DeclareOk helloQueue = channel.queueDeclarePassive("test");
            // 获取队列信息，如果队列不存在则创建队列
            //AMQP.Queue.DeclareOk helloQueue = channel.queueDeclare("test", true, false, false, null);

            // 队列名称
            String queue = helloQueue.getQueue();
            // 消息数量
            int messageCount = helloQueue.getMessageCount();
            // 消费者数量
            int consumerCount = helloQueue.getConsumerCount();

            System.out.println(queue);
            System.out.println(messageCount);
            System.out.println(consumerCount);

            /**
             * 发布消息
             * 交换机名称,简单情况下默认使用“”
             * 队列名称
             * 消息属性props配置信息
             * 消息体
             */
            for (int i = 1; i <= 10; i++) {
                channel.basicPublish("", queue, null, (i+"hello rabbitMQ").getBytes("UTF-8"));
            }

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