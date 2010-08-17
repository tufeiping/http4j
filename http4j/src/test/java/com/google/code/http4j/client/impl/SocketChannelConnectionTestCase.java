package com.google.code.http4j.client.impl;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.client.Connection;
import com.google.code.http4j.client.ConnectionTestCase;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class SocketChannelConnectionTestCase extends ConnectionTestCase {

	@BeforeClass
	public void setUp() throws IOException {
		super.setUp();
	}
	
	@Override
	protected Connection createConnection() {
		return new SocketChannelConnection(host);
	}

	@Test
	public void testConnect() {
		super.testConnect();
	}

	@Test(dependsOnMethods = "testConnect")
	public void testWrite() {
		super.testWrite();
	}

	@Test(dependsOnMethods = "testWrite")
	public void testRead() {
		super.testRead();
	}

	@AfterTest
	public void destory() {
		super.destory();
	}
}
