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

				String jarfilePath = this.jarFilePath(url.getFile());
				subscribers = this.subscribersFromJar(jarfilePath,
						domainEventsPath.toLowerCase());
			}

			hashMap.put(m.getName(), subscribers);
		}

		return hashMap;
	}

	private String jarFilePath(String fullPathString) {
		String[] lines = fullPathString.split("!");
		String line0 = lines[0];
		String line = StringUtils.stripStart(line0, "file:/");

		return line.trim();
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
				Class<?> cls;
				try {
					String classpath = StringUtils.stripEnd(jarEntry.getName()
							.replace('/', '.'), ".class");

					cls = Class.forName(classpath);
					Object o = cls.newInstance();
					if (o instanceof ISubscriber) {
						System.out.println("is subscriber");
						ISubscriber sub = (ISubscriber) cls.newInstance();
						arrayList.add(sub);
					}
				} catch (Exception e) {
					e.printStackTrace();
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
				.map(m -> {
					Class<?> cls;
					try {
						cls = Class.forName(m);

						Object o = cls.newInstance();
						if (o instanceof ISubscriber) {
							ISubscriber sub = (ISubscriber) cls.newInstance();
							return sub;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;

				}).filter(m -> m != null).collect(Collectors.toList());
	}

	private URL getUrls(String path) {
		return cl.getResource(path);
	}
}
