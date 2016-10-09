package easy.domain.application;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import easy.domain.event.IDomainEvent;
import easy.domain.utils.JarPathHelper;

public class DefaultDomainEventLoader implements IDomainEventLoader {
	private ClassLoader cl = getClass().getClassLoader();
	private String[] excluecMethths = { "notifyAll", "notify", "getClass",
			"hashCode", "equals", "wait", "toString" };

	@Override
	public List<Class<?>> load(IApplication application)
			throws ClassNotFoundException {

		Method[] methods = application.getClass().getMethods();

		List<Method> acMethods = new ArrayList<Method>();
		for (Method m : methods) {

			if (Modifier.isPublic(m.getModifiers())
					&& !Modifier.isAbstract(m.getModifiers())
					&& !ArrayUtils.contains(excluecMethths, m.getName())) {

				acMethods.add(m);
			}
		}

		String packagename = application
				.getClass()
				.getName()
				.substring(0, application.getClass().getName().lastIndexOf('.'));

		String path = packagename.replace('.', '/');

		for (Method m : acMethods) {
			String domainEventsPath = path + "/" + m.getName()
					+ "events/";

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

				String classpath = StringUtils.remove(jarEntry.getName()
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

	private List<Class<?>> subscribersFromClasses(String packageName, URL url)
			throws ClassNotFoundException {

		File file = new File(url.getFile());
		String[] files = file.list();

		List<Class<?>> clsList = new ArrayList<Class<?>>();
		for (String f : files) {

			String classpath = packageName + f.substring(0, f.length() - 6);
			Class<?> cls = Class.forName(classpath);

			if (IDomainEvent.class.isAssignableFrom(cls)) {
				clsList.add(cls);
			}
		}
		return clsList;
	}

	private URL getUrls(String path) {
		return cl.getResource(path);
	}
}
