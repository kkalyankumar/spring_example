package stephansun.github.samples.amqp.spring.listeneradapter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                		"stephansun/github/samples/amqp/spring/listeneradapter/spring-rabbitmq.xml");
		
		AmqpTemplate template = applicationContext.getBean(AmqpTemplate.class);
		//template.convertAndSend("myqueue", "foo");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("myKey", "myValue");
		template.convertAndSend("myqueue", map);
		
	}
}
