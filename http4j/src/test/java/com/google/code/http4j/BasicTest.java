package com.google.code.http4j;

import org.testng.annotations.BeforeTest;

import com.google.code.http4j.impl.BasicContainer;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class BasicTest {
	
	@BeforeTest
	public void preTest() {
		Container.setDefault(new BasicContainer());
	}
}
