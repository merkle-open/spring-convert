/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.convert.converter;

import com.namics.oss.spring.convert.CollectionConverter;

/**
 * Simple converter for null save toString conversion.
 *
 * @author aschaefer, Namics AG
 * @since 17.04.15 09:40
 */
public class ObjectToStringConverter<T> extends CollectionConverter<T, String> {
	public String convert(Object source, String target) {
		if (source == null) {
			return null;
		}
		return source.toString();
	}
}
