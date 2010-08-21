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
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.google.code.http4j.client.Connection;
import com.google.code.http4j.client.HttpHost;
import com.google.code.http4j.client.impl.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class SocketChannelConnection extends AbstractConnection implements Connection {

	protected SocketChannel channel;

	public SocketChannelConnection(HttpHost host) {
		super(host);
	}

	@Override
	public void close() {
		IOUtils.close(channel);
	}

	@Override
	public void connect() throws IOException {
		SocketAddress address = getSocketAddress(host);
		channel = SocketChannel.open(address);
	}

	@Override
	protected int read(ByteBuffer buffer) throws IOException {
		return channel.read(buffer);
	}

	@Override
	public boolean isClosed() {
		return !channel.isOpen();
	}

	@Override
	public void write(byte[] message) throws IOException {
		ByteBuffer buffer = ByteBuffer.wrap(message);
		channel.write(buffer);
	}
}
