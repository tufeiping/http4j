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

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicClient implements Client {

	protected ConnectionManager connectionManager;

	protected final CookieCache cookieCache;

	protected final ResponseParser responseParser;

	public BasicClient() {
		cookieCache = createCookieCache();
		responseParser = createResponseParser();
		useDNSCache();
		useConnectionPool();
	}
	
	public BasicClient noConnectionPool() {
		connectionManager.shutdown();
		connectionManager = new SingleConnectionManager();
		return this;
	}
	
	public BasicClient useConnectionPool() {
		connectionManager = new ConnectionPool();
		return this;
	}
	
	/**
	 * DNS lookup cost is normally greater than 0 except the connection is cached.
	 * @return this
	 */
	public BasicClient noDNSCache() {
		DNS.noCache();
		return this;
	}
	
	public BasicClient useDNSCache() {
		DNS.useCache();
		return this;
	}

	@Override
	public Response submit(Request request) throws InterruptedException,
			IOException {
		RequestExecutor executor = new BasicRequestExecutor(connectionManager, cookieCache, responseParser);
		return executor.execute(request);
	}
	
	@Override
	public Response get(String url) throws InterruptedException, IOException, URISyntaxException {
		return submit(new Get(url));
	}
	
	@Override
	public Response post(String url) throws InterruptedException, IOException, URISyntaxException {
		return submit(new Post(url));
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
}
