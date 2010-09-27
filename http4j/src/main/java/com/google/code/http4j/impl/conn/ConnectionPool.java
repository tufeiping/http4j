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

package com.google.code.http4j.impl.conn;

import java.io.IOException;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.code.http4j.Connection;
import com.google.code.http4j.Host;
import com.google.code.http4j.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class ConnectionPool extends AbstractConnectionManager {

	protected ConcurrentHashMap<Host, ConcurrentLinkedQueue<Connection>> free;

	public ConnectionPool() {
		this(MAX_CONNECTION_PER_HOST);
	}
	
	public ConnectionPool(int maxConnectionsPerHost) {
		super(maxConnectionsPerHost);
		free = new ConcurrentHashMap<Host, ConcurrentLinkedQueue<Connection>>();
	}

	@Override
	public void doShutdown() {
		closeAllConnections();
		free.clear();
		used.clear();
	}
	
	@Override
	public boolean doRelease(Connection connection) {
		boolean reuse = !shutdown.get() && connection.isReusable();
		if (reuse) {
			reuse = getFreeQueue(connection.getHost()).offer(connection);
		}
		if(!reuse) {
			IOUtils.close(connection);
		}
		return reuse;
	}
	
	protected Connection getConnection(Host host) throws InterruptedException, IOException {
		increaseUsed(host);
		Queue<Connection> queue = getFreeQueue(host);
		Connection connection = queue.poll();
		connection = connection == null || connection.isClosed() ? createConnection(host) : connection;
		return connection;
	}

	private void closeAllConnections() {
		Collection<ConcurrentLinkedQueue<Connection>> queues = free.values();
		for (ConcurrentLinkedQueue<Connection> queue : queues) {
			while (!queue.isEmpty()) {
				IOUtils.close(queue.poll());
			}
		}
	}

	private ConcurrentLinkedQueue<Connection> getFreeQueue(Host host) {
		ConcurrentLinkedQueue<Connection> queue = free.get(host);
		if (queue == null) {
			queue = new ConcurrentLinkedQueue<Connection>();
			ConcurrentLinkedQueue<Connection> exist = free.putIfAbsent(host,
					queue);
			queue = exist == null ? queue : exist;
		}
		return queue;
	}
}
