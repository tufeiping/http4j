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

package com.google.code.http4j.client.metrics;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.code.http4j.client.Connection;
import com.google.code.http4j.client.ConnectionTestCase;
import com.google.code.http4j.client.impl.SocketConnection;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class MetricConnectionDecoratorTestCase extends ConnectionTestCase {
	
	@Test
	public void testConnect() throws IOException {
		Timer timer = ThreadLocalMetrics.getInstance().getConnectionTimer();
		Assert.assertFalse(timer.get() > 0);
		super.testConnect();
		Assert.assertTrue(timer.get() > 0);
	}
	
	@Test(dependsOnMethods = "testConnect")
	public void testWrite() {
		Metrics metrics = ThreadLocalMetrics.getInstance();
		Timer timer = metrics.getRequestTimer();
		Counter<Long> counter = metrics.getRequestTrafficCounter();
		Assert.assertEquals(timer.get(), 0);
		Assert.assertEquals(counter.get(), Long.valueOf(0));
		super.testWrite();
		Assert.assertTrue(timer.get() > 0);
		Assert.assertTrue(counter.get() > 0);
	}

	@Test(dependsOnMethods = "testWrite")
	public void testRead() {
		Metrics metrics = ThreadLocalMetrics.getInstance();
		Timer timer = metrics.getResponseTimer();
		Counter<Long> counter = metrics.getResponseTrafficCounter();
		Assert.assertEquals(timer.get(), 0);
		Assert.assertEquals(counter.get(), Long.valueOf(0));
		super.testRead();
		Assert.assertTrue(timer.get() > 0);
		Assert.assertTrue(counter.get() > 0);
	}
	
	@Test(dependsOnMethods = "testConnect")
	public void testIsClosed() {
		super.testIsClosed();
	}

	@Override
	protected Connection createConnection() {
		return new MetricConnectionDecorator(new SocketConnection(host));
	}
}
