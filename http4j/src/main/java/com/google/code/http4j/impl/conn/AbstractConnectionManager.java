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
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.code.http4j.Connection;
import com.google.code.http4j.ConnectionManager;
import com.google.code.http4j.Host;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public abstract class AbstractConnectionManager implements ConnectionManager {
	
	protected static final int MAX_CONNECTION_PER_HOST = 2;
	
	protected AtomicBoolean shutdown;
	
	protected int maxConnectionsPerHost;
	
	protected AbstractConnectionManager(int maxConnectionPerHost) {
		this.maxConnectionsPerHost = maxConnectionPerHost;
	}
	
	@Override
	public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	@Override
	public final void shutdown() {
		if (shutdown.compareAndSet(false, true)) {
			doShutdown();
		}
	}
	
	protected Connection createConnection(Host host) throws IOException {
		 SocketConnection connection = new SocketConnection(host);
		 connection.connect();
		 return connection;
	}

	abstract protected void doShutdown();

}
