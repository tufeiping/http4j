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

import com.google.code.http4j.utils.MetricsRecorder;
import com.google.code.http4j.utils.ThreadLocalMetricsRecorder;

/**
 * This class does not totally matched the decorator, because only some of methods are delegated.
 * So it is used within this package.
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
class InputStreamWrapper extends InputStream {

	protected final DataInputStream in;

	public InputStreamWrapper(InputStream in) {
		this.in = new DataInputStream(in);
	}

	public int read() throws IOException {
		MetricsRecorder recorder = ThreadLocalMetricsRecorder.getInstance();
		int b = in.read();
		if (recorder.getResponseTransportCounter().addAndGet(1L) == 1) {
			recorder.getResponseTimer().start();
		}
		return b;
	}
	
	@Override
	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	// Only be called while getting next chunk,
	// so we do not need to worry about response timer start
	public int read(byte[] b, int off, int len) throws IOException {
		int read = super.read(b, off, len);
		ThreadLocalMetricsRecorder.getInstance().getResponseTransportCounter().addAndGet((long) read);
		return read;
	}
}
