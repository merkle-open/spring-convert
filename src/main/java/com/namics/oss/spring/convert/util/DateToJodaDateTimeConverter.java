/*
 * Copyright 2000-2018 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.convert.util;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * DateToDateTimeConverter.
 *
 * @author aschaefer, Namics AG
 * @since 23.02.18 16:31
 */
public class DateToJodaDateTimeConverter extends BeanUtils.Converter<Date, DateTime> {

	@Override
	public DateTime convert(Date from) {
		if (from == null) {
			return null;
		}
		return new DateTime(from);
	}
}
