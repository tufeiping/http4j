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

package com.google.code.http.impl;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.code.http.Connection;
import com.google.code.http.ConnectionManager;
import com.google.code.http.Host;
import com.google.code.http.impl.conn.SocketConnection;
import com.google.code.http.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class ConnectionPool implements ConnectionManager {

	protected ConcurrentHashMap<Host, ConcurrentLinkedQueue<Connection>> free;

	// TODO this field might be used by customized connection reuse strategy
	protected ConcurrentHashMap<Host, AtomicInteger> used;

	protected AtomicBoolean shutdown;

	public ConnectionPool() {
		free = new ConcurrentHashMap<Host, ConcurrentLinkedQueue<Connection>>();
		used = new ConcurrentHashMap<Host, AtomicInteger>();
		shutdown = new AtomicBoolean(false);
	}

	@Override
	public Connection acquire(Host host) {
		Queue<Connection> queue = getFreeQueue(host);
		Connection connection = queue.poll();// do not use blocking queue
		connection = connection == null ? createConnection(host) : connection;
		increaseUsed(host);
		return connection;
	}

	@Override
	public boolean release(Connection connection) {
		boolean success = !shutdown.get() && !connection.isClosed();
		if (success) {
			Host host = connection.getHost();
			Queue<Connection> queue = getFreeQueue(host);
			if (queue.offer(connection)) {
				decreaseUsed(host);
			}
		}
		return success;
	}

	@Override
	public void shutdown() {
		if(shutdown.compareAndSet(false, true)) {
			closeAllConnections();
			free.clear();
			used.clear();
		}
	}

	protected Connection createConnection(Host host) {
		return new SocketConnection(host);
	}

	private void closeAllConnections() {
		Collection<ConcurrentLinkedQueue<Connection>> queues = free.values();
		for(ConcurrentLinkedQueue<Connection> queue : queues) {
			while(!queue.isEmpty()) {
				IOUtils.close(queue.poll());
			}
		}
	}

	private void increaseUsed(Host host) {
		getUsedCounter(host).incrementAndGet();
	}

	private void decreaseUsed(Host host) {
		getUsedCounter(host).decrementAndGet();
	}

	private ConcurrentLinkedQueue<Connection> getFreeQueue(Host host) {
		ConcurrentLinkedQueue<Connection> queue = free.get(host);
		if (queue == null) {
			queue = new ConcurrentLinkedQueue<Connection>();
			ConcurrentLinkedQueue<Connection> exist = free.putIfAbsent(host, queue);
			queue = exist == null ? queue : exist;
		}
		return queue;
	}

	private AtomicInteger getUsedCounter(Host host) {
		AtomicInteger counter = used.get(host);
		if (counter == null) {
			counter = new AtomicInteger(0);
			AtomicInteger exist = used.putIfAbsent(host, counter);
			counter = exist == null ? counter : exist;
		}
		return counter;
	}
}
