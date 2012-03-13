package stephansun.github.samples.amqp.spring.helloworld;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Recv {

	private final static String QUEUE_NAME = "hello";
	
	private static MessageConverter messageConverter = new SimpleMessageConverter();
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                		"stephansun/github/samples/amqp/spring/helloworld/spring-rabbitmq.xml");
		
		RabbitTemplate rabbitTempalte = (RabbitTemplate) applicationContext.getBean("rabbitTemplate");
		
		Message message = rabbitTempalte.receive(QUEUE_NAME);
		
		Object obj = messageConverter.fromMessage(message);
		
		System.out.println("received:[" + obj + "]");
		
	}
}
