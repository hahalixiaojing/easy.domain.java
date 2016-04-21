package easy.domain.application;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import easy.domain.event.ISubscriber;

public class DefaultDomainEventSubscriberLoader implements
		IDomainEventSubscriberLoader {

	private ClassLoader cl = getClass().getClassLoader();

	private static final String[] excludeMethods = {
			"registerReturnTransformer", "registerDomainEvents",
			"registerReturnTransformer", "registerDomainEvent","find" };

	@Override
	public HashMap<String, List<ISubscriber>> find(IApplication application) {

		Stream<Method> methods = Arrays.stream(
				application.getClass().getDeclaredMethods()).filter(
				m -> !Arrays.stream(excludeMethods).anyMatch(
						s -> s.equals(m.getName())) && m.getModifiers() == 1);

		String packagename = application.getClass().getName()
				.substring(0, application.getClass().getName().length() - 11);

		String path = packagename.replace('.', '/');

		HashMap<String, List<ISubscriber>> hashMap = new HashMap<String, List<ISubscriber>>();
		
		List<Method> methodList = methods.collect(Collectors.toList());
		for (Method m : methodList) {

			String domainEventsPath = path + "/" + m.getName()
					+ "domainevents/";

			URL url = this.getUrls(domainEventsPath.toLowerCase());

			if (url == null) {
				throw new NullPointerException(url + " is not found");
			}

			List<ISubscriber> subscribers = this.getSubscribers(
					domainEventsPath.replace('/', '.').toLowerCase(), url);
			hashMap.put(m.getName(), subscribers);
		}

		return hashMap;
	}

	private List<ISubscriber> getSubscribers(String packageName, URL url) {

		File file = new File(url.getFile());
		String[] files = file.list();

		return Arrays.stream(files)
				.map(m -> packageName + m.substring(0, m.length() - 6))
				.map(m -> {
					Class<?> cls;
					try {
						cls = Class.forName(m);
						
						Object o = cls.newInstance();
						if(o instanceof ISubscriber){
							ISubscriber sub = (ISubscriber) cls.newInstance();
							return sub;
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					return null;

				}).filter(m -> m != null).collect(Collectors.toList());
	}

	private URL getUrls(String path) {
		return cl.getResource(path);
	}
}
