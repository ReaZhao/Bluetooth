package com.reazha.svn_test.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Gson 解析工具类
 */
public class GsonUtils {
	public GsonUtils() {

	}

	@Override
	public String toString() {
		return super.toString();
	}

	// 使用Gson进行解析Person对象
	public static <T> T getPerson(String jsonString, Class<T> cls) throws Exception {
		T t = null;
		Gson gson = new Gson();
		t = gson.fromJson(jsonString, cls);
		return t;
	}

	// 使用Gson进行解析 List<Person>数组
	public static <T> List<T> getPersons(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();

		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
			}.getType());
		} catch (Exception e) {
			System.out.println("Gson解析错误");
		}
		return list;
	}

	public static ArrayList<String> getMapList(Map<String, String> map) {

		ArrayList<String> stringList = new ArrayList<String>();
		try {
			for (String key : map.keySet()) {
				System.out.println("解析mapok" + "key=" + key);
				String value = map.get(key);
				System.out.println("解析mapok" + "value=" + value);
				String string = key + ":" + value;
				stringList.add(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringList;

	}
}
