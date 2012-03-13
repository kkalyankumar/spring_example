package stephansun.github.samples.jms.plain.pointtopoint;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class Sender {

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
			
			// 创建队列
			Destination destination = new ActiveMQQueue("myQueue");
			
			// 设置消息
			MessageProducer producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage();
			message.setText("Hello World!");
			
			producer.send(message);
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
