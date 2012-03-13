package stephansun.github.samples.amqp.plain.rpc;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class RPCServer {

	// 我们的代码仍然相当简单，没有试图解决更复杂（或者更重要）的问题，像：
	// 客户端在没有服务端运行的情况下如何处理？
	// 一个RPC的客户端应该有一些超时类型吗？
	// 如果服务端出现异常，是否应该将异常返回给客户端？
	// 在进行业务处理前阻止不合法的消息进入（比如检查绑定,类型）
	// Protecting against invalid incoming messages (eg checking bounds, type) before processing.

	private static final String RPC_QUEUE_NAME = "rpc_queue";
	
	// FIXME Don't expect this one to work for big numbers, and it's probably the slowest recursive implementation possible.
	private static int fib(int n) {
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		return fib(n - 1) + fib(n - 2);
	}
	
	public static void main(String[] args) {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
			
			// We might want to run more than one server process. 
			// In order to spread the load equally over multiple servers we need to set the prefetchCount setting in channel.basicQos.
			channel.basicQos(1);
			
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
			
			System.out.println("[x] Awaiting RPC requests");
			
			while (true) {
				String response = null;
				
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				
				BasicProperties props = delivery.getProperties();
				BasicProperties replyProps = new BasicProperties.Builder().correlationId(props.getCorrelationId()).build();
				
				try {
					String message = new String(delivery.getBody(), "UTF-8");
					int n = Integer.parseInt(message);
					
					System.out.println(" [.] fib(" + message + ")");
					response = "" + fib(n);
				} catch (Exception e) {
					System.out.println(" [.] " + e.toString());
					response = "";
				} finally {
					channel.basicPublish("", props.getReplyTo(), replyProps, response.getBytes("UTF-8"));
					
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ignore) {
					// ignore
				}
			}
		}
	}
}
