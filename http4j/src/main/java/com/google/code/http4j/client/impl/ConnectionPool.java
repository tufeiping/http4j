package com.google.code.http4j.client.impl;

import java.io.IOException;

import com.google.code.http4j.client.HttpHost;


/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface ConnectionPool {

	Connection getConnection(HttpHost host) throws IOException;

	void releaseConnection(Connection connection);
}
