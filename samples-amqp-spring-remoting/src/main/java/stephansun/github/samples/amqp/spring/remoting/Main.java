package stephansun.github.samples.amqp.spring.remoting;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(new String[] {
                		"stephansun/github/samples/amqp/spring/remoting/amqp-remoting.xml",
                		"stephansun/github/samples/amqp/spring/remoting/amqp-remoting-sender.xml",
                		"stephansun/github/samples/amqp/spring/remoting/amqp-remoting-receiver.xml"
                });
		MyService sender = (MyService) applicationContext.getBean("sender");
		sender.sayHello();
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", "stephan");
		param.put("age", 26);
		String str = sender.foo(param);
		System.out.println("str:" + str);
	}
}
