package com.namics.oss.spring.convert;

/**
 * Converter interface to be used whenever a converter is required.
 *
 * @param <Source>
 * @param <Target>
 * @author aschaefer, Namics AG
 * @author bhelfenberger, Namics AG
 * @since Jun 5, 2013
 */
public interface Converter<Source, Target> {

	/**
	 * Convert from Source to Target.
	 *
	 * @param source input object
	 * @return mapped target object, null for null
	 */
	Target convert(Source source);

	/**
	 * Merges the source into the target. If target is <code>null</code>, a new instance will be created.
	 * Otherwise the given target is used and the properties present in source are copied to it.
	 *
	 * @param source .
	 * @param target .
	 * @return .
	 */
	Target convert(Source source, Target target);

}