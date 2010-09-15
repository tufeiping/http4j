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

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class SumTimerTestCase {
	private SumTimer timer;

	@BeforeClass
	public void beforeClass() {
		timer = new SumTimer();
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
		Assert.assertTrue(timer.getStart() < timer.getCurrentTime().longValue());
	}
	
	@Test(dependsOnMethods = {"start", "getStop"})
	public void stop() {
		timer.stop();
		Assert.assertTrue(timer.getStop() < timer.getCurrentTime().longValue());
	}
	
	@Test(dependsOnMethods = "stop")
	public void getDuration() {
		Assert.assertTrue(timer.getDuration() > 0);
	}
	
	@Test(dependsOnMethods = "stop")
	public void reset() {
		timer.reset();
		Assert.assertEquals(timer.getStart(), 0);
		Assert.assertEquals(timer.getStop(), 0);
		Assert.assertEquals(timer.getDuration(), 0);
	}
	
	@Test(dependsOnMethods = "reset")
	public void aggregate() throws InterruptedException, ExecutionException {
		int n = 100;
		ExecutorService pool = Executors.newFixedThreadPool(n);
		CompletionService<Timer> service = new ExecutorCompletionService<Timer>(pool);
		for (int i = 0; i < n; i++) {
			service.submit(new Callable<Timer>() {
				@Override
				public Timer call() {
					Timer t = new NanoSecondTimer();
					t.start();
					try {
						Thread.sleep(new Random().nextInt(3));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					t.stop();
					timer.aggregate(t);
					return t;
				}
			});
		}
		
		long total = 0;
		long start = Long.MAX_VALUE;
		long stop = 0;
		while(n-- > 0) {
			Timer t = service.take().get();
			start = Math.min(start, t.getStart());
			stop = Math.max(stop, t.getStop());
			total += t.getDuration();
		}
		Assert.assertEquals(timer.getStart(), start);
		Assert.assertEquals(timer.getStop(), stop);
		Assert.assertEquals(timer.getDuration(), total);
	}
}
