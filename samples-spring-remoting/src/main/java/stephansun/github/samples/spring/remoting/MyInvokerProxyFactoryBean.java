package stephansun.github.samples.spring.remoting;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.ClassUtils;

public class MyInvokerProxyFactoryBean extends MyInvokerClientInterceptor
	implements FactoryBean<Object>, BeanClassLoaderAware {
	
	private Class serviceInterface;

	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	private Object serviceProxy;

	// FIXME for Spring injection
	public void setServiceInterface(Class serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		if (this.serviceInterface == null) {
			throw new IllegalArgumentException("Property 'serviceInterface' is required");
		}
		this.serviceProxy = new ProxyFactory(this.serviceInterface, this).getProxy(this.beanClassLoader);
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}

	@Override
	public Object getObject() throws Exception {
		return this.serviceProxy;
	}

	@Override
	public Class<?> getObjectType() {
		return this.serviceInterface;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
