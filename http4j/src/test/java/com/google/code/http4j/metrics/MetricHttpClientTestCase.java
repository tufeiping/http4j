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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.Container;
import com.google.code.http4j.HttpResponse;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class MetricHttpClientTestCase {

	MetricHttpClient client;

	@BeforeClass
	public void setUp() {
		client = new MetricHttpClient();
		Container.setDefault(new MetricContainer());
	}

	@Test(groups = "singlely")
	public void testMetrics() throws IOException, URISyntaxException {
		Metrics metrics = ThreadLocalMetrics.getInstance();
		Counter<Long> requestTrafficCounter = metrics.getRequestTrafficCounter();
		Counter<Long> responseTrafficCounter = metrics.getResponseTrafficCounter();
		Timer connectionTimer = metrics.getConnectionTimer();
		Timer dnsTimer = metrics.getDnsTimer();
		Timer requestTimer = metrics.getRequestTimer();
		Timer responseTimer = metrics.getResponseTimer();
		Assert.assertEquals(requestTrafficCounter.get(), Long.valueOf(0));
		Assert.assertEquals(responseTrafficCounter.get(), Long.valueOf(0));
		Assert.assertEquals(connectionTimer.get(), 0);
		Assert.assertEquals(dnsTimer.get(), 0);
		Assert.assertEquals(requestTimer.get(), 0);
		Assert.assertEquals(responseTimer.get(), 0);
		HttpResponse response = client.get("http://www.bing.com", false);
		Assert.assertNotNull(response);
		Assert.assertTrue(requestTrafficCounter.get() > 0);
		Assert.assertTrue(responseTrafficCounter.get() > 0);
		Assert.assertTrue(connectionTimer.get() > 0);
		Assert.assertTrue(dnsTimer.get() > 0);
		Assert.assertTrue(requestTimer.get() > 0);
		Assert.assertTrue(responseTimer.get() > 0);
	}

	@Test(groups = "singlely",dependsOnMethods = "testMetrics")
	public void testAggregateMetrics() throws InterruptedException, ExecutionException {
		int count = 5;
		ExecutorService executor = Executors.newFixedThreadPool(count);
		CompletionService<Boolean> service = new ExecutorCompletionService<Boolean>(executor);
		for (int i = 0; i < count; i++) {
			service.submit(new Callable<Boolean>(){
				@Override
				public Boolean call() throws Exception {
					try {
						Metrics metrics = ThreadLocalMetrics.getInstance();
						Counter<Long> requestTrafficCounter = metrics.getRequestTrafficCounter();
						Counter<Long> responseTrafficCounter = metrics.getResponseTrafficCounter();
						Timer connectionTimer = metrics.getConnectionTimer();
						Timer dnsTimer = metrics.getDnsTimer();
						Timer requestTimer = metrics.getRequestTimer();
						Timer responseTimer = metrics.getResponseTimer();
						Assert.assertEquals(requestTrafficCounter.get(), Long.valueOf(0));
						Assert.assertEquals(responseTrafficCounter.get(), Long.valueOf(0));
						Assert.assertEquals(connectionTimer.get(), 0);
						Assert.assertEquals(dnsTimer.get(), 0);
						Assert.assertEquals(requestTimer.get(), 0);
						Assert.assertEquals(responseTimer.get(), 0);
						HttpResponse response = client.get("http://www.bing.com", false);
						Assert.assertNotNull(response);
						Assert.assertTrue(requestTrafficCounter.get() > 0);
						Assert.assertTrue(responseTrafficCounter.get() > 0);
						// not sure, depends on pool behavior
						//Assert.assertEquals(connectionTimer.get(), 0); 
						Assert.assertEquals(dnsTimer.get(), 0);// dns cache
						Assert.assertTrue(requestTimer.get() > 0);
						Assert.assertTrue(responseTimer.get() > 0);
						return true;
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
				}});
		}
		boolean result = true;
		while(count --> 0) {
			result &= service.take().get();
		}
		Assert.assertTrue(result);
		AggregatedMetrics m = client.getMetrics();
		Assert.assertNotNull(m);
		Assert.assertTrue(m.getConnectionTimer().get() > 0);
		Assert.assertTrue(m.getDnsTimer().get() > 0);
		Assert.assertTrue(m.getRequestTimer().get() > 0);
		Assert.assertTrue(m.getResponseTimer().get() > 0);
		Assert.assertTrue(m.getRequestTrafficCounter().get() > 0);
		Assert.assertTrue(m.getResponseTrafficCounter().get() > 0);
	}
}
