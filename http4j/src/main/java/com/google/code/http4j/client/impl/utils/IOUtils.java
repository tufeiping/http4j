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

package com.google.code.http4j.client.impl.utils;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class IOUtils {

	public static void close(Closeable closeable) {
		if (null != closeable) {
			try {
				closeable.close();
			} catch (IOException e) {
			}
		}
	}

	public static void close(Socket socket) {
		if (null != socket && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}

	public static ByteBuffer extendBuffer(ByteBuffer buffer) {
		ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() << 1);
		fill(buffer, newBuffer);
		return newBuffer;
	}

	/**
	 * Extract buffer data from 0 to the position after flip.
	 * 
	 * @param buffer
	 * @return bytes
	 */
	public static byte[] extract(ByteBuffer buffer) {
		byte[] data = new byte[buffer.position()];
		System.arraycopy(buffer.array(), 0, data, 0, data.length);
		return data;
	}

	/**
	 * Extract data from buffer by given end expression.e.g.
	 * <li>http4j [4j] -&gt; http</li>
	 * <li>http4j [4] -&gt; http</li>
	 * <li>http4j [ttp4j] -&gt; h</li>
	 * <li>http4j [http4j] -&gt; ""</li>
	 * <li>http4j [4g] -&gt; "http4j"</li>
	 * @param buffer
	 * @param endExpression minimum length of 1
	 * @return bytes
	 */
	public static byte[] extractByEnd(ByteBuffer buffer, byte... endExpression) {
		ByteBuffer valueHolder = ByteBuffer.allocate(buffer.limit());
		byte b;
		int count = 0;
		while(buffer.hasRemaining() && count < endExpression.length) {
			count = ((b = buffer.get()) == endExpression[count]) ? count + 1 : 0;
			valueHolder.put(b);
		}
		valueHolder.position(valueHolder.position() - count);
		return extract(valueHolder);
	}
	
	public static void fill(ByteBuffer src, ByteBuffer dest) {
		src.flip();
		dest.put(src);
		src.clear();
	}
}
