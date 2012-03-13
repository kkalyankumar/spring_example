package stephansun.github.samples.amqp.plain.topics;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class ReceiveLogsTopic {
	
	// FIXME
	// Some teasers:
	// Will "*" binding catch a message sent with an empty routing key?
	// Will "#.*" catch a message with a string ".." as a key? Will it catch a message with a single word key?
	// How different is "a.*.#" from "a.#"?

	private static final String EXCHANGE_NAME = "topic_logs";
	
	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String queueName = channel.queueDeclare().getQueue();
		
		String[] strs = new String[] { "kern.critical", "A critical kernel error" };
		for (String str : strs) {
			channel.queueBind(queueName, EXCHANGE_NAME, str);
		}
		
		System.out.println("CTRL+C");
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
		
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			String routingKey = delivery.getEnvelope().getRoutingKey();
			
			System.out.println("r:[" + routingKey + "]:[" + message + "]");
		}
	}
}
