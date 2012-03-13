package stephansun.github.samples.amqp.plain.rpc;

import java.io.IOException;
import java.util.UUID;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class RPCClient {
	
	// FIXME
	// AMQP协议预定义了14种伴随着消息的属性。大多数属性很少使用到。除了以下这些异常情况：
	// deliveryMode:
	// contentType:
	// replyTo:
	// correlationId: 
	
	// FIXME
	// 为什么我们忽略掉callback队列里的消息，而不是抛出错误？
	// 这取决于服务端的竞争条件的可能性。
	// 虽然不太可能，但这种情况是存在的，即
	// RPC服务在刚刚将答案发给我们，然而没等我们将通知标志后返回时就死了
	// 如果发生了这种情况, 重启的RPC服务将会重新再次处理该请求。
	// 这就是为什么在客户端我们必须优雅地处理重复性的响应，及RPC在理想情况下应该时幂等的。（不太理解这句话的意思）

	private Connection connection;
	private Channel channel;
	private String requestQueueName = "rpc_queue";
	private String replyQueueName;
	private QueueingConsumer consumer;
	
	public RPCClient() throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
		
		// temporary queue.
		replyQueueName = channel.queueDeclare().getQueue();
		consumer = new QueueingConsumer(channel);
		channel.basicConsume(replyQueueName, true, consumer);
	}
	
	public String call(String message) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		String response = null;
		String corrId = UUID.randomUUID().toString();
		
		// in order to receive a response we need to send a 'callback' queue address with the request.
		// We can use the default queue(which is exclusive in the Java client)
		BasicProperties props = new BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();
		
		channel.basicPublish("", requestQueueName, props, message.getBytes());
		
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			if (delivery.getProperties().getCorrelationId().equals(corrId)) {
				response = new String(delivery.getBody(), "UTF-8");
				break;
			}
		}
		
		return response;
	}
	
	public void close() throws IOException {
		connection.close();
	}
	
	public static void main(String[] args) {
		RPCClient fibonacciRpc = null;
		String response = null;
		try {
			fibonacciRpc = new RPCClient();
			
			System.out.println("fib(30)");
			response = fibonacciRpc.call("30");
			System.out.println("got[" + response + "]");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fibonacciRpc != null) {
				try {
					fibonacciRpc.clone();
				} catch (Exception ignore) {
					// ignore
				}
			}
		}
	}
}
