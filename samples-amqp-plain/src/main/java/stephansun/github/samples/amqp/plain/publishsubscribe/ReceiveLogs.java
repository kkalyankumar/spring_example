package stephansun.github.samples.amqp.plain.publishsubscribe;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class ReceiveLogs {
	
	// 就像你看到的, 创建了连接后，我们声明了一个exchange，这一步是必须的，因为将消息发送到一个并不存在的exchange上是不允许的。
	// 如果还没有queue绑定到exchange上，消息将会丢失。
	// 但那对我们来说是ok的。
	// 如果没有消费者在监听，我们可以安全地丢弃掉消息。
	
	// RabbitMQ中有关消息模型地核心观点是，生产者永远不会直接将消息发往队列。
	// 事实上，相当多的生产者甚至根本不知道一个消息是否已经传递给了一个队列。
	// 相反，生产者只能将消息发送给一个exchange。
	// exchange是一个很简单的东西。
	// 一边它接收来自生产者的消息，另一边它将这些消息推送到队列。
	// exchagne必须明确地知道拿它收到的消息来做什么。把消息附在一个特定的队列上？把消息附在很多队列上？或者把消息丢弃掉。
	// 这些规则在exchange类型里都有定义。

	private static final String EXCHANGE_NAME = "logs";
	
	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		// 创建fanout类型的exchange, 我们叫它logs:
		// 这种类型的exchange将它收到的所有消息广播给它知道的所有队列。
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		// 临时队列（temporary queue）
		// 首先，无论什么时候连接Rabbit时，我们需要一个fresh的，空的队列
		// First, whenever we connect to Rabbit we need a fresh, empty queue.
		// 为了做到这一点，我们可以创建一个随机命名的队列，或者更好的，就让服务端给我们选择一个随机的队列名字。
		// 其次，一旦我们关闭消费者的连接，这个临时队列应该自动销毁。
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, "");
		
		System.out.println("CTRL+C");
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
		
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			
			System.out.println("r[" + message + "]");
		}
	}	
}
