package com.phospher.DistributedTaskScheduler.util;

import java.lang.reflect.Constructor;

public class ObjectUtil {

	public static Object createInstance(String className) throws Exception {
		if(className == null || className.isEmpty()) {
			return null;
		}

		if(className.contains("$")) {
			String outterClassName = className.substring(0, className.indexOf('$'));
			Class outterClass = Class.forName(outterClassName);
			for(Class c : outterClass.getClasses()) {
				if(c.getName().equals(className)) {
					Constructor con = c.getConstructor(new Class[] { outterClass });
					return con.newInstance(new Object[] { outterClass.newInstance() });
				}
			}
			return null;
		} else {
			return Class.forName(className).newInstance();
		}
	}

}