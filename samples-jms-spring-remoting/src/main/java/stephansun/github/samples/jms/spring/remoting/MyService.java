package stephansun.github.samples.jms.spring.remoting;

import java.util.Map;

public interface MyService {

	void sayHello();
	
	String foo(Map<String, Object> param);
	
}
