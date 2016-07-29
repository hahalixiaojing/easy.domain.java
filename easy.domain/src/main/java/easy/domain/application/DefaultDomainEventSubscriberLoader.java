package easy.domain.application;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import easy.domain.event.ISubscriber;
import easy.domain.utils.JarPathHelper;

public class DefaultDomainEventSubscriberLoader implements
		IDomainEventSubscriberLoader {

	private ClassLoader cl = getClass().getClassLoader();

	private String[] excluecMethths = { "notifyAll", "notify", "getClass",
			"hashCode", "equals", "wait", "toString" };

	@Override
	public HashMap<String, List<ISubscriber>> find(IApplication application) {

		Stream<Method> methods = Arrays.stream(
				application.getClass().getMethods()).filter(
				m -> Modifier.isPublic(m.getModifiers())
						&& !Modifier.isAbstract(m.getModifiers())
						&& !Arrays.stream(excluecMethths).anyMatch(
								s -> s.equals(m.getName())));

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
				return hashMap;
			}

			List<ISubscriber> subscribers = null;
			if (url.getProtocol().equals("file")) {
				subscribers = this.subscribersFromClasses(domainEventsPath
						.replace('/', '.').toLowerCase(), url);

			} else if (url.getProtocol().equals("jar")) {

				String jarfilePath = JarPathHelper.jarPath(url.getFile());
				subscribers = this.subscribersFromJar(jarfilePath,
						domainEventsPath.toLowerCase());
			}

			hashMap.put(m.getName(), subscribers);
		}

		return hashMap;
	}

	private List<ISubscriber> subscribersFromJar(String jarfile,
			String packageName) {

		ArrayList<ISubscriber> arrayList = new ArrayList<ISubscriber>();

		try (JarFile jar = new JarFile(new File(jarfile))) {

			Enumeration<JarEntry> entries = jar.entries();

			while (entries.hasMoreElements()) {
				JarEntry jarEntry = entries.nextElement();
				if (!jarEntry.getName().startsWith(packageName)
						|| !jarEntry.getName().endsWith(".class")) {
					continue;
				}

				String classpath = StringUtils.stripEnd(jarEntry.getName()
						.replace('/', '.'), ".class");

				ISubscriber subscriber = this.subscriberObject(classpath);
				if (subscriber != null) {
					arrayList.add(subscriber);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrayList;
	}

	private List<ISubscriber> subscribersFromClasses(String packageName, URL url) {

		File file = new File(url.getFile());
		String[] files = file.list();

		return Arrays.stream(files)
				.map(m -> packageName + m.substring(0, m.length() - 6))
				.map(this::subscriberObject).filter(m -> m != null)
				.collect(Collectors.toList());
	}

	private ISubscriber subscriberObject(String classpath) {
		try {
			Class<?> cls = Class.forName(classpath);
			Object o = cls.newInstance();
			if (o instanceof ISubscriber) {
				return (ISubscriber) o;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private URL getUrls(String path) {
		return cl.getResource(path);
	}
}
