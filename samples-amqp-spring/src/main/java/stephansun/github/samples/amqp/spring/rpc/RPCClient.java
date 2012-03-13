package stephansun.github.samples.amqp.spring.rpc;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RPCClient {
	
	private static String requestQueueName = "rpc_queue";
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                		"stephansun/github/samples/amqp/spring/rpc/spring-rabbitmq-client.xml");
		
		RabbitTemplate rabbitTemplate = (RabbitTemplate) applicationContext.getBean("rabbitTemplate");
		
		String message = "30";
		Message reply = rabbitTemplate.sendAndReceive("", requestQueueName, new SimpleMessageConverter().toMessage(message, null));
		
		if (reply == null) {
			System.out.println("接收超时，返回null");
		} else {
			System.out.println("接收到消息：");
			System.out.println(reply);
		}
	}
}
