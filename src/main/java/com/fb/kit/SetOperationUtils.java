package com.fb.kit;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

//                         _ooOoo_  
//                        o8888888o  
//                        88" . "88  
//                        (| -_- |)  
//                         O\ = /O  
//                     ____/`---'\____  
//                   .   ' \\| |// `.  
//                    / \\||| : |||// \  
//                  / _||||| -:- |||||- \  
//                    | | \\\ - /// | |  
//                  | \_| ''\---/'' | |  
//                   \ .-\__ `-` ___/-. /  
//                ___`. .' /--.--\ `. . __  
//             ."" '< `.___\_<|>_/___.' >'"".  
//            | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//              \ \ `-. \_ __\ /__ _/ .-` / /  
//      ======`-.____`-.___\_____/___.-`____.-'======  
//                         `=---='  
//
//      .............................................  
//               佛祖保佑             永无BUG 
//       佛曰:  
//               写字楼里写字间，写字间里程序员；  
//               程序人员写程序，又拿程序换酒钱。  
//               酒醒只在网上坐，酒醉还来网下眠；  
//               酒醉酒醒日复日，网上网下年复年。  
//               但愿老死电脑间，不愿鞠躬老板前；  
//               奔驰宝马贵者趣，公交自行程序员。  
//               别人笑我忒疯癫，我笑自己命太贱；  
//               不见满街漂亮妹，哪个归得程序员？ 

/**
 * 集合[并集、交集、差集运算]
 * @date 2016年10月12日 上午9:54:12
 */
public class SetOperationUtils {

	/**
	 * 并集（set唯一性）
	 * @author sun
	 * @date 2016年10月12日 上午9:54:08
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static Object[] union(Object[] arr1, Object[] arr2) {
		Set<Object> hs = new HashSet<Object>();
		for (Object str : arr1) {
			hs.add(str);
		}
		for (Object str : arr2) {
			hs.add(str);
		}
		Object[] result = {};
		return hs.toArray(result);
	}

	/**
	 * 交集(注意结果集中若使用LinkedList添加，则需要判断是否包含该元素，否则其中会包含重复的元素)
	 * @author sun
	 * @date 2016年10月12日 上午9:52:06
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static Object[] intersect(Object[] arr1, Object[] arr2) {
		List<Object> l = new LinkedList<Object>();
		Set<Object> common = new HashSet<Object>();
		for (Object str : arr1) {
			if (!l.contains(str)) {
				l.add(str);
			}
		}
		for (Object str : arr2) {
			if (l.contains(str)) {
				common.add(str);
			}
		}
		Object[] result = {};
		return common.toArray(result);
	}

	/**
	 * 例如：<br/>
	 * 数组1[2,3,4] 和<br/>
	 * 数组2[1,2,3]<br/>
	 * 数组相差 4， 也就是说应该去掉4.<br/>
	 * 
	 * 谁在前表示谁是谁的差集<br/>
	 * 
	 * 求两个数组的差集<br/>
	 * @author sun
	 * @date 2016年10月12日 上午9:50:38
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static Object[] substract(Object[] arr1, Object[] arr2) {
		LinkedList<Object> list = new LinkedList<Object>();
		for (Object str : arr1) {
			if (!list.contains(str)) {
				list.add(str);
			}
		}
		for (Object str : arr2) {
			if (list.contains(str)) {
				list.remove(str);
			}
		}
		Object[] result = {};
		return list.toArray(result);
	}
	
}
