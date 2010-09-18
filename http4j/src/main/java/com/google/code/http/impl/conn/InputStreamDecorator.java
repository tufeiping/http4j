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

import com.google.code.http.metrics.Counter;
import com.google.code.http.metrics.ThreadLocalMetricsRecorder;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class InputStreamDecorator extends InputStream {
	
	protected InputStream in;
	
	protected final Counter<Long> counter;
	
	public InputStreamDecorator(InputStream in) {
		this.in = in;
		counter = ThreadLocalMetricsRecorder.getInstance().getResponseTransportCounter();
	}

	public int available() throws IOException {
		return in.available();
	}

	public void close() throws IOException {
		in.close();
	}

	public void mark(int readlimit) {
		in.mark(readlimit);
	}

	public boolean markSupported() {
		return in.markSupported();
	}

	public int read() throws IOException {
		int i = in.read();
		if(i != -1 && counter.addAndGet(1l) == 1) {// don't change logic order
			ThreadLocalMetricsRecorder.responseStarted();
		}
		return i;
	}

	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	public int read(byte[] b, int off, int len) throws IOException {
		int i = read();
		if(i == -1) {
			return -1;
		}
		b[off++] = (byte) i;
		if(--len > 0) { 
			counter.addAndGet((long) len);
			return in.read(b, off, len) + 1;
		}
		return 1;
	}

	public void reset() throws IOException {
		in.reset();
		counter.reset();
	}

	public long skip(long n) throws IOException {
		counter.addAndGet(n);
		return in.skip(n);
	}
}
