package easy.domain.application;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import easy.domain.event.IDomainEvent;
import easy.domain.utils.JarPathHelper;

public class DefaultDomainEventLoader implements IDomainEventLoader {
	private ClassLoader cl = getClass().getClassLoader();
	private String[] excluecMethths = { "notifyAll", "notify", "getClass",
			"hashCode", "equals", "wait", "toString" };

	@Override
	public List<Class<?>> load(IApplication application) {

		Stream<Method> methods = Arrays.stream(
				application.getClass().getMethods()).filter(
				m -> Modifier.isPublic(m.getModifiers())
						&& !Modifier.isAbstract(m.getModifiers())
						&& !Arrays.stream(excluecMethths).anyMatch(
								s -> s.equals(m.getName())));

		String packagename = application.getClass().getName()
				.substring(0, application.getClass().getName().length() - 11);

		String path = packagename.replace('.', '/');

		List<Method> methodList = methods.collect(Collectors.toList());

		for (Method m : methodList) {
			String domainEventsPath = path + "/" + m.getName()
					+ "domainevents/";

			URL url = this.getUrls(domainEventsPath.toLowerCase());

			if (url == null) {
				return new ArrayList<Class<?>>(0);
			}

			if (url.getProtocol().equals("file")) {
				return this.subscribersFromClasses(
						domainEventsPath.replace('/', '.').toLowerCase(), url);

			} else if (url.getProtocol().equals("jar")) {
				String jarfilePath = JarPathHelper.jarPath(url.getFile());
				return this.subscribersFromJar(jarfilePath,
						domainEventsPath.toLowerCase());
			}
		}
		return new ArrayList<Class<?>>(0);
	}

	private List<Class<?>> subscribersFromJar(String jarfile, String packageName) {

		ArrayList<Class<?>> arrayList = new ArrayList<>();

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

				Class<?> cls = Class.forName(classpath);

				if (IDomainEvent.class.isAssignableFrom(cls)) {
					arrayList.add(cls);
				}
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return arrayList;
	}

	private List<Class<?>> subscribersFromClasses(String packageName, URL url) {

		File file = new File(url.getFile());
		String[] files = file.list();

		return Arrays
				.stream(files)
				.map(m -> packageName + m.substring(0, m.length() - 6))
				.map(m -> {
					try {
						return Class.forName(m);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;

				})
				.filter(m -> m != null
						&& IDomainEvent.class.isAssignableFrom(m))
				.collect(Collectors.toList());
	}

	private URL getUrls(String path) {
		return cl.getResource(path);
	}
}
