package stephansun.github.samples.amqp.plain.publishsubscribe;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {
	
	// 在前面，我们使用queue，都给定了一个指定的名字。能够对一个queue命名对于我们来说是很严肃的
	// 下面我们需要将worker指定到同一个queue。
	
	// echange的类型有: direct, topic, headers and fanout.

	private static final String EXCHANGE_NAME = "logs";
	
	public static void main(String[] args) throws IOException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		// fanout exchange 将它收的所有消息广播给它知道的所有队列。
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		String message = getMessage(new String[] { "test" });
		
		// 如果routingkey存在的话，消息通过一个routingkey指定的名字路由至队列
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println("sent [" + message + "]");
		
		channel.close();
		connection.close();
	}
	
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
}
