/**
 * 
 */
package com.google.code.http4j.client.impl;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicHttpHeaderTestCase {
	
	BasicHttpHeader header;
	
	@BeforeClass
	public void setUp() {
		header = new BasicHttpHeader("host", "www.google.com");
	}
	
	@Test
	public void testToUpperCase() {
		StringBuilder buffer = new StringBuilder("host");
		header.toUpperCase(buffer, 0);
		Assert.assertEquals(buffer.toString(), "Host");
	}
}
