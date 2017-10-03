/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.convert.converter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * StringToIntegerConverterTest.
 *
 * @author aschaefer, Namics AG
 * @since 17.04.15 09:50
 */
public class StringToIntegerConverterTest {

	protected final StringToIntegerConverter converter = new StringToIntegerConverter();

	@Test
	public void testConvertNull() throws Exception {
		assertThat(converter.convert(null), is(nullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertNumberFormat() throws Exception {
		converter.convert("Hello");
	}

	@Test
	public void testConvert() throws Exception {
		assertThat(converter.convert("4711"), is(equalTo(4711)));
	}
}