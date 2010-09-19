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
import java.io.InputStream;
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
	 * Extract data from buffer by given end expression.e.g. 
	 * <li>http4j [4j] -&gt; http</li>
	 * <li>http4j [4] -&gt; http</li>
	 * <li>http4j [ttp4j] -&gt; h</li>
	 * <li>http4j [http4j] -&gt; ""</li>
	 * <li>http4j [4g] -&gt; "http4j"</li>
	 * 
	 * @param buffer
	 * @param endExpression
	 *            minimum length of 1
	 * @return bytes
	 */
	public static byte[] extractByEnd(ByteBuffer buffer, byte... endExpression) {
		ByteBuffer bf = ByteBuffer.allocate(buffer.limit());
		byte b;
		int count = 0;
		while (buffer.hasRemaining() && count < endExpression.length) {
			count = ((b = buffer.get()) == endExpression[count]) ? count + 1 : 0;
			bf.put(b);
		}
		bf.position(bf.position() - count);
		return extract(bf);
	}
	
	public static byte[] extractByEnd(InputStream in, byte... endExpression) throws IOException {
		ByteBuffer bf = ByteBuffer.allocate(2 << 12);
		int count = 0;
		byte b;
		while (count < endExpression.length && (b = (byte) in.read()) != -1) {
			count = (b == endExpression[count]) ? count + 1 : 0;
			bf = bf.hasRemaining() ? bf : extendBuffer(bf);
			bf.put(b);
		}
		bf.position(bf.position() - count);
		return extract(bf);
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
	 * @param in
	 * @return next chunk, <code>null</code> if reaches the end of chunk body.
	 * @throws IOException 
	 */
	public static byte[] getNextChunk(InputStream in) throws IOException {
		int size = getNextChunkSize(in);
		return size > 0 ? getNextChunk(in, size) : null;
	}

	static byte[] getNextChunk(InputStream in, int size) throws IOException {
		byte[] chunk = new byte[size];
		if(in.read(chunk) < size || in.read(new byte[2]) < 2)// CRLF
			throw new IOException("EOF at unexpected position.");
		return chunk;
	}

	static int getNextChunkSize(InputStream in) throws IOException {
		byte[] chunkSize = extractByEnd(in, HTTP.CR, HTTP.LF);
		if (chunkSize.length > 0) {
			String s = new String(chunkSize);
			int end = s.indexOf(';');
			s = end < 0 ? s : s.substring(0, end);
			return Integer.parseInt(s.trim(), 16);
		}
		return 0;
	}
}
