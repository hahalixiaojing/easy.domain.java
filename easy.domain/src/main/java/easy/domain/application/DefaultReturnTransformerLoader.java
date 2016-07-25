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

import org.apache.commons.lang3.StringUtils;

public class DefaultReturnTransformerLoader implements IReturnTransformerLoader {

	private ClassLoader cl = getClass().getClassLoader();

	@Override
	public HashMap<String, List<IReturnTransformer>> find(
			IApplication application) {

		List<Method> methods = Arrays
				.stream(application.getClass().getMethods())
				.filter(m -> m.getReturnType() == IReturn.class
						&& Modifier.isPublic(m.getModifiers())
						&& !Modifier.isAbstract(m.getModifiers()))
				.collect(Collectors.toList());

		String packagename = application.getClass().getName()
				.substring(0, application.getClass().getName().length() - 11);

		String path = packagename.replace('.', '/');

		HashMap<String, List<IReturnTransformer>> hashMap = new HashMap<String, List<IReturnTransformer>>();

		for (Method m : methods) {
			String transformerPath = path + "/" + m.getName();

			URL url = cl.getResource(transformerPath.toLowerCase());
			if (url == null) {
				throw new NullPointerException(url + " is not found");
			}

			List<IReturnTransformer> transformers = null;
			String protocol = url.getProtocol();
			if (protocol.equals("file")) {
				transformers = this.transformerFromClasses(transformerPath
						.replace('/', '.').toLowerCase(), url);
			} else if (protocol.equals("jar")) {

				String jarfilePath = this.jarFilePath(url.getFile());

				transformers = this.transformerFromJar(jarfilePath,
						path.toLowerCase());

				System.out.println("name=" + m.getName());
				System.out.println("size=" + transformers.size());
			}

			hashMap.put(m.getName(), transformers);
		}
		return hashMap;
	}

	private String jarFilePath(String fullPathString) {
		String[] lines = fullPathString.split("!");
		String line0 = lines[0];
		String line = StringUtils.stripStart(line0, "file:/");

		return line.trim();
	}

	private List<IReturnTransformer> transformerFromJar(String jarfile,
			String packageName) {
		System.out.println("packname=" + packageName);
		ArrayList<IReturnTransformer> arrayList = new ArrayList<IReturnTransformer>();

		try (JarFile jar = new JarFile(new File(jarfile))) {

			Enumeration<JarEntry> entries = jar.entries();

			while (entries.hasMoreElements()) {
				JarEntry jarEntry = entries.nextElement();
				System.out.println(jarEntry.getName());
				if (!jarEntry.getName().startsWith(packageName)
						|| !jarEntry.getName().endsWith(".class")) {
					continue;
				}
				Class<?> cls;
				try {
					String classpath = StringUtils.stripEnd(jarEntry.getName()
							.replace('/', '.'), ".class");

					cls = Class.forName(classpath);
					if (Modifier.isAbstract(cls.getModifiers())) {
						continue;
					}
					Object o = cls.newInstance();
					if (o instanceof IReturnTransformer) {
						IReturnTransformer trans = (IReturnTransformer) cls
								.newInstance();
						arrayList.add(trans);
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

	private List<IReturnTransformer> transformerFromClasses(String packageName,
			URL url) {

		File file = new File(url.getFile());
		String[] files = file.list();

		return Arrays
				.stream(files)
				.map(m -> packageName + "." + m.substring(0, m.length() - 6))
				.map(m -> {
					Class<?> cls;
					try {
						cls = Class.forName(m);
						if (Modifier.isAbstract(cls.getModifiers())) {
							return null;
						}
						Object o = cls.newInstance();
						if (o instanceof IReturnTransformer) {
							IReturnTransformer trans = (IReturnTransformer) cls
									.newInstance();
							return trans;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;

				}).filter(m -> m != null)
				.sorted((a, b) -> b.getOrder() - a.getOrder())
				.collect(Collectors.toList());
	}

}
