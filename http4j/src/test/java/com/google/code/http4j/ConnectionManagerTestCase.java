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

package com.google.code.http4j;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.Connection;
import com.google.code.http4j.ConnectionManager;
import com.google.code.http4j.Host;
import com.google.code.http4j.impl.BasicHost;
import com.google.code.http4j.impl.conn.ConnectionPool;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class ConnectionManagerTestCase {

	private ConnectionManager manager;

	private Host host;

	private Connection connection1;

	private Connection connection2;

	@BeforeClass
	public void beforeClass() {
		manager = new ConnectionPool();
		host = new BasicHost("www.google.com");
	}

	@Test
	public void acquire() throws InterruptedException, IOException {
		connection1 = manager.acquire(host);
		Assert.assertNotNull(connection1);
		Assert.assertEquals(connection1.getHost(), host);
		connection2 = manager.acquire(host);
		Assert.assertNotNull(connection2);
		Assert.assertEquals(connection2.getHost(), host);
		Assert.assertFalse(connection1 == connection2);
	}

	@Test(dependsOnMethods = "acquire")
	public void release() {
		boolean success1 = manager.release(connection1);
		boolean success2 = manager.release(connection2);
		Assert.assertTrue(success1 && success2);
	}

	@Test(dependsOnMethods = "release")
	public void shutdown() throws InterruptedException, IOException {
		manager.shutdown();
		Connection connection = manager.acquire(host);
		Assert.assertNull(connection);
		boolean released = manager.release(connection1);
		Assert.assertFalse(released);
	}

	@Test(expectedExceptions = TimeoutException.class)
	public void setMaxConnectionsPerHost() throws InterruptedException,
			ExecutionException, TimeoutException, IOException {
		final ConnectionManager pool = new ConnectionPool();
		pool.setMaxConnectionsPerHost(1);
		try {
			Connection connection = pool.acquire(host);
			Assert.assertNotNull(connection);
			pool.release(connection);
			connection = pool.acquire(host);
			Assert.assertNotNull(connection);
			ExecutorService service = Executors.newSingleThreadExecutor();
			service.invokeAny(Collections.singleton(new Callable<Connection>() {
				@Override
				public Connection call() throws Exception {
					return pool.acquire(host);
				}
			}), 2, TimeUnit.SECONDS);
		} finally {
			pool.shutdown();
		}
	}
}
