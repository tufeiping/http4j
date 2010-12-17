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

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.impl.BasicHost;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public final class HostTestCase {
	
	private String host;
	
	private Host googleHttp;
	
	private Host googleHttps;
	
	private Host specialHttp;
	
	private Host specialHttps;
	
	@BeforeClass
	public void beforeClass() {
		host = "www.google.com";
		googleHttp = new BasicHost("http", host, 80);
		googleHttps = new BasicHost("https", host, 443);
		specialHttp = new BasicHost("http", host, 8080);
		specialHttps = new BasicHost("https", host, 993);
	}
	
	@Test
	public void construct_cause_exception() {
		construct_with_error("ftp", host, 22);
		construct_with_error("http", null, 22);
		construct_with_error("http", "www.google.com", -3);
	}
	
	private void construct_with_error(String protocol, String host, int port) {
		try {
			new BasicHost(protocol, host, port);
			Assert.fail("Exception should be thrown, but not.");
		} catch(IllegalArgumentException e) {
			// OK
		}
	}
	
	@Test
	public void getName() {
		Assert.assertEquals(googleHttp.getName(), host);
		Assert.assertEquals(googleHttps.getName(), host);
		Assert.assertEquals(specialHttp.getName(), host);
		Assert.assertEquals(specialHttps.getName(), host);
	}
	
	@Test
	public void getAuthority() {
		Assert.assertEquals(googleHttp.getAuthority(), host);
		Assert.assertEquals(googleHttps.getAuthority(), host);
		Assert.assertEquals(specialHttp.getAuthority(), host + ":" + 8080);
		Assert.assertEquals(specialHttps.getAuthority(), host + ":" + 993);
	}
	
	@Test
	public void getPort() {
		Assert.assertEquals(googleHttp.getPort(), 80);
		Assert.assertEquals(googleHttps.getPort(), 443);
		Assert.assertEquals(specialHttp.getPort(), 8080);
		Assert.assertEquals(specialHttps.getPort(), 993);
	}
	
	@Test
	public void getProtocol() {
		Assert.assertEquals(googleHttp.getProtocol().toString(), "http");
		Assert.assertEquals(googleHttps.getProtocol().toString(), "https");
		Assert.assertEquals(specialHttp.getProtocol().toString(), "http");
		Assert.assertEquals(specialHttps.getProtocol().toString(), "https");
	}
	
	@Test
	public void getDefaultPort() {
		Assert.assertEquals(googleHttp.getDefaultPort(), 80);
		Assert.assertEquals(googleHttps.getDefaultPort(), 443);
		Assert.assertEquals(specialHttp.getDefaultPort(), 80);
		Assert.assertEquals(specialHttps.getDefaultPort(), 443);
	}
	
	@Test
	public void equals_object() {
		Assert.assertTrue(googleHttp.equals(googleHttp));
		Assert.assertTrue(googleHttp.equals(new BasicHost("http", "www.google.com", 80)));
		Assert.assertFalse(googleHttp.equals(new Object()));
		Assert.assertFalse(googleHttp.equals(googleHttps));
		Assert.assertFalse(googleHttp.equals(null));
	}
	
	@Test
	public void _hashCode() {
		Assert.assertEquals(googleHttp.hashCode(), new BasicHost("http", "www.google.com", 80).hashCode());
	}
}
