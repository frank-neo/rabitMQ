package newtask;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Work1 {

    private static final String Task_QUERY_NAME = "task_query";

    public static void main(String[] args) throws IOException, TimeoutException {

        final ConnectionFactory factory = new ConnectionFactory();

       factory.setHost("localhost");

        Connection connection = factory.newConnection();

        final Channel channel = connection.createChannel();

        channel.queueDeclare(Task_QUERY_NAME, true, false,false,null);

        System.out.println("工人-1 准备接受消息！");

        //每次从队列获取的数量
        channel.basicQos(1);

         final Consumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");

                System.out.println("工人-1 接受到消息："+message);
            }

        };

        boolean autoAck=true;
        //消息消费完成确认
        channel.basicConsume(Task_QUERY_NAME, autoAck, consumer);

    }


}
