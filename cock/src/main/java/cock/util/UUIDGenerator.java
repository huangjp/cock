/*
 * 文件名：UUIDGenerator.java	 
 * 时     间：下午3:00:19
 * 作     者：Administrator       
 * 版     权：2014-2022  智慧园区, 公司保留所有权利.
 * 联     系：www.szyungu.com
 */
package cock.util;

import java.util.UUID;

/**
 * @ClassName: UUIDGenerator
 * @描述: UUID
 * @author Administrator
 * @date 2014年7月29日 下午3:00:19
 */
public class UUIDGenerator {
	public UUIDGenerator() {
	}

	/**
	 * 获得一个UUID
	 * 
	 * @return String UUID
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.replace("-", "");
	}

	/**
	 * 获得指定数目的UUID
	 * 
	 * @param number
	 *            int 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}
}
