/*
 * Copyright 2000-2012 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.convert;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Extends Converter interface with a method to convert lists, to be used whenever a converter is required.
 *
 * @param <Source>
 * @param <Target>
 * @author aschaefer, Namics AG
 * @author bhelfenberger, Namics AG
 * @since Jun 5, 2013
 */
public abstract class CollectionConverter<Source, Target> implements Converter<Source, Target> {

	/**
	 * Override when needed.
	 * {@inheritDoc}
	 */
	public Target convert(Source source) {
		return convert(source, null);
	}

	/**
	 * Perform conversion on a list of objects.
	 *
	 * @param source list source objects
	 * @return list of target objects
	 */
	public List<Target> convertAll(List<? extends Source> source) {
		if (source == null) {
			return null;
		}

		List<Target> target = new ArrayList<Target>();
		for (Source element : source) {
			target.add(this.convert(element));
		}
		return target;
	}

	/**
	 * Perform conversion on a set of objects.
	 *
	 * @param source set source objects
	 * @return set of target objects
	 */
	public Set<Target> convertAll(Set<? extends Source> source) {
		if (source == null) {
			return null;
		}

		Set<Target> target = new LinkedHashSet<Target>();
		for (Source element : source) {
			target.add(this.convert(element));
		}
		return target;
	}

}