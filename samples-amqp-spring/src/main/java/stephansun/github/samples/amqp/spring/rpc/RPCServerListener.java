package stephansun.github.samples.amqp.spring.rpc;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

public class RPCServerListener implements MessageListener {

	private RabbitTemplate rabbitTemplate;

	private static MessageConverter messageConverter = new SimpleMessageConverter();
	
	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void onMessage(Message requestMessage) {
		Object obj = messageConverter.fromMessage(requestMessage);
		String str = (String) obj;
		int n = Integer.parseInt(str);
		System.out.println(" [.] fib(" + requestMessage + ")");
		String response = "" + fib(n);
		String replyTo = requestMessage.getMessageProperties().getReplyTo();
		rabbitTemplate.send(
				"", 
				replyTo, 
				messageConverter.toMessage(response, null));
	}

	private static int fib(int n) {
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		return fib(n - 1) + fib(n - 2);
	}

}
