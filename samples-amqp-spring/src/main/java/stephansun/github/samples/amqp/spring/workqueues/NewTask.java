package stephansun.github.samples.amqp.spring.workqueues;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class NewTask {
	
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
		ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                		"stephansun/github/samples/amqp/spring/workqueues/spring-rabbitmq-sender.xml");
		
		RabbitTemplate rabbitTemplate = (RabbitTemplate) applicationContext.getBean("rabbitTemplate");
		
		String[] strs = new String[] { "First message." };

		String messageStr = getMessage(strs);
		
		MessagePropertiesConverter messagePropertiesConverter = new DefaultMessagePropertiesConverter();
		MessageProperties messageProperties = 
				messagePropertiesConverter.toMessageProperties(
						com.rabbitmq.client.MessageProperties.PERSISTENT_TEXT_PLAIN, null, null);
		
		MessageConverter messageConverter = new SimpleMessageConverter();
		Message message = messageConverter.toMessage(messageStr, messageProperties);
		rabbitTemplate.send("", QUEUE_NAME, message);
		
		System.out.println("s[" + message + "]");
		
	}
}
