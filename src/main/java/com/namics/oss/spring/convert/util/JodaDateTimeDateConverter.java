/*
 * Copyright 2000-2018 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.convert.util;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * JodaDateTimeDateConverter.
 *
 * @author aschaefer, Namics AG
 * @since 23.02.18 16:42
 */
class JodaDateTimeDateConverter extends BeanUtils.Converter<DateTime, Date> {

	@Override
	public Date convert(DateTime from) {
		if (from == null) {
			return null;
		}
		return from.toDate();
	}
}
