/*
 * Copyright 2000-2012 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.convert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

/**
 * AbstractConverterTest.
 *
 * @author aschaefer, Namics AG
 * @since CIPO 1.0 18.07.2012
 */
public class AbstractConverterTest {

	@Test
	public void testNull() {
		TestIn in = null;
		TestOut out = new TestConverter().convert(in);
		assertNull(out);
	}

	@Test
	public void testEmpty() {
		TestIn in = new TestIn();
		TestOut out = new TestConverter().convert(in);
		assertNotNull(out);
		assertNull(out.value);
		assertNull(out.getProperty());
	}

	@Test
	public void testFullData() {
		TestIn in = new TestIn();
		in.value = "test";
		in.setProperty("test");
		TestOut out = new TestConverter().convert(in);
		assertNotNull(out);
		assertNull(out.value);
		assertEquals("test", out.getProperty());
	}

	@Test
	public void testNullList() {
		List<TestIn> list = null;
		assertNull(new TestConverter().convertAll(list));
	}

	@Test
	public void testFullDataList() {
		List<TestIn> inList = new ArrayList<TestIn>();

		TestIn in = new TestIn();
		in.value = "test";
		in.setProperty("test");

		inList.add(in);

		in = new TestIn();
		in.value = "test";
		in.setProperty("test");

		inList.add(in);

		List<TestOut> outList = new TestConverter().convertAll(inList);
		assertThat(outList, hasSize(2));
		for (TestOut out : outList) {
			assertNotNull(out);
			assertNull(out.value);
			assertEquals("test", out.getProperty());
		}
	}

	static class TestConverter extends AbstractConverter<TestIn, TestOut> {
		@Override
		protected TestOut getTargetInstance() {
			return new TestOut();
		}

	}

	static class TestIn {
		String value;

		private String property;

		/**
		 * Getter for property. @return the property
		 */
		public String getProperty() {
			return this.property;
		}

		/**
		 * Setter for property. @param property the property to set
		 */
		public void setProperty(String property) {
			this.property = property;
		}

	}

	static class TestOut {
		String value;

		private String property;

		/**
		 * Getter for property. @return the property
		 */
		public String getProperty() {
			return this.property;
		}

		/**
		 * Setter for property. @param property the property to set
		 */
		public void setProperty(String property) {
			this.property = property;
		}
	}
}
