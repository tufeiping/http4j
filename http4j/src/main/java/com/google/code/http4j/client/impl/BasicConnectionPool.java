package com.google.code.http4j.client.impl;


import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.code.http4j.client.HttpHost;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicConnectionPool implements ConnectionPool {
	
	protected ConcurrentHashMap<HttpHost, ConcurrentLinkedQueue<Connection>> pool;
	
	public BasicConnectionPool() {
		pool = new ConcurrentHashMap<HttpHost, ConcurrentLinkedQueue<Connection>>();
	}
	
	@Override
	public Connection getConnection(HttpHost host) throws IOException {
		Connection connection = poll(host);
		return (null == connection || connection.isClosed())? createConnection(host) : connection;
	}

	@Override
	public void releaseConnection(Connection connection) {
		if(! connection.isClosed()) {
			getQueue(connection.getHost()).offer(connection);
		}
	}
	
	protected Connection poll(HttpHost host) {
		return getQueue(host).poll();
	}
	
	protected Connection createConnection(HttpHost host) throws IOException {
		SocketConnection connection = new SocketConnection(host);
		connection.connect();
		return connection;
	}
	
	protected ConcurrentLinkedQueue<Connection> getQueue(HttpHost host) {
		if(null == pool.get(host)) {// avoid create a queue each time
			pool.putIfAbsent(host, new ConcurrentLinkedQueue<Connection>());
		}
		return pool.get(host);
	}
}
