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
package com.google.code.http4j.client;

import java.io.IOException;

import org.testng.Assert;

import com.google.code.http4j.client.impl.BasicHttpHost;
import com.google.code.http4j.client.impl.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public abstract class ConnectionTestCase {

	protected Connection connection;

	protected HttpHost host;

	public void setUp() throws IOException {
		host = new BasicHttpHost("www.google.com");
		connection = createConnection();
	}

	abstract protected Connection createConnection();

	public void testConnect() {
		try {
			connection.connect();
		} catch (IOException e) {
			Assert.fail("Can not connect to: " + connection.getHost());
		}
	}

	public void testWrite() {
		String head = "GET / HTTP/1.1\r\nHost:" + host.getHostName()
				+ "\r\n\r\n";
		try {
			connection.write(head.getBytes());
		} catch (IOException e) {
			Assert.fail("Can not write request via connection.");
		}
	}

	public void testRead() {
		try {
			byte[] response = connection.read();
			Assert.assertNotNull(response);
			Assert.assertTrue(response.length > 0);
		} catch (IOException e) {
			Assert.fail("Can not read response via connection.");
		}
	}

	public void destory() {
		IOUtils.close(connection);
	}
}
