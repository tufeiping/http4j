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
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http.impl.BasicClient;
import com.google.code.http.impl.Get;
import com.google.code.http.metrics.Metrics;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class ClientTestCase {
	
	private Client client;
	
	@BeforeClass
	public void beforeClass() {
		client = new BasicClient();
	}
	
	@Test
	public void submit() throws IOException, InterruptedException, URISyntaxException {
		Response response = client.submit(new Get("http://www.baidu.com/"));
		checkResponse(response);
	}
	
	@Test
	public void get() throws InterruptedException, IOException, URISyntaxException {
		Response response = client.get("http://www.baidu.com");
		checkResponse(response);
	}
	
	@Test
	public void post() throws InterruptedException, IOException, URISyntaxException {
		Response response = client.post("http://www.javaeye.com/login?name=invalid&password=invalid");
		checkResponse(response);
	}
	
	@Test(dependsOnMethods = {"submit", "get", "post"})
	public void getMetrics() {
		checkMetrics(client.getMetrics());
	}
	
	private void checkResponse(Response response) {
		Assert.assertNotNull(response);
		StatusLine statusLine = response.getStatusLine();
		Assert.assertNotNull(statusLine);
		Assert.assertTrue(statusLine.getStatusCode() > 199);
		Assert.assertTrue(statusLine.getStatusCode() < 400);
		checkMetrics(response.getMetricsRecorder().captureMetrics());
	}
	
	private void checkMetrics(Metrics metrics) {
		Assert.assertTrue(metrics.getBytesReceived() > 0);
		Assert.assertTrue(metrics.getBytesSent() > 0);
		Assert.assertTrue(metrics.getConnectingCost() >= 0);// cache
		Assert.assertTrue(metrics.getReceivingCost() > 0);
		Assert.assertTrue(metrics.getSendingCost() > 0);
		Assert.assertTrue(metrics.getWaitingCost() > 0);
		Assert.assertTrue(metrics.getDnsLookupCost() >= 0);// cache
	}
}
