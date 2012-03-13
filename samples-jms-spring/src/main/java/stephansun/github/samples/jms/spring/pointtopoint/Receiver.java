package stephansun.github.samples.jms.spring.pointtopoint;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class Receiver {

	public static void main(String[] args) throws JMSException {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                		"stephansun/github/samples/jms/spring/pointtopoint/jms-point-to-point.xml");
        
        Queue myQueue = (Queue) applicationContext.getBean("myQueue");
        JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
        
        MapMessage message = (MapMessage) jmsTemplate.receive(myQueue);
        
        String name = message.getString("name");
        int age = message.getInt("age");
        
        System.out.println("name:" + name);
        System.out.println("age:" + age);
    }
	
}
