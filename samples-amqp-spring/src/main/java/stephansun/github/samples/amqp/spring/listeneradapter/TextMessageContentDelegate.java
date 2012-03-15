package stephansun.github.samples.amqp.spring.listeneradapter;

import java.io.Serializable;
import java.util.Map;

public interface TextMessageContentDelegate {

	void handleMessage(String text);
	
	void handleMessage(Map text);
	
	void handleMessage(byte[] text);
	
	void handleMessage(Serializable text);
	
}
