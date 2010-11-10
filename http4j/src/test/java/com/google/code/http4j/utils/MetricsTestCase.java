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

package com.google.code.http4j.utils;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.Client;
import com.google.code.http4j.Response;
import com.google.code.http4j.impl.BasicClient;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class MetricsTestCase {
	
	private Client client;
	
	@BeforeClass
	public void beforeClass() {
		client = new BasicClient().followRedirect(true);
	}
	
	@Test
	public void getSourceMetrics() throws InterruptedException, IOException, URISyntaxException {
		Response response = client.get("http://www.sun.com");// redirect to oracle.com
		Metrics metrics = response.getMetrics();
		Metrics sourceMetrics = metrics.getSourceMetrics();
		Assert.assertNotNull(sourceMetrics);
	}
	
	@AfterTest
	public void afterTest() {
		client.shutdown();
	}
}
