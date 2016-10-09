package easy.domain.application;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;

import easy.domain.utils.JarPathHelper;

public class DefaultReturnTransformerLoader implements IReturnTransformerLoader {
	private ClassLoader cl = getClass().getClassLoader();

	@Override
	public HashMap<String, List<IReturnTransformer>> find(
			IApplication application) {

		Method[] methods = application.getClass().getMethods();

		List<Method> rMethods = new ArrayList<Method>();
		for (Method m : methods) {

			if (Modifier.isPublic(m.getModifiers())
					&& !Modifier.isAbstract(m.getModifiers())
					&& IReturn.class.isAssignableFrom(m.getReturnType())) {

				rMethods.add(m);
			}
		}

		String packagename = application.getClass().getName()
				.substring(0, application.getClass().getName().lastIndexOf('.'));

		String path = packagename.replace('.', '/');

		HashMap<String, List<IReturnTransformer>> hashMap = new HashMap<>();

		for (Method m : rMethods) {
			System.out.println(m.getName());
			String transformerPath = path + "/" + m.getName();

			URL url = cl.getResource(transformerPath.toLowerCase());
			if (url == null) {
				hashMap.put(m.getName(), new ArrayList<IReturnTransformer>(0));
				continue;
			}

			List<IReturnTransformer> transformers = null;
			String protocol = url.getProtocol();
			if (protocol.equals("file")) {
				transformers = this.transformerFromClasses(transformerPath
						.replace('/', '.').toLowerCase(), url);
			} else if (protocol.equals("jar")) {

				String jarfilePath = JarPathHelper.jarPath(url.getFile());

				transformers = this.transformerFromJar(jarfilePath,
						transformerPath.toLowerCase());
			}
			hashMap.put(m.getName(), transformers);
		}
		return hashMap;
	}

	private List<IReturnTransformer> transformerFromJar(String jarfile,
			String packageName) {

		ArrayList<IReturnTransformer> arrayList = new ArrayList<>();

		try (JarFile jar = new JarFile(new File(jarfile))) {

			Enumeration<JarEntry> entries = jar.entries();

			while (entries.hasMoreElements()) {

				String jarName = entries.nextElement().getName();

				if (!jarName.startsWith(packageName)
						|| !jarName.endsWith(".class")) {
					continue;
				}
				System.out.println(jarName);
				String classpath = StringUtils.remove(
						jarName.replace('/', '.'), ".class");
				System.out.println(classpath);
				IReturnTransformer returnTransformer = this
						.returnTransformer(classpath);

				if (returnTransformer != null) {
					arrayList.add(returnTransformer);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrayList;
	}

	private IReturnTransformer returnTransformer(String classpath) {
		Class<?> cls;
		try {
			System.out.println(classpath);
			cls = Class.forName(classpath);

			if (Modifier.isAbstract(cls.getModifiers())) {
				return null;
			}

			boolean result = IReturnTransformer.class.isAssignableFrom(cls);
			if (result) {
				return (IReturnTransformer) cls.newInstance();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	private List<IReturnTransformer> transformerFromClasses(String packageName,
			URL url) {

		File file = new File(url.getFile());
		String[] files = file.list();

		List<IReturnTransformer> transformers = new ArrayList<>();
		for (String f : files) {

			String classpath = packageName + "."
					+ f.substring(0, f.length() - 6);

			IReturnTransformer t = this.returnTransformer(classpath);
			if (t != null) {
				transformers.add(t);
			}
		}

		Collections.sort(transformers, new Comparator<IReturnTransformer>() {
			@Override
			public int compare(IReturnTransformer a, IReturnTransformer b) {
				return b.getOrder() - a.getOrder();
			}
		});
		return transformers;
	}
}
