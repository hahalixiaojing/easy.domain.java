package easy.domain.application;

import java.util.HashMap;

public class ApplicationFactory {
	private static final HashMap<String, IApplication> Application = new HashMap<String, IApplication>();

	private static ApplicationFactory factory;
	private ApplicationBuild build = new ApplicationBuild();

	private ApplicationFactory() {
	}

	public void register(BaseApplication application) throws Exception {
		build.build(application);
		Application.put(application.getClass().getName(), application);
	}

	@SuppressWarnings("unchecked")
	public <T extends IApplication> T get(Class<T> cls) {

		String clsName = cls.getName();

		if (Application.containsKey(clsName)) {
			return (T) Application.get(clsName);
		}
		return null;
	}

	public static ApplicationFactory instance() {
		if (factory == null) {
			synchronized (ApplicationFactory.class) {
				if (factory == null) {
					factory = new ApplicationFactory();
				}
			}
		}
		return factory;
	}
}
