package easy.domain.application;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultReturnTransformerLoader implements IReturnTransformerLoader {

	private ClassLoader cl = getClass().getClassLoader();

	@Override
	public HashMap<String, List<IReturnTransformer>> find(
			IApplication application) {

		List<Method> methods = Arrays
				.stream(application.getClass().getDeclaredMethods())
				.filter(m -> m.getReturnType() == IReturn.class
						&& m.getModifiers() == 1).collect(Collectors.toList());

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

			List<IReturnTransformer> transformers = this.getTransformer(
					transformerPath.replace('/', '.').toLowerCase(), url);
			hashMap.put(m.getName(), transformers);
		}
		return hashMap;
	}

	private List<IReturnTransformer> getTransformer(String packageName, URL url) {

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
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					return null;

				}).filter(m -> m != null)
				.sorted((a, b) -> b.getOrder() - a.getOrder())
				.collect(Collectors.toList());
	}

}
