package com.namics.oss.spring.convert.util;

import org.joda.time.DateTime;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BeanUtils.
 *
 * @author aschaefer, Namics AG
 * @author bhelfenberger, Namics AG
 * @since Jun 5, 2013
 */
public abstract class BeanUtils extends org.springframework.beans.BeanUtils {

	/**
	 * Copy the property values of the given source bean into the given target bean where the types are the same. And
	 * convert java.util.Date to org.joda.time.DateTime and vice versa.
	 *
	 * @param source the source bean
	 * @param target the target bean
	 */
	public static void copyAndConvertProperties(Object source, Object target) {
		copyAndConvertProperties(source, target, null);
	}

	/**
	 * Copy the property values of the given source bean into the given target bean where the types are the same. And
	 * convert java.util.Date to org.joda.time.DateTime and vice versa.
	 *
	 * @param source           the source bean
	 * @param target           the target bean
	 * @param ignoreProperties array of property names to ignore
	 * @throws BeansException if the copying failed
	 */
	public static void copyAndConvertProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {
		copyAndConvertProperties(source, target, ignoreProperties, converters);
	}

	/**
	 * Copy the property values of the given source bean into the given target bean where the types are the same. And
	 * convert java.util.Date to org.joda.time.DateTime and vice versa.
	 *
	 * @param source           the source bean
	 * @param target           the target bean
	 * @param ignoreProperties array of property names to ignore
	 * @param converters       collection of convertes to be used for conversion of incompatible types
	 * @throws BeansException if the copying failed
	 */
	// @checkstyle:off
	@SuppressWarnings("unchecked")
	protected static void copyAndConvertProperties(Object source, Object target, String[] ignoreProperties,
	                                               // @checkstyle:on
	                                               Map<Class<?>, Map<Class<?>, Converter<?, ?>>> converters) throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Assert.notNull(converters, "Converters must not be null, check your custom implementation!");

		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
		List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd2 : targetPds) {
			PropertyDescriptor targetPd = targetPd2;
			if (targetPd.getWriteMethod() != null && (ignoreProperties == null || !ignoreList.contains(targetPd.getName()))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Method readMethodTarget = targetPd.getReadMethod();
						if (readMethod.getReturnType().equals(readMethodTarget.getReturnType())) {
							Object value = readMethod.invoke(source, new Object[0]);
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, new Object[] { value });
						} else {
							@SuppressWarnings("rawtypes")
							Converter converter = getConverter(converters, readMethod.getReturnType(), readMethodTarget.getReturnType());
							if (converter != null) {
								Object value = readMethod.invoke(source, new Object[0]);
								Method writeMethod = targetPd.getWriteMethod();
								if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(target, new Object[] { converter.convert(value) });
							}
						}
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}

	private static Converter<?, ?> getConverter(Map<Class<?>, Map<Class<?>, Converter<?, ?>>> converters, Class<?> returnTypeFrom,
	                                            Class<?> returnTypeTo) {
		if (converters.containsKey(returnTypeTo)) {
			return converters.get(returnTypeTo).get(returnTypeFrom);
		}
		return null;
	}

	/**
	 * Converter class defines how a custom converter looks like.
	 */
	protected static abstract class Converter<FromClass, ToClass> {

		public abstract ToClass convert(FromClass from);
	}

	/**
	 * Use this method to register new Converters for your sub class.
	 * Keep in mind: also add the converters of the super class if you need them!
	 *
	 * @param converters your sub class's converters collection!
	 * @param converter  converter you want to add.
	 */
	protected final static void addConverter(Map<Class<?>, Map<Class<?>, Converter<?, ?>>> converters, Converter<?, ?> converter) {
		ParameterizedType parameterizedType = (ParameterizedType) converter.getClass().getGenericSuperclass();
		Type from = parameterizedType.getActualTypeArguments()[0];
		Type to = parameterizedType.getActualTypeArguments()[1];

		Map<Class<?>, Converter<?, ?>> map = converters.get(to);
		if (map == null) {
			map = new HashMap<Class<?>, Converter<?, ?>>();
		}
		map.put((Class<?>) from, converter);
		converters.put((Class<?>) to, map);
	}

	/**
	 * Call this method in sub types to get the initial set of converters.
	 *
	 * @return set of converters
	 */
	protected static Map<Class<?>, Map<Class<?>, Converter<?, ?>>> getConverters() {
		return converters;
	}

	/**
	 * Converters of this class.
	 */
	private static Map<Class<?>, Map<Class<?>, Converter<?, ?>>> converters = new HashMap<Class<?>, Map<Class<?>, Converter<?, ?>>>();

	static {
		addConverter(converters, new DateToDateTimeConverter());
		addConverter(converters, new Converter<DateTime, Date>() {

			@Override
			public Date convert(DateTime from) {
				if (from == null) {
					return null;
				}
				return from.toDate();
			}
		});
		addConverter(converters, new Converter<BigInteger, String>() {

			@Override
			public String convert(BigInteger from) {
				if (from == null) {
					return null;
				}
				return "" + from;
			}
		});
		addConverter(converters, new Converter<String, BigInteger>() {

			@Override
			public BigInteger convert(String from) {
				if (from == null) {
					return null;
				}
				return BigInteger.valueOf(Long.parseLong(from));
			}
		});
		addConverter(converters, new Converter<Double, BigDecimal>() {

			@Override
			public BigDecimal convert(Double from) {
				if (from == null) {
					return null;
				}
				return BigDecimal.valueOf(from);
			}
		});
		addConverter(converters, new Converter<Long, String>() {

			@Override
			public String convert(Long from) {
				if (from == null) {
					return null;
				}
				return from.toString();
			}
		});
		addConverter(converters, new Converter<Integer, BigInteger>() {

			@Override
			public BigInteger convert(Integer from) {
				if (from == null) {
					return null;
				}
				return BigInteger.valueOf(from.longValue());
			}
		});

		addConverter(converters, new Converter<BigInteger, Integer>() {

			@Override
			public Integer convert(BigInteger from) {
				if (from == null) {
					return null;
				}
				return Integer.valueOf(from.intValue());
			}
		});
	}

	private static class DateToDateTimeConverter extends Converter<Date, DateTime> {

		@Override
		public DateTime convert(Date from) {
			if (from == null) {
				return null;
			}
			return new DateTime(from);
		}
	}
}
