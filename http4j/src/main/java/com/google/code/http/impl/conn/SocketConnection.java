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
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

import com.google.code.http.Connection;
import com.google.code.http.Host;
import com.google.code.http.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class SocketConnection extends AbstractConnection
		implements Connection {

	protected Socket socket;

	public SocketConnection(Host host) {
		this(host, 0);
	}
	
	public SocketConnection(Host host, int timeout) {
		super(host, timeout);
		socket = createSocket();
	}

	@Override
	public void close() throws IOException {
		IOUtils.close(socket);
	}

	@Override
	public void doConnect() throws IOException {
		SocketAddress address = getSocketAddress(host);
		socket.connect(address, timeout);
	}
	
	protected Socket createSocket() {
		return new Socket();
	}

	@Override
	public boolean isClosed() {
		return socket.isClosed();
	}

	@Override
	protected int read(ByteBuffer buffer) throws IOException {
		InputStream in = socket.getInputStream();
		int i = in.read(buffer.array());
		buffer.position(i);
		return i;
	}

	@Override
	protected void flush() throws IOException {
		socket.getOutputStream().flush();
	}
	
	@Override
	protected void writeFirstByte(byte b) throws IOException {
		socket.getOutputStream().write(b);
	}

	@Override
	protected void write(byte[] m, int i, int j) throws IOException {
		socket.getOutputStream().write(m, i, j);
	}

	@Override
	protected byte readFirstByte() throws IOException {
		int read = socket.getInputStream().read();
		if(read == -1) {
			IOUtils.close(socket);
			throw new IOException("Socket ends while reading the first byte.");
		}
		return (byte) read;
	}

	@Override
	protected int getReceiveBufferSize() throws SocketException {
		return socket.getReceiveBufferSize();
	}
}
