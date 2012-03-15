package stephansun.github.samples.amqp.plain.workqueues;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Worker {

	private final static String QUEUE_NAME = "task_queue";
	
	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		System.out.println("CRTL+C");
		
		// 这条语句告诉RabbitMQ在同一时间不要给一个worker一个以上的消息。
		// 或者换一句话说, 不要将一个新的消息分发给worker知道它处理完了并且返回了前一个消息的通知标志（acknowledged）
		// 替代的，消息将会分发给下一个不忙的worker。
		channel.basicQos(1);
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 自动通知标志
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
		
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println("r[" + message + "]");
			doWord(message);
			System.out.println("r[done]");
			// 发出通知标志
System.out.println(delivery.getEnvelope().getDeliveryTag());
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}

	private static void doWord(String task) throws InterruptedException {
		for (char ch : task.toCharArray()) {
			if (ch == '.') {
				Thread.sleep(1000);
			}
		}
	}
}
