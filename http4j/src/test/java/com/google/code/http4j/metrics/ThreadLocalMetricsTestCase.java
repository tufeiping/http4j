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

package com.google.code.http4j.metrics;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.code.http4j.metrics.Metrics;
import com.google.code.http4j.metrics.ThreadLocalMetrics;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class ThreadLocalMetricsTestCase {
	
	@Test
	public void testInSameThread() {
		Metrics instance1 = ThreadLocalMetrics.getInstance();
		Metrics instance2 = ThreadLocalMetrics.getInstance();
		Assert.assertTrue(instance1 == instance2);
	}
	
	@Test
	public void testInDifferentThreads() throws InterruptedException, ExecutionException {
		Metrics instance1 = ThreadLocalMetrics.getInstance();
		ExecutorService service = Executors.newFixedThreadPool(2);
		Callable<Metrics> callable = new Callable<Metrics>(){
			@Override
			public Metrics call() throws Exception {
				return ThreadLocalMetrics.getInstance();
			}};
		Metrics instance2 = service.submit(callable).get();
		Metrics instance3 = service.submit(callable).get();
		Assert.assertFalse(instance1 == instance2);
		Assert.assertFalse(instance1 == instance3);
		Assert.assertFalse(instance2 == instance3);
	}
}
