package cock.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class PrivilegeReader {
	public static List<String> getRootPaths(String path) {
		try {
			FileInputStream fis = new FileInputStream(path + "/.classpath");
			List<String> paths = toModeulesList(fis);
			return paths;
		} catch (FileNotFoundException e) {
		}
		return new ArrayList<String>();
	}

	private static List<String> toModeulesList(InputStream xml) {
		List<String> paths = new ArrayList<String>();
		SAXReader sax = new SAXReader();
		try {
			Document doc = sax.read(xml);
			Element root = doc.getRootElement();

			@SuppressWarnings("unchecked")
			List<Element> list = root.elements("classpathentry");

			for (Element ele : list) {
				String kind = ele.attributeValue("kind");
				String path = ele.attributeValue("path");
				if (!"src".equals(kind))
					continue;
				paths.add(path);
			}
		} catch (DocumentException e) {
			return new ArrayList<String>();
		}
		return paths;
	}
}