package producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public final static  String QUERY_NAME = "rabbitMQ.test";

    public static void main (String[] args) throws IOException, TimeoutException {

        //创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("localhost");
        //factory.setUsername("lp");
        //factory.setPassword("");
        //factory.setPort(2088);

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUERY_NAME,false,false,false,null);

        String message = "Hello RabbitMQ";

        channel.basicPublish("",QUERY_NAME,null,message.getBytes("UTF-8"));

        System.out.println("生产者发送："+message);

        channel.close();
        connection.close();


    }
}
