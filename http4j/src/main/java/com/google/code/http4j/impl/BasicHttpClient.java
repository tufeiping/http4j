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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.http4j.Connection;
import com.google.code.http4j.ConnectionPool;
import com.google.code.http4j.Container;
import com.google.code.http4j.CookieCache;
import com.google.code.http4j.DnsCache;
import com.google.code.http4j.HttpClient;
import com.google.code.http4j.HttpHeader;
import com.google.code.http4j.HttpHost;
import com.google.code.http4j.HttpRequest;
import com.google.code.http4j.HttpResponse;
import com.google.code.http4j.HttpResponseParser;

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
		ensureContainerCreated();
		connectionPool = createConnectionPool();
		cookieCache = createCookieCache();
		DnsCache.setDefault(createDnsCache());
	}
	
	protected void ensureContainerCreated() {
		Container container = Container.getDefault();
		if(null == container) {
			container = createContainer();
			Container.setDefault(container);
		}
	}

	protected Container createContainer() {
		return new BasicContainer();
	}

	protected ConnectionPool createConnectionPool() {
		return Container.getDefault().getConnectionPoolFactory().create();
	}

	protected CookieCache createCookieCache() {
		return Container.getDefault().getCookieCacheFactory().create();
	}

	protected DnsCache createDnsCache() {
		return new BasicDnsCache();
	}

	protected HttpRequest createGetRequest(String url, String encoding)
			throws MalformedURLException, UnknownHostException, URISyntaxException {
		return new HttpGet(url, encoding);
	}

	protected HttpRequest createHeadRequest(String url, String encoding)
			throws MalformedURLException, UnknownHostException, URISyntaxException {
		return new HttpHead(url, encoding);
	}

	protected HttpRequest createPostRequest(String url, String encoding)
			throws MalformedURLException, UnknownHostException, URISyntaxException {
		return new HttpPost(url, encoding);
	}

	protected HttpResponseParser createResponseParser() {
		return new BasicHttpResponseParser();
	}

	@Override
	public HttpResponse get(String url) throws IOException, URISyntaxException {
		return get(url, null);
	}

	@Override
	public HttpResponse get(String url, boolean parseEntity)
			throws IOException, URISyntaxException {
		return get(url, null, parseEntity);
	}

	@Override
	public HttpResponse get(String url, String encoding) throws IOException,
			URISyntaxException {
		return get(url, encoding, true);
	}

	@Override
	public HttpResponse get(String url, String encoding, boolean parseEntity)
			throws IOException, URISyntaxException {
		return submit(createGetRequest(url, encoding), parseEntity);
	}

	protected Connection getConnection(HttpHost host) throws IOException {
		return connectionPool.getConnection(host);
	}

	@Override
	public HttpResponse head(String url) throws IOException, URISyntaxException {
		return head(url, null);
	}

	@Override
	public HttpResponse head(String url, String encoding) throws IOException,
			URISyntaxException {
		return submit(createHeadRequest(url, null), false);
	}

	@Override
	public HttpResponse post(String url) throws IOException, URISyntaxException {
		return post(url, null);
	}

	@Override
	public HttpResponse post(String url, boolean parseEntity)
			throws IOException, URISyntaxException {
		return post(url, null, parseEntity);
	}

	@Override
	public HttpResponse post(String url, String encoding) throws IOException,
			URISyntaxException {
		return post(url, encoding, true);
	}

	@Override
	public HttpResponse post(String url, String encoding, boolean parseEntity)
			throws IOException, URISyntaxException {
		return submit(createPostRequest(url, encoding), parseEntity);
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
			String requestMessage = request.format();
			logger.debug("Request >>\r\n{}", requestMessage);
			connection.write(requestMessage.getBytes());
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
