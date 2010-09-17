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
import java.io.OutputStream;

import com.google.code.http.metrics.Counter;
import com.google.code.http.metrics.ThreadLocalMetricsRecorder;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class OutputStreamDecorator extends OutputStream {
	
	protected OutputStream out;
	
	protected Counter<Long> counter;
	
	public OutputStreamDecorator(OutputStream out) {
		this.out = out;
		counter = ThreadLocalMetricsRecorder.getInstance().getRequestTransportCounter();
	}

	public void close() throws IOException {
		out.close();
	}

	public void flush() throws IOException {
		out.flush();
	}

	public void write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}

	public void write(byte[] b, int off, int len) throws IOException {
		write(b[off++]);
		if(--len > 0) {
			counter.addAndGet((long) len);
			out.write(b, off, len);
		}
	}

	public void write(int b) throws IOException {
		out.write(b);
		if(counter.addAndGet(1l) == 1) {
			ThreadLocalMetricsRecorder.requestStarted();
		}
	}
}
