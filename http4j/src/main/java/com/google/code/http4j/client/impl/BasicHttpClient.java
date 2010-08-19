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
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import com.google.code.http4j.client.Connection;
import com.google.code.http4j.client.ConnectionPool;
import com.google.code.http4j.client.HttpClient;
import com.google.code.http4j.client.HttpHost;
import com.google.code.http4j.client.HttpRequest;
import com.google.code.http4j.client.HttpResponse;
import com.google.code.http4j.client.HttpResponseParser;

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

	protected ConnectionPool createConnectionPool() {
		return new BasicConnectionPool();
	}

	protected HttpRequest createGetRequest(String url) throws MalformedURLException, UnknownHostException {
		return new HttpGet(url);
	}
	
	protected HttpRequest createHeadRequest(String url) throws MalformedURLException, UnknownHostException {
		return new HttpHead(url);
	}
	
	protected HttpRequest createPostRequest(String url) throws MalformedURLException, UnknownHostException {
		return new HttpPost(url);
	}

	protected HttpResponseParser createResponseParser() {
		return new BasicHttpResponseParser();
	}

	@Override
	public byte[] execute(HttpRequest request) throws IOException {
		Connection connection = getConnection(request.getHost());
		try {
			connection.write(request.format().getBytes());
			byte[] response = connection.read();
			connectionPool.releaseConnection(connection);
			return response;
		} catch (IOException e) {
			connection.close();
			throw e;
		}
	}

	@Override
	public byte[] executeGet(String url) throws IOException {
		return execute(createGetRequest(url));
	}

	@Override
	public byte[] executeHead(String url) throws IOException {
		return execute(createHeadRequest(url));
	}

	@Override
	public byte[] executePost(String url) throws IOException {
		return execute(createPostRequest(url));
	}
	
	@Override
	public HttpResponse get(String url) throws IOException {
		return submit(createGetRequest(url));
	}

	protected Connection getConnection(HttpHost host) throws IOException {
		return connectionPool.getConnection(host);
	}
	
	@Override
	public HttpResponse head(String url) throws IOException {
		return submit(createHeadRequest(url));
	}
	
	@Override
	public HttpResponse post(String url) throws IOException {
		return submit(createPostRequest(url));
	}
	
	@Override
	public HttpResponse submit(HttpRequest request) throws IOException {
		return createResponseParser().parse(execute(request), request.hasResponseEntity());
	}
}
