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

import java.io.IOException;
import java.io.OutputStream;

import com.google.code.http4j.utils.MetricsRecorder;
import com.google.code.http4j.utils.ThreadLocalMetricsRecorder;

/**
 * This class does not totally matched the decorator, because only some of methods are delegated.
 * So it is used within this package.
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
class OutputStreamWrapper extends OutputStream {
	
	protected final OutputStream out;
	
	public OutputStreamWrapper(OutputStream out) {
		this.out = out;
	}

	public void flush() throws IOException {
		out.flush();
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		MetricsRecorder recorder = ThreadLocalMetricsRecorder.getInstance();
		recorder.getRequestTimer().start();
		out.write(b);
		recorder.getRequestTransportCounter().addAndGet((long) b.length);
	}
	
	@Deprecated
	public void write(int b) throws IOException {
		out.write(b);//never use
	}
}
