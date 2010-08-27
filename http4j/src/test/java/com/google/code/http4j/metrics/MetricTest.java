package com.google.code.http4j.metrics;

import org.testng.annotations.BeforeTest;

import com.google.code.http4j.Container;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class MetricTest {
	
	@BeforeTest
	public void preTest() {
		Container.setDefault(new MetricContainer());
	}
}
