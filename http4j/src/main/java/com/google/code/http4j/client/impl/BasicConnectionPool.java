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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.code.http4j.client.Connection;
import com.google.code.http4j.client.ConnectionPool;
import com.google.code.http4j.client.HttpHost;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicConnectionPool implements ConnectionPool {
	
	protected ConcurrentHashMap<HttpHost, ConcurrentLinkedQueue<Connection>> pool;
	
	public BasicConnectionPool() {
		pool = new ConcurrentHashMap<HttpHost, ConcurrentLinkedQueue<Connection>>();
	}
	
	protected Connection createConnection(HttpHost host) throws IOException {
		return new SocketChannelConnection(host);
	}

	protected Connection establishConnection(HttpHost host) throws IOException {
		Connection connection = createConnection(host);
		connection.connect();
		return connection;
	}
	
	@Override
	public Connection getConnection(HttpHost host) throws IOException {
		Connection connection = poll(host);
		return (null == connection || connection.isClosed())? establishConnection(host) : connection;
	}

	protected ConcurrentLinkedQueue<Connection> getQueue(HttpHost host) {
		if(null == pool.get(host)) {// avoid create a queue each time
			pool.putIfAbsent(host, new ConcurrentLinkedQueue<Connection>());
		}
		return pool.get(host);
	}
	
	protected Connection poll(HttpHost host) {
		return getQueue(host).poll();
	}
	
	@Override
	public void releaseConnection(Connection connection) {
		if(! connection.isClosed()) {
			getQueue(connection.getHost()).offer(connection);
		}
	}
}
