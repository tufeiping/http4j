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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.http4j.client.Http;
import com.google.code.http4j.client.HttpHost;
import com.google.code.http4j.client.impl.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class SocketConnection implements Connection {

	private static final Logger logger = LoggerFactory.getLogger(SocketConnection.class);

	protected Socket socket;
	protected HttpHost host;

	public SocketConnection(HttpHost host) {
		socket = new Socket();
		this.host = host;
	}

	@Override
	public void close() {
		IOUtils.close(socket);
	}

	@Override
	public void connect() throws IOException {
		InetSocketAddress address = getSocketAddress(host);
		socket.connect(address);
	}

	@Override
	public HttpHost getHost() {
		return host;
	}

	@Override
	public byte[] read() throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(2 << 13);
		InputStream stream = socket.getInputStream();
		byte b;
		while ((b = (byte) stream.read()) != -1) {
			if(!buffer.hasRemaining()) {
				buffer = extendBuffer(buffer);
			}
			buffer.put(b);
		}
		buffer.flip();
		return buffer.array();
	}
	
	private ByteBuffer extendBuffer(ByteBuffer buffer) {
		ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() << 1);
		newBuffer.put(buffer);
		return newBuffer;
	}

	protected InetSocketAddress getSocketAddress(HttpHost host)
			throws UnknownHostException {
		int port = host.getPort();
		port = (port < 0) ? (host.getProtocol().equalsIgnoreCase(
				Http.PROTOCOL_HTTP) ? 80 : 443) : port;
		return new InetSocketAddress(host.getInetAddress(), port);
	}

	@Override
	public boolean isClosed() {
		return socket.isClosed();
	}

	@Override
	public void write(byte[] message) throws IOException {
		logger.debug("HTTP Request >>\r\n{}", new String(message));
		OutputStream out = socket.getOutputStream();
		out.write(message);
		out.flush();
	}
}
