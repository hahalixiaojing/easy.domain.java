package easy.domain.repository.framework.test;

import java.lang.reflect.Method;

import easy.core.InterfaceProxy;

public class DemoRepositoryProxy extends InterfaceProxy {
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("DemoRepositoryProxy");
		return super.invoke(proxy, method, args);
	}
}
