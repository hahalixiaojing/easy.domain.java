package easy.xml.test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.junit.Assert;
import org.junit.Test;
import org.dom4j.Node;

public class XmlTest {
	@SuppressWarnings("unchecked")
	@Test
	public void ReadXmlTest() {
		String dir = System.getProperty("user.dir");

		System.out.println(dir);

		Path path = Paths.get(dir, "repositories.xml");

		boolean exists = path.toFile().exists();
		System.out.print(exists);

		SAXReader xmlReader = new SAXReader();
		Document document = null;
		try {
			document = xmlReader.read(path.toFile());
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		Map<String, String> xmlMap = new HashMap<String, String>();
		xmlMap.put("abc", "http://www.39541240.com/repository");
		xmlMap.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");

		XPath xpath = DocumentHelper
				.createXPath("abc:repositories/abc:repository");
		xpath.setNamespaceURIs(xmlMap);

		List<Node> nodes = xpath.selectNodes(document);
		for (Node n : nodes) {

			Element e = (Element) n;
			String value = e.attribute("interface").getText();
			System.out.println(value);

		}

		Assert.assertTrue(nodes.size() > 0);

	}

	@Test
	public void BooleanUtilsTest() {
		boolean result = BooleanUtils.toBooleanObject("true", "true", "false",
				"null");
		Assert.assertTrue(result);
		result = BooleanUtils.toBooleanObject("false", "true", "false", "null");
		Assert.assertFalse(result);

		Boolean result2 = BooleanUtils.toBooleanObject("", "true", "false", "");
		Assert.assertNull(result2);

		result = Boolean.parseBoolean(null);
		Assert.assertFalse(result);

		result = Boolean.parseBoolean("");
		Assert.assertFalse(result);

		result = Boolean.parseBoolean("false");
		Assert.assertFalse(result);

		result = Boolean.parseBoolean("true");
		Assert.assertTrue(result);
	}
	@Test
	public void test2(){
		String path = "com/demo/application/product/models/ToViewProductModel.class";
		
		String a =path.replace('/', '.');
		String classpath = StringUtils.remove(a,".class");
		
		System.out.println(classpath);
	}

	interface IConst {

		List<String> get();
	}

	class ConstClass {
		public ConstClass(IConst constcls) {

		}
	}

	enum Planet {
		EARTH(1), MARS(2);

		private final int _id;

		Planet(int id) {
			this._id = id;
		}

		public int id() {
			return this._id;
		}

	}
}
