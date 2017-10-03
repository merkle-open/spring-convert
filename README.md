# Advanced Random Test Data Utils

System        | Status
--------------|------------------------------------------------        
CI master     | [![Build Status][travis-master]][travis-url]
CI develop    | [![Build Status][travis-develop]][travis-url]
Dependency    | [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.namics.oss.spring.convert/spring-convert/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.namics.oss.spring.convert/spring-convert)

Small but useful library providing converter APIs and default implementations for easy and reliable conversion of objects.

## Usage

### Maven Dependency (Latest Version in `pom.xml`):

	<dependency>
		<groupId>com.namics.oss.spring.convert</groupId>
		<artifactId>spring-convert</artifactId>
		<version>1.0.0</version>
	</dependency>
	
### Requirements	

Java: JDK 8            	 


### Usage options

- Implement the interface `com.namics.oss.spring.convert.Converter<SOURCE,TARGET>` on your own
- Use `com.namics.oss.spring.convert.CollectionConverter<SOURCE,TARGET>` to implement mapping of single Objects and get collection conversion for free
- Use AbstractConverter to use reflection based auto-conversion of Objects with  `com.namics.oss.spring.convert.AbstractConverter<SOURCE,TARGET>`


[travis-master]: https://travis-ci.org/namics/spring-convert.svg?branch=master
[travis-develop]: https://travis-ci.org/namics/spring-convert.svg?branch=develop
[travis-url]: https://travis-ci.org/namics/spring-convert
