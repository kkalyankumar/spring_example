package stephansun.github.samples.amqp.spring.workqueues;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import com.rabbitmq.client.Channel;

public class MyWorker implements ChannelAwareMessageListener {

	private void doWord(String task) {
		for (char ch : task.toCharArray()) {
			if (ch == '.') {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		throw new RuntimeException("test exception");
	}

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
System.out.println("MyWorker");
		MessageProperties messageProperties = message.getMessageProperties();
		String messageContent = (String) new SimpleMessageConverter().fromMessage(message);
		System.out.println("r[" + message + "]");
		
		// 写在前面会怎样？
		// channel.basicAck(messageProperties.getDeliveryTag(), true);
		
		
		doWord(messageContent);
System.out.println("deliveryTag是递增的");
System.out.println(messageProperties.getDeliveryTag());

		// 写在后面会怎样？
		// channel.basicAck(messageProperties.getDeliveryTag(), false);

		System.out.println("r[done]");
	}

}
