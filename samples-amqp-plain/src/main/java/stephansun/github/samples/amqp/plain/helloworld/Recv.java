package stephansun.github.samples.amqp.plain.helloworld;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Recv {

	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		// 注意我们也在这里声明了一个queue，因为我们有可能在发送者启动前先启动接收者。
		// 我们要确保当从这个queue消费消息时，这个queue是存在的。
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("CRTL+C");
		
		// 这个另外的QueueingConsumer类用来缓存服务端推送给我们的消息。
		// 下面我们准备告诉服务端给我们传递存放在queue里的消息，因为消息是由服务端推送过来的。
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);
		
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println("[" + message + "]");
		}
	}
}
