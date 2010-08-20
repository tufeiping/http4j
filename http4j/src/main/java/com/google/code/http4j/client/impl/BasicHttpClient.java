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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.http4j.client.Connection;
import com.google.code.http4j.client.ConnectionPool;
import com.google.code.http4j.client.CookieCache;
import com.google.code.http4j.client.HttpClient;
import com.google.code.http4j.client.HttpHeader;
import com.google.code.http4j.client.HttpHost;
import com.google.code.http4j.client.HttpRequest;
import com.google.code.http4j.client.HttpResponse;
import com.google.code.http4j.client.HttpResponseParser;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicHttpClient implements HttpClient {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected ConnectionPool connectionPool;
	protected CookieCache cookieCache;

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
		cookieCache = createCookieCache();
	}

	protected ConnectionPool createConnectionPool() {
		return new BasicConnectionPool();
	}

	protected CookieCache createCookieCache() {
		return new CookieStoreAdapter();
	}

	protected HttpRequest createGetRequest(String url)
			throws MalformedURLException, UnknownHostException {
		return new HttpGet(url);
	}

	protected HttpRequest createHeadRequest(String url)
			throws MalformedURLException, UnknownHostException {
		return new HttpHead(url);
	}

	protected HttpRequest createPostRequest(String url)
			throws MalformedURLException, UnknownHostException {
		return new HttpPost(url);
	}

	protected HttpResponseParser createResponseParser() {
		return new BasicHttpResponseParser();
	}

	@Override
	public HttpResponse get(String url) throws IOException, URISyntaxException {
		return get(url, true);
	}

	@Override
	public HttpResponse get(String url, boolean parseEntity)
			throws IOException, URISyntaxException {
		return submit(createGetRequest(url), parseEntity);
	}

	protected Connection getConnection(HttpHost host) throws IOException {
		return connectionPool.getConnection(host);
	}

	@Override
	public HttpResponse head(String url) throws IOException, URISyntaxException {
		return submit(createHeadRequest(url), false);
	}

	@Override
	public HttpResponse post(String url) throws IOException, URISyntaxException {
		return post(url, true);
	}

	@Override
	public HttpResponse post(String url, boolean parseEntity)
			throws IOException, URISyntaxException {
		return submit(createPostRequest(url), parseEntity);
	}

	protected void setCookies(HttpRequest request) throws URISyntaxException {
		HttpHeader cookies = cookieCache.getCookies(request.getUri());
		if (null != cookies) {
			logger.debug("Cookie header >>\r\n{}", cookies);
			request.addHeader(cookies);
		}
	}

	protected void setCookies(URI uri, HttpResponse response) {
		List<HttpHeader> headers = response.getHeaders();
		cookieCache.storeCookies(uri, headers);
	}

	protected HttpResponse submit(HttpRequest request, boolean parseEntity)
			throws IOException, URISyntaxException {
		Connection connection = getConnection(request.getHost());
		try {
			setCookies(request);
			connection.write(request.format().getBytes());
			byte[] message = connection.read();
			connectionPool.releaseConnection(connection);
			HttpResponse response = createResponseParser().parse(message, parseEntity);
			setCookies(request.getUri(), response);
			return response;
		} catch (IOException e) {
			connection.close();
			throw e;
		} catch (URISyntaxException e) {
			connection.close();
			throw e;
		}
	}
}
