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
import java.net.SocketTimeoutException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.Connection;
import com.google.code.http4j.Host;
import com.google.code.http4j.impl.BasicHost;
import com.google.code.http4j.impl.conn.SocketConnection;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class ConnectionTestCase {

	private Connection connection;

	private Host host;

	@BeforeClass
	public void beforeClass() throws IOException {
		host = new BasicHost("www.google.com");
		connection = createConnection();
	}
	
	private Connection createConnection() throws IOException {
		return new SocketConnection(host);
	}
	
	@Test
	public void connect() throws IOException {
		connection.connect();
	}
	
	@Test(dependsOnMethods = "connect", expectedExceptions = SocketTimeoutException.class)
	public void setTimeout() throws IOException {
		Connection conn = createConnection();
		conn.setTimeout(1);
		conn.connect();
	}
	
	@Test
	public void getInputStream() throws IOException {
		Assert.assertNotNull(connection.getInputStream());
	}
	
	@Test
	public void getOutputStream() throws IOException {
		Assert.assertNotNull(connection.getOutputStream());
	}
	
	@Test
	public void getHost() {
		Assert.assertEquals(connection.getHost(), host);
	}
	
	@Test
	public void isReusable() {
		Assert.assertTrue(connection.isReusable());
	}
	
	@Test(dependsOnMethods = "isReusable")
	public void setReusable() {
		connection.setReusable(false);
		Assert.assertFalse(connection.isReusable());
		connection.setReusable(true);
		Assert.assertTrue(connection.isReusable());
	}
	
	@AfterClass
	public void close() throws IOException {
		Assert.assertFalse(connection.isClosed());
		connection.close();
		Assert.assertTrue(connection.isClosed());
	}
}
