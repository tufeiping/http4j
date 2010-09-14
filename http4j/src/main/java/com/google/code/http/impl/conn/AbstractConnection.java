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
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import com.google.code.http.Connection;
import com.google.code.http.DnsCache;
import com.google.code.http.Host;
import com.google.code.http.metrics.ThreadLocalMetricsRecorder;
import com.google.code.http.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public abstract class AbstractConnection implements Connection {

	protected Host host;

	protected int timeout;
	
	protected boolean reusable;

	public AbstractConnection(Host host) {
		this(host, 0);
	}

	public AbstractConnection(Host host, int timeout) {
		this.host = host;
		this.timeout = timeout;
		this.reusable = true;
		ThreadLocalMetricsRecorder.connectionCreated();
	}

	abstract protected int read(ByteBuffer buffer) throws IOException;

	abstract protected byte readFirstByte() throws IOException;

	// before sending start event
	abstract protected void writeFirstByte(byte b) throws IOException;

	// sending event
	abstract protected void write(byte[] m, int i, int j) throws IOException;

	// before sending stop event
	abstract protected void flush() throws IOException;

	abstract protected void doConnect() throws IOException;

	@Override
	public final byte[] read() throws IOException {
		byte b = readFirstByte();
		ThreadLocalMetricsRecorder.responseStarted();
		ByteBuffer buffer = ByteBuffer.allocate(2 << 13);
		ByteBuffer extended = ByteBuffer.allocate(buffer.capacity() << 1);
		while (read(buffer) == buffer.capacity()) {
			// Increasing buffer's capacity reduces the chance to get here
			extended = IOUtils.ensureSpace(buffer, extended);
			IOUtils.transfer(buffer, extended);
		}
		byte[] data = IOUtils.extract(extended.position() == 0 ? buffer : extended);
		extended = ByteBuffer.allocate(data.length + 1).put(b).put(data);
		ThreadLocalMetricsRecorder.responseStopped(extended.capacity());
		return extended.array();
	}

	@Override
	public final void write(byte[] m) throws IOException {
		writeFirstByte(m[0]);
		ThreadLocalMetricsRecorder.requestStarted();
		if (m.length > 1) {
			write(m, 1, m.length - 1);
		}
		flush();
		ThreadLocalMetricsRecorder.requestStopped(m.length);
	}

	@Override
	public final void connect() throws IOException {
		ThreadLocalMetricsRecorder.connectStarted();
		doConnect();
		ThreadLocalMetricsRecorder.connectStopped();
	}

	protected SocketAddress getSocketAddress(Host host)
			throws UnknownHostException {
		int port = (host.getPort() < 0) ? host.getDefaultPort() : host.getPort();
		InetAddress address = DnsCache.getAddress(host.getName());
		return new InetSocketAddress(address, port);
	}
	
	@Override
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public Host getHost() {
		return host;
	}
	
	@Override
	public void setReusable(boolean reusable) {
		this.reusable = reusable;
	}
	
	@Override
	public boolean isReusable() {
		return reusable && !isClosed();
	}
}
