package stephansun.github.samples.jms.spring.remoting;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(new String[] {
                		"stephansun/github/samples/jms/spring/remoting/jms-remoting.xml",
                		"stephansun/github/samples/jms/spring/remoting/jms-remoting-sender.xml",
                		"stephansun/github/samples/jms/spring/remoting/jms-remoting-receiver.xml"
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
