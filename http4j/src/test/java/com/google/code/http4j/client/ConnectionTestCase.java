package com.google.code.http4j.client;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.client.impl.BasicHttpHost;
import com.google.code.http4j.client.impl.SocketChannelConnection;
import com.google.code.http4j.client.impl.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class ConnectionTestCase {
	
	private Connection connection;
	
	private HttpHost host;
	
	@BeforeClass
	public void setUp() throws IOException {
		host = new BasicHttpHost("www.google.com");
		connection = new SocketChannelConnection(host);
	}
	
	@Test
	public void testConnect() {
		try {
			connection.connect();
		} catch (IOException e) {
			Assert.fail("Can not connect to: " + connection.getHost());
		}
	}
	
	@Test(dependsOnMethods = "testConnect")
	public void testWrite() {
		String head = "GET / HTTP/1.1\r\nHost:" + host.getHostName() + "\r\n\r\n";
		try {
			connection.write(head.getBytes());
		} catch (IOException e) {
			Assert.fail("Can not write request via connection.");
		}
	}
	
	@Test(dependsOnMethods = "testWrite")
	public void testRead() {
		try {
			byte[] response = connection.read();
			Assert.assertNotNull(response);			
			Assert.assertTrue(response.length > 0);
		} catch (IOException e) {
			Assert.fail("Can not read response via connection.");
		}
	}
	
	@AfterTest
	public void destory() {
		IOUtils.close(connection);
	}
}
