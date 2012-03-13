package stephansun.github.samples.amqp.spring.topics;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmitLogTopic {

	private static final String EXCHANGE_NAME = "topic_logs";
	
	private static MessageConverter messageConverter = new SimpleMessageConverter();
	
	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                		"stephansun/github/samples/amqp/spring/topics/spring-rabbitmq.xml");
		
		RabbitTemplate rabbitTempalte = (RabbitTemplate) applicationContext.getBean("rabbitTemplate");
		
		// diff
		String serverity = getServerity(new String[] { "test" });
		String message = getMessage(new String[] { "test" });
		
		rabbitTempalte.send(EXCHANGE_NAME, serverity, messageConverter.toMessage(message, null));
		System.out.println("s[" + serverity + "]:[" + message + "]");
	}

	private static String getServerity(String[] strings) {
		return "kern.critical";
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
