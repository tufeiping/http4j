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

package com.google.code.http.metrics;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class NanoSecondTimerTestCase {
	
	private NanoSecondTimer timer;
	
	@BeforeClass
	public void beforeClass() {
		timer = new NanoSecondTimer();
	}
	
	@Test
	public void getStart() {
		Assert.assertEquals(timer.getStart(), 0);
	}
	
	@Test
	public void getStop() {
		Assert.assertEquals(timer.getStop(), 0);
	}
	
	@Test(dependsOnMethods = "getStart")
	public void start() {
		timer.start();
		Assert.assertTrue(timer.getStart() < timer.getCurrentTime());
	}
	
	@Test(dependsOnMethods = {"start", "getStop"})
	public void stop() {
		timer.stop();
		Assert.assertTrue(timer.getStop() < timer.getCurrentTime());
	}
	
	@Test(dependsOnMethods = "stop")
	public void getDuration() {
		Assert.assertTrue(timer.getDuration() > 0);
	}
	
	@Test(dependsOnMethods = "getDuration")
	public void reset() {
		timer.reset();
		Assert.assertEquals(timer.getStart(), 0);
		Assert.assertEquals(timer.getStop(), 0);
		Assert.assertEquals(timer.getDuration(), 0);
	}
}
