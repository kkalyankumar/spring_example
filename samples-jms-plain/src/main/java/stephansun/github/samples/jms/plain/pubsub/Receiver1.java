package stephansun.github.samples.jms.plain.pubsub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class Receiver1 {

	public static void main(String[] args) {
		// 获得连接工厂
		ConnectionFactory cf = new ActiveMQConnectionFactory(
				"tcp://localhost:61616");
		
		// javax.jms.Connection
		Connection conn = null;
		// javax.jms.Session
		Session session = null;
		
		try {
			// 创建连接
			conn = cf.createConnection();
			
			// 创建会话
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			// 选择目标
			Destination destination = new ActiveMQTopic("myTopic");
			
			// 
			MessageConsumer consumer = session.createConsumer(destination);
			
			conn.start();
			
			// 接收消息
			Message message = consumer.receive();
			
			TextMessage textMessage = (TextMessage) message;
			System.out.println("接收者1 得到一个消息：" + textMessage.getText());
		} catch (JMSException e) {
			// 处理异常
			e.printStackTrace();
		} finally {
			try {
				// 清理资源
				if (session != null) {
					session.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (JMSException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
