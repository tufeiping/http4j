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

package com.google.code.http.impl.conn;

import java.io.IOException;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.code.http.Connection;
import com.google.code.http.ConnectionCache;
import com.google.code.http.Host;
import com.google.code.http.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class ConnectionPool implements ConnectionCache {

	protected ConcurrentHashMap<Host, ConcurrentLinkedQueue<Connection>> free;

	protected ConcurrentHashMap<Host, Semaphore> used;

	protected AtomicBoolean shutdown;
	
	protected int maxConnectionsPerHost;

	public ConnectionPool() {
		this(15);
	}
	
	public ConnectionPool(int maxConnectionsPerHost) {
		free = new ConcurrentHashMap<Host, ConcurrentLinkedQueue<Connection>>();
		used = new ConcurrentHashMap<Host, Semaphore>();
		shutdown = new AtomicBoolean(false);
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	@Override
	public Connection acquire(Host host) throws InterruptedException, IOException {
		return shutdown.get() ? null : getConnection(host);
	}

	@Override
	public boolean release(Connection connection) {
		boolean reuse = !shutdown.get() && connection.isReusable();
		if (reuse) {
			Host host = connection.getHost();
			Queue<Connection> queue = getFreeQueue(host);
			reuse = queue.offer(connection);
		}
		decreaseUsed(connection.getHost());
		return reuse;
	}

	@Override
	public void shutdown() {
		if (shutdown.compareAndSet(false, true)) {
			closeAllConnections();
			free.clear();
			used.clear();
		}
	}

	protected Connection createConnection(Host host) throws IOException {
		 SocketConnection connection = new SocketConnection(host);
		 connection.connect();
		 return connection;
	}
	
	private Connection getConnection(Host host) throws InterruptedException, IOException {
		Queue<Connection> queue = getFreeQueue(host);
		increaseUsed(host);
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

	private void increaseUsed(Host host) throws InterruptedException {
		getSemaphore(host).acquire();
	}

	private void decreaseUsed(Host host) {
		getSemaphore(host).release();
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

	private Semaphore getSemaphore(Host host) {
		Semaphore semaphore = used.get(host);
		if (semaphore == null) {
			semaphore = new Semaphore(maxConnectionsPerHost);
			Semaphore exist = used.putIfAbsent(host, semaphore);
			semaphore = exist == null ? semaphore : exist;
		}
		return semaphore;
	}
	
	@Override
	public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}
}
