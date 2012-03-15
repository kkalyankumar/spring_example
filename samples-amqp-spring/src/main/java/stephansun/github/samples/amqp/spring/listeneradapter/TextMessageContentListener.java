package stephansun.github.samples.amqp.spring.listeneradapter;

import java.io.Serializable;
import java.util.Map;


public class TextMessageContentListener implements TextMessageContentDelegate {

	@Override
	public void handleMessage(String text) {
		System.out.println("string received: " + text);
	}

	@Override
	public void handleMessage(Map text) {
		System.out.println("map received: " + text);
	}

	@Override
	public void handleMessage(byte[] text) {
		System.out.println("byte[] received: " + text);
	}

	@Override
	public void handleMessage(Serializable text) {
		System.out.println("serializable received: " + text);
	}
	
}
