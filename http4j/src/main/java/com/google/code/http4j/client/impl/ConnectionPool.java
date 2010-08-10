package com.google.code.http4j.client.impl;

import java.net.UnknownHostException;

import com.google.code.http4j.client.HttpHost;


/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface ConnectionPool {

	Connection getConnection(HttpHost host) throws UnknownHostException;

	void releaseConnection(Connection connection);
}
