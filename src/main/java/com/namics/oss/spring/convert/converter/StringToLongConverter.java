/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.convert.converter;

import com.namics.oss.spring.convert.CollectionConverter;

/**
 * StringToLongConverter.
 *
 * @author aschaefer, Namics AG
 * @since 17.04.15 09:33
 */
public class StringToLongConverter extends CollectionConverter<String, Long> {
	public Long convert(String source, Long target) {
		if (source == null) {
			return null;
		}
		return Long.parseLong(source);
	}
}
