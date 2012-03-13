package stephansun.github.samples.spring.remoting;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteInvocationBasedExporter;

public class MyInvokerServiceExporter extends RemoteInvocationBasedExporter
	implements  InitializingBean {
	
	private Object proxy;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.proxy = getProxyForService();
	}

}
