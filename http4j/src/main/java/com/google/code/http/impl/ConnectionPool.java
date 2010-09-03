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
import java.util.concurrent.LinkedBlockingQueue;
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

	protected ConcurrentHashMap<Host, Queue<Connection>> free;

	// TODO this field might be used by customized connection reuse strategy
	protected ConcurrentHashMap<Host, AtomicInteger> used;

	protected AtomicBoolean shutdown;

	public ConnectionPool() {
		free = new ConcurrentHashMap<Host, Queue<Connection>>();
		used = new ConcurrentHashMap<Host, AtomicInteger>();
		shutdown = new AtomicBoolean(false);
	}

	@Override
	public Connection acquire(Host host) {
		Queue<Connection> queue = getFreeQueue(host);
		Connection connection = queue.poll();
		connection = connection == null ? createConnection(host) : connection;
		increaseUsed(host);
		return connection;
	}

	@Override
	public void release(Connection connection) {
		if (!shutdown.get() && !connection.isClosed()) {
			Host host = connection.getHost();
			Queue<Connection> queue = getFreeQueue(host);
			if (queue.offer(connection)) {
				decreaseUsed(host);
			}
		}
	}

	@Override
	public void shutdown() {
		shutdown.compareAndSet(false, true);
		closeAllConnections();
		free.clear();
		used.clear();
	}

	protected Connection createConnection(Host host) {
		return new SocketConnection(host);
	}

	protected Queue<Connection> createQueue() {
		return new LinkedBlockingQueue<Connection>();
	}

	private void closeAllConnections() {
		Collection<Queue<Connection>> queues = free.values();
		for(Queue<Connection> queue : queues) {
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

	private Queue<Connection> getFreeQueue(Host host) {
		Queue<Connection> queue = free.get(host);
		if (queue == null) {
			queue = createQueue();
			Queue<Connection> exist = free.putIfAbsent(host, queue);
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
