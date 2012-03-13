package stephansun.github.samples.amqp.plain.workqueues;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {
	
	// 使用Work Queues (也称为Task Queues)最主要的想法是分流那些耗时，耗资源的任务，不至于使队列拥堵。 

	private static String getMessage(String[] strings) {
		if (strings.length < 1) {
			return "Hello World!";
		}
		return joinStrings(strings, " ");
	}

	private static String joinStrings(String[] strings, String delimiter) {
		int length = strings.length;
		if (length == 0) {
			return "";
		}
		StringBuilder words = new StringBuilder(strings[0]);
		for (int i = 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}
	
	private final static String QUEUE_NAME = "task_queue";
	
	public static void main(String[] args) throws IOException {
		String[] strs = new String[] { "First message." };
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		// 跟helloworld的不同点
		boolean durable = true;
		// 下面这个声明队列的队列名字改了，所以生产者和消费者两边的程序都要改成统一的队列名字。
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		// 有了durable为true，我们可以保证名叫task_queue的队列即使在RabbitMQ重启的情况下也不会消失。
		String message = getMessage(strs);
		// 现在我们需要将消息标记成可持久化的。
		// 如果你需要更强大的保证消息传递，你可以将发布消息的代码打包到一个事务里。 
		channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		System.out.println("s[" + message + "]");
		
		channel.close();
		connection.close();
	}
}
