package stephansun.github.samples.amqp.spring.topics;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReceiveLogsTopic {
	

	private static final String EXCHANGE_NAME = "topic_logs";
	
	private static MessageConverter messageConverter = new SimpleMessageConverter();
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                		"stephansun/github/samples/amqp/spring/topics/spring-rabbitmq.xml");
		
		RabbitTemplate rabbitTempalte = (RabbitTemplate) applicationContext.getBean("rabbitTemplate");

		RabbitAdmin rabbitAdmin = (RabbitAdmin) applicationContext.getBean(RabbitAdmin.class);

		TopicExchange directExchange = new TopicExchange(EXCHANGE_NAME);
		rabbitAdmin.declareExchange(directExchange);
		String queueName = rabbitAdmin.declareQueue().getName();
		String[] strs1 = new String[] { "#" };
		String[] strs2 = new String[] { "kern.*" };
		String[] strs3 = new String[] { "*.critical" };
		String[] strs4 = new String[] { "kern.*", "*.critical" };
		String[] strs5 = new String[] { "kern.critical", "A critical kernel error" };
		for (String str : strs5) {
			Binding binding = new Binding(queueName, DestinationType.QUEUE, EXCHANGE_NAME, str, null);
			rabbitAdmin.declareBinding(binding);
		}
		
		System.out.println("CTRL+C");
		
		// FIXME 请你先思考一下，为什么要在这里暂停10秒钟？然后问我。
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Message message = rabbitTempalte.receive(queueName);
		Object obj = messageConverter.fromMessage(message);
		
		System.out.println("received:[" + obj + "]");
	}
	
}
