package com.google.code.http.metrics;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class IntCounterTestCase {
	
	private Counter<Integer> counter;
	
	@BeforeClass
	public void beforeClass() {
		counter = new IntCounter();
	}

	@Test
	public void get() {
		int number = counter.get();
		Assert.assertEquals(number, 0);
	}
	
	@Test(dependsOnMethods = "get")
	public void increase() {
		counter.increase(0);
		int number = counter.get();
		Assert.assertEquals(number, 0);
		counter.increase(19);
		number = counter.get();
		Assert.assertEquals(number, 19);
		counter.increase(-13);
		number = counter.get();
		Assert.assertEquals(number, 6);
	}
	
	@Test(dependsOnMethods = "increase")
	public void reset() {
		counter.reset();
		int number = counter.get();
		Assert.assertEquals(number, 0);
	}
}
