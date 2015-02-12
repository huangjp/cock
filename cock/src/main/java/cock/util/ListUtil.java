/*
 * 文件名：ListUtil.java	 
 * 时     间：下午12:13:18
 * 作     者：huangjp       
 * 版     权： 2014-2022  智慧园区, 公司保留所有权利.
 * 联     系：www.szyungu.com
 */
package cock.util;

import java.util.ArrayList;
import java.util.List;

/** 
 * @ClassName: ListUtil 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author huangjp
 * @date 2014年12月20日 下午12:13:18 
 */
public class ListUtil {
	
	/**
	 * 为集合向上泛型
	 * @param lowper 原集合
	 * @param c 泛型为哪个类型的集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, R extends T> List<T> upwardGenericUpList(List<R> lowper, Class<T> c) {
		List<T> list = new ArrayList<T>();
		for(Object object : lowper) {
			list.add((T) object);
		}
		return list;
	}
	
	/**
	 * 为集合向下泛型
	 * @param lowper 原集合
	 * @param c 泛型为哪个类型的集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, R extends T> List<R> downGenericUpList(List<T> lowper, Class<R> c) {
		List<R> list = new ArrayList<R>();
		for(Object object : lowper) {
			list.add((R) object);
		}
		return list;
	}
	
	/**
	 * 从接口的一个子类向另一个子类过度
	 * @param lowper
	 * @param c
	 * @return
	 */
	public static <T, R extends T> List<R> sonToOtherSonWithList(List<T> lowper, Class<R> c) {
		List<R> list = new ArrayList<R>();
		for(Object object : lowper) {
			try {
				R r = c.newInstance();
				BeanUtils.copyProperties(object, r);
				list.add(r);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 数组转LIST
	 * @param array
	 * @return
	 */
	public static <T> List<T> arrayToList(T[] array) {
		List<T> list = new ArrayList<T>();
		for(T t : array) {
			list.add(t);
		}
		return list;
	}
	
	/**
	 * 常规转换LIST
	 * @param t
	 * @return
	 */
	public static <T> List<T> ObjectToList(T t) {
		List<T> list = new ArrayList<T>();
		list.add(t);
		return list;
	}
}
