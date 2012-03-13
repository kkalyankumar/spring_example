package stephansun.github.samples.amqp.spring.publishsubscribe;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReceiveLogs {
	
	private static final String EXCHANGE_NAME = "logs";
	
	private static MessageConverter messageConverter = new SimpleMessageConverter();
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                		"stephansun/github/samples/amqp/spring/publishsubscribe/spring-rabbitmq.xml");
		
		RabbitTemplate rabbitTempalte = (RabbitTemplate) applicationContext.getBean("rabbitTemplate");

		RabbitAdmin rabbitAdmin = (RabbitAdmin) applicationContext.getBean(RabbitAdmin.class);

		FanoutExchange fanoutExchange = new FanoutExchange(EXCHANGE_NAME);
		rabbitAdmin.declareExchange(fanoutExchange);
		String queueName = rabbitAdmin.declareQueue().getName();
		Binding binding = new Binding(queueName, DestinationType.QUEUE, EXCHANGE_NAME, "", null);
		rabbitAdmin.declareBinding(binding);
		
		System.out.println("CTRL+C");
		
		// FIXME 请你先思考一下，为什么要在这里暂停10秒钟？然后问我。
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Message message = rabbitTempalte.receive(queueName);
		Object obj = messageConverter.fromMessage(message);
		
		System.out.println("received:[" + obj + "]");
		
	}	
}
