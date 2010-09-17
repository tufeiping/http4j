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

package com.google.code.http.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.google.code.http.HTTP;

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
				socket.shutdownInput();
				socket.shutdownOutput();
				socket.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * @param buffer
	 * @return extended buffer, the capacity becomes double of original
	 */
	public static ByteBuffer extendBuffer(ByteBuffer buffer) {
		ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() << 1);
		transfer(buffer, newBuffer);
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
	 * Extract data from buffer by given end expression.e.g. <li>http4j [4j]
	 * -&gt; http</li> <li>http4j [4] -&gt; http</li> <li>http4j [ttp4j] -&gt; h
	 * </li> <li>http4j [http4j] -&gt; ""</li> <li>http4j [4g] -&gt; "http4j"</li>
	 * 
	 * @param buffer
	 * @param endExpression
	 *            minimum length of 1
	 * @return bytes
	 */
	public static byte[] extractByEnd(ByteBuffer buffer, byte... endExpression) {
		ByteBuffer valueHolder = ByteBuffer.allocate(buffer.limit());
		byte b;
		int count = 0;
		while (buffer.hasRemaining() && count < endExpression.length) {
			count = ((b = buffer.get()) == endExpression[count]) ? count + 1
					: 0;
			valueHolder.put(b);
		}
		valueHolder.position(valueHolder.position() - count);
		return extract(valueHolder);
	}

	/**
	 * Transfer data of src[0 - position] to dest buffer. And then clear the src
	 * buffer.
	 * 
	 * @see ByteBuffer#flip()
	 * @see ByteBuffer#put(ByteBuffer)
	 * @see ByteBuffer#clear()
	 * @param src
	 * @param dest
	 */
	public static void transfer(ByteBuffer src, ByteBuffer dest) {
		src.flip();
		dest.put(src);
		src.clear();
	}

	/**
	 * @param buffer
	 * @return next chunk, <code>null</code> if reaches the end of chunk body.
	 */
	public static byte[] getNextChunk(ByteBuffer buffer) {
		int size = getNextChunkSize(buffer);
		return size > 0 ? getNextChunk(buffer, size) : null;
	}

	static byte[] getNextChunk(ByteBuffer buffer, int size) {
		byte[] chunk = new byte[size];
		buffer.get(chunk);
		buffer.get(new byte[2]);// CRLF
		return chunk;
	}

	static int getNextChunkSize(ByteBuffer buffer) {
		byte[] chunkSize = extractByEnd(buffer, HTTP.CR, HTTP.LF);
		if (chunkSize.length > 0) {
			String s = new String(chunkSize);
			int end = s.indexOf(';');
			s = end < 0 ? s : s.substring(0, end);
			return Integer.parseInt(s.trim(), 16);
		}
		return 0;
	}

	public static byte[] convertBytes(byte[] entity, String charset)
			throws UnsupportedEncodingException {
		return new String(entity).getBytes(charset);
	}
}
