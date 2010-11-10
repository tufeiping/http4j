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

package com.google.code.http4j.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.code.http4j.Client;
import com.google.code.http4j.ConnectionManager;
import com.google.code.http4j.CookieCache;
import com.google.code.http4j.DNS;
import com.google.code.http4j.Request;
import com.google.code.http4j.RequestExecutor;
import com.google.code.http4j.Response;
import com.google.code.http4j.impl.conn.ConnectionPool;
import com.google.code.http4j.impl.conn.SingleConnectionManager;
import com.google.code.http4j.utils.Metrics;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicClient implements Client {

	protected ConnectionManager connectionManager;

	protected final CookieCache cookieCache;

	protected final ResponseParser responseParser;

	protected boolean followRedirect;

	public BasicClient() {
		cookieCache = createCookieCache();
		responseParser = createResponseParser();
		useDNSCache(true);
		useConnectionPool(true);
		followRedirect = false;
	}

	@Override
	public BasicClient useConnectionPool(boolean use) {
		if (null != connectionManager) {
			connectionManager.shutdown();
		}
		connectionManager = use ? new ConnectionPool()
				: new SingleConnectionManager();
		return this;
	}

	@Override
	public BasicClient useDNSCache(boolean use) {
		DNS.useCache(use);
		return this;
	}

	@Override
	public Client followRedirect(boolean follow) {
		this.followRedirect = follow;
		return this;
	}

	@Override
	public Response get(String url) throws InterruptedException, IOException,
			URISyntaxException {
		return submit(new Get(url));
	}

	@Override
	public Response post(String url) throws InterruptedException, IOException,
			URISyntaxException {
		return submit(new Post(url));
	}

	@Override
	public Response submit(Request request) throws InterruptedException,
			IOException, URISyntaxException {
		RequestExecutor executor = new BasicRequestExecutor(connectionManager, cookieCache, responseParser);
		Response response = executor.execute(request);
		return postProcess(request, response);
	}

	protected Response postProcess(Request request, Response response)
			throws InterruptedException, IOException, URISyntaxException {
		if (followRedirect && response.needRedirect()) {
			Metrics sourceMetrics = response.getMetrics();
			String location = getLocation(request.getURI(), response.getRedirectLocation());
			response = get(location);// TODO bug here, www.sun.com should cause 3 deepth hierachy but now is 2.
			response.getMetrics().setSourceMetrics(sourceMetrics);
		}
		return response;
	}

	private String getLocation(URI uri, String location) {
		return location.startsWith("http") ? location : new StringBuilder(
				uri.getScheme()).append("://").append(uri.getAuthority())
				.append(location).toString();
	}

	@Override
	public void shutdown() {
		cookieCache.clear();
		connectionManager.shutdown();
	}

	protected ResponseParser createResponseParser() {
		return new ResponseParser();
	}

	protected CookieCache createCookieCache() {
		return new CookieStoreAdapter();
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			shutdown();
		} finally {
			super.finalize();
		}
	}
}
