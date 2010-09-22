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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.ConnectionManager;
import com.google.code.http4j.CookieCache;
import com.google.code.http4j.Header;
import com.google.code.http4j.Request;
import com.google.code.http4j.RequestExecutor;
import com.google.code.http4j.Response;
import com.google.code.http4j.StatusLine;
import com.google.code.http4j.impl.BasicRequestExecutor;
import com.google.code.http4j.impl.CookieStoreAdapter;
import com.google.code.http4j.impl.Get;
import com.google.code.http4j.impl.ResponseParser;
import com.google.code.http4j.impl.conn.ConnectionPool;
import com.google.code.http4j.metrics.Metrics;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class RequestExecutorTestCase {
	
	private RequestExecutor executor;
	
	private ConnectionManager manager;
	
	private CookieCache cookieCache;
	
	private ResponseParser parser;
	
	@BeforeClass
	public void beforeClass() {
		manager = new ConnectionPool();
		cookieCache = new CookieStoreAdapter();
		parser = new ResponseParser();
		executor = new BasicRequestExecutor(manager, cookieCache, parser);
	}
	
	@Test
	public void execute() throws URISyntaxException, InterruptedException, IOException {
		Request request = new Get("http://www.baidu.com/");
		Response response = executor.execute(request);
		Assert.assertNotNull(response);
		checkStatus(response);
		checkHeaders(response);
		checkCharsetAndEntity(response);
		checkMetrics(response);
	}
	
	private void checkMetrics(Response response) {
		Metrics metrics = response.getMetrics();
		Assert.assertNotNull(metrics);
		Assert.assertTrue(metrics.getBytesReceived() > 0);
		Assert.assertTrue(metrics.getBytesSent() > 0);
		Assert.assertTrue(metrics.getConnectingCost() > 0);
		Assert.assertTrue(metrics.getReceivingCost() > 0);
		Assert.assertTrue(metrics.getSendingCost() > 0);
		Assert.assertTrue(metrics.getWaitingCost() > 0);
		Assert.assertTrue(metrics.getDnsLookupCost() >= 0);// cache
	}
	
	private void checkCharsetAndEntity(Response response) throws UnsupportedEncodingException {
		String charset = response.getCharset();
		Assert.assertNotNull(charset);
		byte[] entity = response.getEntity();
		Assert.assertNotNull(entity);
		Assert.assertFalse(entity.length == 0);
		System.out.println(new String(entity, charset));
	}
	
	private void checkHeaders(Response response) {
		List<Header> headers = response.getHeaders();
		Assert.assertNotNull(headers);
		Assert.assertFalse(headers.size() == 0);
	}

	private void checkStatus(Response response) {
		StatusLine statusLine = response.getStatusLine();
		Assert.assertNotNull(statusLine);
		Assert.assertTrue(statusLine.getStatusCode() > 199);
	}
}
