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
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import com.google.code.http4j.client.HttpHost;
import com.google.code.http4j.client.impl.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class SocketConnection extends AbstractConnection {
	
	protected Socket socket;
	
	public SocketConnection(HttpHost host) {
		super(host);
	}
	
	@Override
	public void close() throws IOException {
		IOUtils.close(socket);
	}

	@Override
	public void connect() throws IOException {
		SocketAddress address = getSocketAddress(host);
		socket = createSocket();
		socket.connect(address);
	}
	
	protected Socket createSocket() {
		return new Socket();
	}

	@Override
	public void write(byte[] message) throws IOException {
		OutputStream out = socket.getOutputStream();
		out.write(message);
		out.flush();
	}

	@Override
	protected int read(ByteBuffer buffer) throws IOException {
		InputStream in = socket.getInputStream();
		int i = in.read(buffer.array());
		buffer.position(i);
		return i;
	}
	@Override
	public boolean isClosed() {
		return socket.isClosed();
	}
}
