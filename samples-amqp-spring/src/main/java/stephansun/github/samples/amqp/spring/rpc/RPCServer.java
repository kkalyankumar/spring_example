package stephansun.github.samples.amqp.spring.rpc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RPCServer {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                		"stephansun/github/samples/amqp/spring/rpc/spring-rabbitmq-server.xml");
	}
}
