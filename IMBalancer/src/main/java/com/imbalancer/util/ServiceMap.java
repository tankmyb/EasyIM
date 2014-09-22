package com.imbalancer.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imbalancer.annotation.ExecuteService;
import com.imbalancer.annotation.Resource;
import com.imbalancer.service.IService;

public class ServiceMap {

	public static Map<Integer, IService> serviceMap = new HashMap<Integer, IService>();

	@SuppressWarnings("rawtypes")
	public static void initService() {
		List<Class> list;
		try {
			list = ClassUtils.getAllClassByInterface(IService.class);
			for (Class c : list) {
				Object bean = putService(c);
				injectResource(bean);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		}

	}

	public static IService getService(int key) {
		return serviceMap.get(key);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static IService putService(Class c) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		ExecuteService myClassAnnotation = (ExecuteService) c
				.getAnnotation(ExecuteService.class);
		int key = myClassAnnotation.value();
		// 如果MAP中有，则直接从取出，不用初始化了
		if (serviceMap.containsKey(key)) {
			throw new RuntimeException("key:" + key
					+ " had exist,exist service is "
					+ serviceMap.get(key).getClass() + ",cur service is:" + c);
		} else {
			IService service = (IService) Class.forName(c.getName())
					.newInstance();
			// 放到MAP中
			serviceMap.put(key, service);
			return service;
		}

	}

	/**
	 * 注入资源
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void injectResource(Object bean)
			throws IllegalArgumentException, IllegalAccessException {
		// 按字段注入
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			// 如果注解存在
			if (field.isAnnotationPresent(Resource.class)) {
				// 取得注解
				Resource resource = field.getAnnotation(Resource.class);
				if (resource != null) {
					String key = field.getType().getName();
					Object value = ResourceMap.getResource(key);
					if (value == null) {
						throw new RuntimeException(key
								+ " resource object is not exist");
					}
					field.setAccessible(true);
					field.set(bean, value);
				}

			}
		}
	}

	public static Collection<IService> getValues() {
		return serviceMap.values();
	}
}
