package cock.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName: JSONUtil
 * @Description: (这里用一句话描述这个类的作用)
 * @author huangjp
 * @date 2014年12月20日 上午11:18:23
 */
public class JSONUtil {

	private static ObjectMapper mapper;

	/**
	 * 将对象转换为json字符串
	 * 
	 * @param value
	 * @return
	 * @throws ParameterException
	 */
	public static String toJson(Object value) {
		String jsonString = "";
		try {
			jsonString = getObjectMapper().writeValueAsString(value);
		} catch (Exception e) {}
		return jsonString;
	}

	/**
	 * 将json字符串转换为对象
	 * 
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Object(String jsonString, Class<T> clazz) {
		T object = null;
		try {
			object = getObjectMapper().readValue(jsonString, clazz);
		} catch (Exception e) {
		}
		return object;
	}

	/**
	 * 将json字符串转换为List
	 * 
	 * @param jsonString  json字符串
	 * @param c 例如：String[].class
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T, R> List<R> jsonToList(String jsonString, Class<T> c, Class<R> r) {
		try {
			R[] items = (R[]) getObjectMapper().readValue(jsonString, c);
			return ListUtil.arrayToList(items);
		} catch (JsonParseException e) {
			throw new RuntimeException("", e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("", e);
		} catch (IOException e) {
			throw new RuntimeException("", e);
		}
	}

	private static ObjectMapper getObjectMapper() {
		if (mapper == null) {
			if (mapper == null) {
				mapper = new ObjectMapper();
			}
		}
		return mapper;
	}
}
