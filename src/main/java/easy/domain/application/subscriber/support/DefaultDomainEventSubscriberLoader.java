package easy.domain.application.subscriber.support;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import easy.domain.application.IApplication;
import easy.domain.application.subscriber.IDomainEventSubscriberLoader;
import org.apache.commons.lang3.ArrayUtils;
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

		Method[] methods = application.getClass().getMethods();

		List<Method> sMethods = new ArrayList<Method>();

		for (Method m : methods) {

			if (Modifier.isPublic(m.getModifiers())
					&& !Modifier.isAbstract(m.getModifiers())
					&& !ArrayUtils.contains(excluecMethths, m.getName())) {

				sMethods.add(m);
			}
		}
		String packagename = application
				.getClass()
				.getName()
				.substring(0, application.getClass().getName().lastIndexOf('.'));

		String path = packagename.replace('.', '/');

		HashMap<String, List<ISubscriber>> hashMap = new HashMap<>();

		for (Method m : sMethods) {

			String domainEventsPath = path + "/" + m.getName() + "events/";

			URL url = this.getUrls(domainEventsPath.toLowerCase());

			if (url == null) {
				hashMap.put(m.getName(), new ArrayList<ISubscriber>(0));
				continue;
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

				String classpath = StringUtils.remove(jarEntry.getName()
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

		List<ISubscriber> subscribers = new ArrayList<ISubscriber>();
		for (String f : files) {

			String classpath = packageName + f.substring(0, f.length() - 6);

			ISubscriber subscriber = this.subscriberObject(classpath);
			if (subscriber != null) {
				subscribers.add(subscriber);
			}
		}
		return subscribers;
	}

	private ISubscriber subscriberObject(String classpath) {
		try {
			Class<?> cls = Class.forName(classpath);

			boolean result = ISubscriber.class.isAssignableFrom(cls);
			if (result) {
				return (ISubscriber) cls.newInstance();
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
