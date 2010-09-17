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

import com.google.code.http.metrics.ThreadLocalMetricsRecorder;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class InputStreamDecorator extends InputStream {
	
	protected InputStream in;
	
	protected int count;
	
	public InputStreamDecorator(InputStream in) {
		this.in = in;
	}

	public int available() throws IOException {
		return in.available();
	}

	public void close() throws IOException {
		in.close();
	}

	public boolean equals(Object obj) {
		return in.equals(obj);
	}

	public int hashCode() {
		return in.hashCode();
	}

	public void mark(int readlimit) {
		in.mark(readlimit);
	}

	public boolean markSupported() {
		return in.markSupported();
	}

	public int read() throws IOException {
		if(++count == 1) {
			ThreadLocalMetricsRecorder.responseStarted();
		}
		return in.read();
	}

	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	public int read(byte[] b, int off, int len) throws IOException {
		b[off++] = (byte) read();
		count += --len;
		return in.read(b, off, len);
	}

	public void reset() throws IOException {
		in.reset();
	}

	public long skip(long n) throws IOException {
		return in.skip(n);
	}

	public String toString() {
		return in.toString();
	}
}
