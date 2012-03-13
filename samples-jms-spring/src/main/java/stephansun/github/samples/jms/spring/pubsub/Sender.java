package stephansun.github.samples.jms.spring.pubsub;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class Sender {

	public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                		"stephansun/github/samples/jms/spring/pubsub/jms-pub-sub.xml");
        
        Topic myTopic = (Topic) applicationContext.getBean("myTopic");
        JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
        
        jmsTemplate.send(myTopic, new MessageCreator() {
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
