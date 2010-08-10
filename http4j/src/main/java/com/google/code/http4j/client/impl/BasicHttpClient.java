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
		connection.send(request.getFormattedMessage());
		HttpResponse response = request.parse(connection.getInputStream());
		connectionPool.releaseConnection(connection);
		return response;
	}

	protected ConnectionPool createConnectionPool() {
		return new BasicConnectionPool();
	}
}
