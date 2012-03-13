package stephansun.github.samples.amqp.plain.routing;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {

	private static final String EXCHANGE_NAME = "direct_logs";
	
	public static void main(String[] args) throws IOException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		
		// diff
		String serverity = getServerity(new String[] { "test" });
		String message = getMessage(new String[] { "test" });
		
		channel.basicPublish(EXCHANGE_NAME, serverity, null, message.getBytes());
		System.out.println("s[" + serverity + "]:[" + message + "]");
		
		channel.close();
		connection.close();
		
	}

	private static String getServerity(String[] strings) {
		return "info";
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
