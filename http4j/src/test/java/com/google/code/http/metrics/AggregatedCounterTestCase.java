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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class AggregatedCounterTestCase {

	private AggregatedCounter<Long> aggregatedCounter;

	private List<Counter<? extends Number>> counters;
	
	private long total;
	
	@BeforeClass
	public void beforeClass() {
		aggregatedCounter = new AtomicLongCounter();
		int count = 100;
		counters = new ArrayList<Counter<? extends Number>>(count);
		for (int i = 0; i < count; i++) {
			Counter<? extends Number> c = i % 2 == 0 ? new IntCounter(i)
					: new LongCounter(i);
			counters.add(c);
			total += i;
		}
	}

	@Test
	public void aggregate() throws InterruptedException {
		ExecutorService pool = Executors.newFixedThreadPool(counters.size());
		CompletionService<Boolean> service = new ExecutorCompletionService<Boolean>(
				pool);
		for (Counter<? extends Number> c : counters) {
			final Counter<? extends Number> counter = c;
			service.submit(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					aggregatedCounter.aggregate(counter);
					return Boolean.TRUE;
				}
			});
		}
		int i = counters.size();
		while(i-- > 0) {
			service.take();
		}
		Assert.assertEquals(aggregatedCounter.get().longValue(), total);
	}
}
