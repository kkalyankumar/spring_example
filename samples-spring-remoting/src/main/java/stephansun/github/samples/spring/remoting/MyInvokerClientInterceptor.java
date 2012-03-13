package stephansun.github.samples.spring.remoting;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.DefaultRemoteInvocationFactory;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationFactory;
import org.springframework.remoting.support.RemoteInvocationResult;

public class MyInvokerClientInterceptor implements MethodInterceptor, InitializingBean {
	
	private RemoteInvocationFactory remoteInvocationFactory = new DefaultRemoteInvocationFactory();
	
	public void setRemoteInvocationFactory(RemoteInvocationFactory remoteInvocationFactory) {
		this.remoteInvocationFactory =
				(remoteInvocationFactory != null ? remoteInvocationFactory : new DefaultRemoteInvocationFactory());
	}
	
	protected RemoteInvocation createRemoteInvocation(MethodInvocation methodInvocation) {
		return this.remoteInvocationFactory.createRemoteInvocation(methodInvocation);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("afterPropertiesSet");
	}

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		RemoteInvocation invocation = createRemoteInvocation(methodInvocation);
		Object[] arguments = invocation.getArguments();
		System.out.println("arguments:" + arguments);
		String methodName = invocation.getMethodName();
		System.out.println("methodName:" + methodName);
		Class[] classes = invocation.getParameterTypes();
		System.out.println("classes:" + classes);
		// do whatever you want to do
		RemoteInvocationResult result = new RemoteInvocationResult("hello, world!");
		return result.getValue();
	}

}
