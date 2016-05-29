package cock.util;

import java.util.ArrayList;
import java.util.List;

/** 
 * @ClassName: RecursionUtil 
 * @Description: (通用递归树结构的方法) 
 * @author huangjp
 * @date 2014年12月19日 上午10:20:39 
 */
public class RecursionUtil {
	
	/**
	 * 通用递归树结构 ，调用方式例如
	 * List<T> newList = RecursionUtil.Recursion(oldList, "parentId", "id", "children");
	 * oldList -- 原始列表集合
	 * parentId -- 父标识字段名
	 * id -- 关联父标识值的字段名
	 * children -- 父亲孩子的集合字段名,用于存放其孩子
	 * 
	 * @param list 原始列表数据
	 * @param parentField //判断父子关系的关联字段名
	 * @param keyField //判断父子关系的主键字段名
	 * @param childrenField //需要放入的子列表字段名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> Recursion(List<T> list, 
			String parentField, String keyField, String childrenField,  T...parent) {
		T parentOrg = null;
		if(parent != null && parent.length > 0) {
			parentOrg = parent[0];
		}
		List<T> orgs = new ArrayList<T>();
		if(list != null && list.isEmpty()) return orgs;
		for(int i = list.size() - 1; i >= 0; i--) {
			T org = list.get(i);
			Object parentObject = ReflectUtil.getObjectByReflect(parentField, org);
			if(parent == null || parent.length == 0) {//先列出所有父亲
				if(parentObject == null || "".equals(parentObject)) {//父亲标识为null或""的，则删除
					parentOrg = list.remove(i);
					orgs.add(parentOrg);
				} else {//父亲标识不为null或""，则需要用其标识去找到其父亲对象
					parentOrg = ReflectUtil.getObjectById(list, keyField, parentObject);
					if(parentOrg == null) {//若找不到父亲对象说明他自己就是顶层父亲
						parentOrg = list.remove(i);
						orgs.add(parentOrg);
					}
				}
			} else {//有了父亲就找孩子
				Object sonOrg = ReflectUtil.getObjectByReflect(keyField, parentOrg);
				if(sonOrg.equals(parentObject)) {
					orgs.add(list.remove(i));
				}
			}
		}
		//为orgs父亲集合找孩子
		for(int i = 0; i < orgs.size(); i++) {
			T org = orgs.get(i);
			List<T> son = Recursion(list, parentField, keyField, childrenField, org);
			if(son != null && !son.isEmpty()) {
				ReflectUtil.setObjectByReflect(childrenField, son, org, List.class);
			}
		}
		
		if(list.size() == 0) {//只有list == null时才算找完，则返回
			return orgs;
		} else {//如果还有未找完的数据
			if(parent == null) {
				//不找孩子进来的，则是需要直接返回的顶层对象，则将未清的数据全部加入顶层父亲对象集合中
				orgs.addAll(list);
				return orgs;
			} else {//是找孩子进来的，则直接返回orgs
				return orgs;
			}
		}
	}
}
