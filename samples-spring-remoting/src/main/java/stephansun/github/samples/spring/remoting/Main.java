package stephansun.github.samples.spring.remoting;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(new String[] {
                		"stephansun/github/samples/spring/remoting/spring-remoting.xml"
                });
		
		MyService myService = (MyService) applicationContext.getBean("sender");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", "stephan");
		param.put("age", 26);
		String str = myService.foo(param);
		System.out.println("str:" + str);
	}
}
