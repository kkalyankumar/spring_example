package stephansun.github.samples.amqp.spring.remoting;

import java.util.Map;

public class MyServiceImpl implements MyService {

	@Override
	public void sayHello() {
		System.out.println("hello world!");
	}

	@Override
	public String foo(Map<String, Object> param) {
		return param.toString();
	}

}
