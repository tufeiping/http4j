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

package com.google.code.http4j.impl.conn;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.code.http4j.utils.Counter;
import com.google.code.http4j.utils.ThreadLocalMetricsRecorder;

/**
 * This class does not totally matched the decorator, because only some of methods are delegated.
 * So it is used within this package.
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
class InputStreamDecorator extends InputStream {

	protected final DataInputStream in;

	public InputStreamDecorator(InputStream in) {
		this.in = new DataInputStream(in);
	}

	public int read() throws IOException {
		int i = in.read();
		Counter<Long> counter = ThreadLocalMetricsRecorder.getInstance().getResponseTransportCounter();
		if (i != -1 && counter.addAndGet(1l) == 1) {// don't change logic order
			ThreadLocalMetricsRecorder.getInstance().getResponseTimer().start();
		}
		return i;
	}

	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	public int read(byte[] b, int off, final int len) throws IOException {
		int s = read(), c = len;// ensure metrics recorded.
		if(s == -1) {
			return -1;
		}
		b[off] = (byte) s;
		in.readFully(b, ++off, --c);
		ThreadLocalMetricsRecorder.getInstance().getResponseTransportCounter().addAndGet((long) c);
		return len;
	}
}
