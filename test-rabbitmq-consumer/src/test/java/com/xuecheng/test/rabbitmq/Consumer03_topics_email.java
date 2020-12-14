package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer03_topics_email {
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String EXCHANGE_TOPICS_INFORM="exchange_topics_inform";
    private static final String ROUTINGKEY_EMAIL="inform.#.email.#";

    public static void main(String[] args) throws IOException, TimeoutException {
        //和MQ建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        //设置虚拟机  一个mq服务就可以设置多个虚拟机，每个虚拟机就相当于一个独立的mq
        connectionFactory.setVirtualHost("/");
        //建立新连接
        Connection connection= connectionFactory.newConnection();
        //创建会话通道，生产者和mq服务所有通信都在channel通道中完成
        Channel channel = connection.createChannel();

        //声明队列 如果队列在mq中，没有则要创建
        //参数:String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        /**
         * 参数明细：
         * 1、queue 队列名称
         * 2、durable 是否持久化，如果持久化，mq重启后队列还在
         * 3、exclusive 是否独占连接，队列只允许在该连接中访问，如果在connection连接关闭队列则自动删除，，如果将此参数设置为true可用于临时队列的创建
         * 4、autoDelete 自动删除，队列不再使用时，是否自动删除此队列，如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了就自动删除）
         * 5、arguments 参数，可以设置一个队列的扩展参数 比如可设置存活时间
         */
        channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);

        //声明一个交换机
        //参数：String exchange, BuiltinExchangeType type
        /**
         * 参数明细：
         * 1、交换机的类型
         * 2、交换机的名称
         * fanout ：对应的rabbitmq的工作模式 publish/subscribe
         * direct ：对应的Routing 工作模式
         * topic ： 对应Topic工作模式
         * headers：对应Headers工作模式
         */
        channel.exchangeDeclare(EXCHANGE_TOPICS_INFORM, BuiltinExchangeType.TOPIC);
        //进行交换机和队列的绑定
        //参数 String queue, String exchange, String routingKey
        /**
         * 参数名称
         * 1、queue 队列名称
         * 2、exchange 交换机名称
         * 3、routing 路由key 作用是交换机根据路由key的值转发到指定的队列中，在发布订阅模式中设置为空串
         */
        channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_TOPICS_INFORM,ROUTINGKEY_EMAIL);
        //实现消费方法
        DefaultConsumer defaultConsumer= new DefaultConsumer(channel){
            /**
             * 当接收到消息后此方法将被调用
             * @param consumerTag   消费者标签  用来标识消费者的，在监听队列时设置channel.basicConsumer()
             * @param envelope      信封 通过envelope
             * @param properties     消息属性
             * @param body            消息内容
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //交换机
                String exchange = envelope.getExchange();
                //消息Id mq在channel中用来标识消息已接收
                long deliveryTag = envelope.getDeliveryTag();
                //消息内容
                String message = new String(body,"utf-8");
                System.out.println("receive message"+message);

            }
        };


        //监听队列
        //参数：String queue, boolean autoAck, Consumer callback
        /**
         * 参数明细：
         * 1、queue 队列名称
         * 2、autoAck 自动回复 当消费者接收到消息后要告诉mq消息已接收，如果将此参数设置为true表示会自动回复mq，如果设置为false则要通过编程实现回复
         * 3、callback 消费方法 当消费者接收到消息要执行的方法
         */
        channel.basicConsume(QUEUE_INFORM_EMAIL,true,defaultConsumer);
    }
}
