/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.convert.converter;

import com.namics.oss.spring.convert.CollectionConverter;

import java.math.BigDecimal;

/**
 * StringToBigDecimalConverter.
 *
 * @author aschaefer, Namics AG
 * @since 17.04.15 09:33
 */
public class StringToBigDecimalConverter extends CollectionConverter<String, BigDecimal> {
	public BigDecimal convert(String source, BigDecimal target) {
		if (source == null) {
			return null;
		}
		return new BigDecimal(source);
	}
}
