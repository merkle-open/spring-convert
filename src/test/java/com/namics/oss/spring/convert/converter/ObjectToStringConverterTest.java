/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.convert.converter;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * ObjectToStringConverterTest.
 *
 * @author aschaefer, Namics AG
 * @since 17.04.15 09:45
 */
public class ObjectToStringConverterTest {

	protected ObjectToStringConverter<Object> converter = new ObjectToStringConverter<Object>();

	@Test
	public void testConvertNull() throws Exception {
		assertThat(converter.convert(null), is(nullValue()));
	}

	@Test
	public void testConvertString() throws Exception {
		assertThat(converter.convert("String"), is(equalTo("String")));
	}

	@Test
	public void testConvertLong() throws Exception {
		assertThat(converter.convert(1L), is(equalTo("1")));
	}

	@Test
	public void testConvertBigDecimal() throws Exception {
		assertThat(converter.convert(new BigDecimal("12.78")), is(equalTo("12.78")));
	}
}