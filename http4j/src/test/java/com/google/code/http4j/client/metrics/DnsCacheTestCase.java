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
import java.net.InetAddress;
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.client.DnsCache;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class DnsCacheTestCase {
	MetricHttpClient client;

	@BeforeClass
	public void setUp() {
		client = new MetricHttpClient();
	}
	
	@Test
	public void testCacheDns() throws IOException, URISyntaxException {
		String host = "www.csdn.net";
		InetAddress ip = InetAddress.getByName(host);
		DnsCache.getDefault().cache(host, ip);
		client.head(host);
		Timer dns = ThreadLocalMetrics.getInstance().getDnsTimer();
		Assert.assertEquals(dns.get(), 0);
	}
}
