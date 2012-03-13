package stephansun.github.samples.jms.spring.pointtopoint;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class Sender {

	public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                		"stephansun/github/samples/jms/spring/pointtopoint/jms-point-to-point.xml");
        
        Queue myQueue = (Queue) applicationContext.getBean("myQueue");
        JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
        
        jmsTemplate.send(myQueue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();
				message.setString("name", "stephan");
				message.setInt("age", 26);
				return message;
			}
        });
    }
	
}
