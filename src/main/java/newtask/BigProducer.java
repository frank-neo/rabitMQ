package newtask;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
 *积累了大量的工作，我们就需要更多的工作者来处理，这里就要采用分布机制了
 * 先运行两个work进行监听，然后再跑BigProducer类，你就会会发现每个work平分得接受了消息！
 */
public class BigProducer {

    private static final String Task_QUERY_NAME = "task_query";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(Task_QUERY_NAME, true, false, false, null);

        //模拟大批量分发消息
        for (int i = 0; i < 21; i++) {

            String message = "分发的消息：" + i;
            channel.basicPublish("", Task_QUERY_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            System.out.println("生产者发送了：" + message);

        }

        channel.close();
        connection.close();

    }

}
