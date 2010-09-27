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

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.utils.Metrics;
import com.google.code.http4j.utils.ThreadLocalMetricsRecorder;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class DNSTestCase {
	
	private InetAddress address;
	
	private String host;
	
	@BeforeClass
	public void beforeClass() {
		host = "www.hao123.com";
	}
	
	@Test
	public void getAddress() throws UnknownHostException {
		address = DNS.getAddress(host);
		Assert.assertNotNull(address);
		Metrics metrics = ThreadLocalMetricsRecorder.getInstance().captureMetrics();
		Assert.assertTrue(metrics.getDnsLookupCost() > 0);
	}
	
	@Test(dependsOnMethods = "getAddress")
	public void cache() throws UnknownHostException {
		InetAddress original = InetAddress.getByName(host);
		DNS.CachedDNS cached = new DNS.CachedDNS();
		cached.cache(host, original);
		DNS.useCache();
		address = DNS.getAddress(host);
		Assert.assertNotNull(address);
		Metrics metrics = ThreadLocalMetricsRecorder.getInstance().captureMetrics();
		Assert.assertEquals(metrics.getDnsLookupCost(), 0);
	}
}
