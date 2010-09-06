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

package com.google.code.http;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http.impl.BasicHost;
import com.google.code.http.impl.conn.SocketConnection;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class ConnectionTestCase {

	private Connection connection;

	private Host host;

	@BeforeClass
	public void beforeClass() {
		host = new BasicHost("www.google.com");
		connection = createConnection();
	}
	
	private Connection createConnection() {
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
	
	@Test(dependsOnMethods = "connect")
	public void write() throws IOException {
		String request = "HEAD / HTTP/1.1\r\nHost:www.google.com\r\n\r\n";
		connection.write(request.getBytes());
	}
	
	@Test(dependsOnMethods = "write")
	public void read() throws IOException {
		byte[] response = connection.read();
		Assert.assertNotNull(response);
		Assert.assertTrue(response.length > 0);
		String message = new String(response);
		Assert.assertTrue(message.startsWith("HTTP/1.1"));
	}
	
	@Test(dependsOnMethods = "read")
	public void close() throws IOException {
		Assert.assertFalse(connection.isClosed());
		connection.close();
		Assert.assertTrue(connection.isClosed());
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
}
