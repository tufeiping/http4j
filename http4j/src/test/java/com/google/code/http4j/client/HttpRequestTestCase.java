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

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.client.impl.BasicDnsCache;
import com.google.code.http4j.client.impl.BasicHttpHost;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public abstract class HttpRequestTestCase {
	
	protected HttpHost host;
	protected HttpRequest request;
	protected DnsCache dnsCache;

	@BeforeClass
	public void setUp() throws MalformedURLException, UnknownHostException, URISyntaxException {
		dnsCache = createDnsCache();
		host = new BasicHttpHost("www.google.com", dnsCache);
		request = createHttpRequest("http://www.google.com/search?q=http4j");
	}
	
	@Test
	public void testGetHost() throws MalformedURLException, UnknownHostException {
		Assert.assertEquals(request.getHost(), host);
	}
	
	protected DnsCache createDnsCache() {
		return new BasicDnsCache();
	}
	
	abstract protected HttpRequest createHttpRequest(String url) throws MalformedURLException, UnknownHostException, URISyntaxException;
	
	abstract public void testHasResponseEntity();
}
