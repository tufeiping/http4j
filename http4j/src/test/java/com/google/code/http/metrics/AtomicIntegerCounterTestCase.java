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
 *
 */
public final class AtomicIntegerCounterTestCase {
	private AggregatedCounter<Integer> counter;

	@BeforeClass
	public void beforeClass() {
		counter = new AtomicIntegerCounter();
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
		counter.addAndGet(1048576);
		number = counter.get();
		Assert.assertEquals(number, 1048576);
		counter.addAndGet(-13);
		number = counter.get();
		Assert.assertEquals(number, 1048576 - 13);
	}

	@Test(dependsOnMethods = "increase")
	public void reset() {
		counter.reset();
		int number = counter.get();
		Assert.assertEquals(number, 0);
	}

	@Test(dependsOnMethods = "reset")
	public void aggregate() throws InterruptedException, ExecutionException {
		int n = 100;
		ExecutorService pool = Executors.newFixedThreadPool(n);
		CompletionService<Integer> service = new ExecutorCompletionService<Integer>(pool);
		for (int i = 0; i < n; i++) {
			final Integer j = i;
			service.submit(new Callable<Integer>() {
				@Override
				public Integer call() {
					Counter<Integer> c = new IntCounter();
					c.addAndGet(j);
					counter.aggregate(c);
					return j;
				}
			});
		}
		
		int total = 0;
		while(n-- > 0) {
			total += service.take().get();
		}
		Assert.assertEquals(counter.get().intValue(), total);
	}
}
