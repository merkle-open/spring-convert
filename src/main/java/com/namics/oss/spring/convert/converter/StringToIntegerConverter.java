/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.convert.converter;

import com.namics.oss.spring.convert.CollectionConverter;

/**
 * StringToIntegerConverter.
 *
 * @author aschaefer, Namics AG
 * @since 17.04.15 09:33
 */
public class StringToIntegerConverter extends CollectionConverter<String, Integer> {
	public Integer convert(String source, Integer target) {
		if (source == null) {
			return null;
		}
		return Integer.parseInt(source);
	}
}
