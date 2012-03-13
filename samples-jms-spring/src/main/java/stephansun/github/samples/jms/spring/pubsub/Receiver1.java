package stephansun.github.samples.jms.spring.pubsub;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Topic;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class Receiver1 {

	public static void main(String[] args) throws JMSException {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                		"stephansun/github/samples/jms/spring/pubsub/jms-pub-sub.xml");
        
        Topic myTopic = (Topic) applicationContext.getBean("myTopic");
        JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
        
        MapMessage message = (MapMessage) jmsTemplate.receive(myTopic);
        
        String name = message.getString("name");
        int age = message.getInt("age");
        
        System.out.println("name:" + name);
        System.out.println("age:" + age);
    }
	
}
