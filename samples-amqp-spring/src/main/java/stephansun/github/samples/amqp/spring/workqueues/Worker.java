package stephansun.github.samples.amqp.spring.workqueues;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Worker {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                		"stephansun/github/samples/amqp/spring/workqueues/spring-rabbitmq-receiver.xml");
	}
}
