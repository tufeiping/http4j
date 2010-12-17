/**
 * Copyright (C) 2010 Zhang, Guilin <guilin.zhang@hotmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.http4j.utils;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.utils.Counter;
import com.google.code.http4j.utils.IntCounter;

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
		counter.addAndGet(0);
		int number = counter.get();
		Assert.assertEquals(number, 0);
		counter.addAndGet(19);
		number = counter.get();
		Assert.assertEquals(number, 19);
		counter.addAndGet(-13);
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
