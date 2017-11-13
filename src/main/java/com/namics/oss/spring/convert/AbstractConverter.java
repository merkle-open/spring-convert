/*
 * Copyright 2000-2012 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.convert;

import com.namics.oss.spring.convert.util.BeanUtils;

/**
 * AbstractConverter class to convert Beans. Spring {@link BeanUtils} are used internally. Override the
 * getTagetInstance() method. Return a new
 * Instance of the Target class. Potentially override the methods convertInternal() and getIgnoreProperties() for fancy
 * implementations.
 *
 * @param <Source>
 * @param <Target>
 * @author boris burgstaller, Namics AG
 * @author bhelfenberger, Namics AG
 * @since Jun 5, 2013
 */
public abstract class AbstractConverter<Source, Target> extends CollectionConverter<Source, Target> {

	public Target convert(Source source) {
		if (source == null) {
			return null;
		}
		Target target = this.getTargetInstance(source);
		return convert(source, target);
	}

	public Target convert(Source source, Target target) {
		if (source == null) {
			return null;
		}
		BeanUtils.copyAndConvertProperties(source, target, this.getIgnoreProperties());
		this.convertInternal(source, target);
		return target;
	}

	/**
	 * Override this method to manually convert fields.
	 *
	 * @param source source object
	 * @param target target object
	 */
	protected void convertInternal(Source source, Target target) {
	}

	/**
	 * Override this method to ignore properties on the Objects.
	 *
	 * @return array of properties to be ignored
	 */
	protected String[] getIgnoreProperties() {
		return null;
	}

	/**
	 * override this method when you need information of the source object to create a specific target instance.
	 *
	 * @param source Source object.
	 * @return An Instance of the Target class. usually new Bla().
	 */
	protected Target getTargetInstance(Source source) {
		return this.getTargetInstance();
	}

	/**
	 * Implement this to specify how to create target objects.
	 *
	 * @return An Instance of the Target class. usually new Bla().
	 */
	protected abstract Target getTargetInstance();

}
