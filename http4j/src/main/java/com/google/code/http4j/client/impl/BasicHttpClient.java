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

package com.google.code.http4j.client.impl;

import java.io.IOException;

import com.google.code.http4j.client.HttpClient;
import com.google.code.http4j.client.HttpRequest;
import com.google.code.http4j.client.HttpResponse;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicHttpClient implements HttpClient {

	protected ConnectionPool connectionPool;

	/**
	 * Construct a <code>BasicHttpClient</code> instance, subclass can
	 * <code>override</code> {@link #createConnectionPool()} and
	 * {@link #createResponseParser()} method to apply customized
	 * implementation.
	 * 
	 * @see #createConnectionPool()
	 */
	public BasicHttpClient() {
		connectionPool = createConnectionPool();
	}

	@Override
	public HttpResponse submit(HttpRequest request) throws IOException {
		Connection connection = connectionPool.getConnection(request.getHost());
		try {
			connection.send(request.format());
			HttpResponse response = request.parse(connection.getInputStream());
			connectionPool.releaseConnection(connection);
			return response;
		} catch (IOException e) {
			connection.close();
			throw e;
		}
	}

	protected ConnectionPool createConnectionPool() {
		return new BasicConnectionPool();
	}

	@Override
	public HttpResponse head(String url) throws IOException {
		HttpRequest request = new HttpHead(url);
		return submit(request);
	}
}
