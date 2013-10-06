package com.htsoft.est.util;

public class StringUtil {

	/**
	 * 简单的把Object对象装换为String对象 如果输入参数为null则返回长度为0的字符串
	 * 如果输入参数为String，返回则把该String的两端空 字符串去掉后的字符串
	 */
	public static String o2s(Object o) {
		if (null == o) {
			return "";
		}
		if (o instanceof String) {
			return ((String) o).trim();
		}
		return o.toString().trim();
	}

	/**
	 * 把对象转换成int，失败返回0
	 * 
	 * @param o
	 *            对象
	 * @return int
	 */
	public static int o2i(Object o) {
		int r = 0;
		try {
			r = Integer.parseInt(o2s(o));
		} catch (Throwable e) {
		}
		return r;
	}
	
	/**
	 * 把对象转换成Short，失败返回0
	 * 
	 * @param o
	 *            对象
	 * @return int
	 */
	public static Short o2st(Object o) {
		Short r = 0;
		try {
			r = Short.parseShort(o2s(o));
		} catch (Throwable e) {
		}
		return r;
	}

	/**
	 * 把对象转换成long，失败返回0
	 * 
	 * @param o
	 *            对象
	 * @return int
	 */
	public static long o2l(Object o) {
		long r = 0;
		try {
			r = Long.parseLong(o2s(o));
		} catch (Throwable e) {
		}
		return r;
	}

	/**
	 * 把对象转换成double，失败返回0
	 * 
	 * @param o
	 *            对象
	 * @return int
	 */
	public static double o2d(Object o) {
		double r = 0;
		try {
			r = Double.parseDouble(o2s(o));
		} catch (Throwable e) {
		}
		return r;
	}
	
	public static String s2s(String s) {
		if(s == null) {
			return "";
		} else {
			return s;
		}
	}
}
