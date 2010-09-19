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

package com.google.code.http4j.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.google.code.http4j.Header;
import com.google.code.http4j.Headers;
import com.google.code.http4j.Response;
import com.google.code.http4j.StatusLine;
import com.google.code.http4j.metrics.Metrics;
import com.google.code.http4j.metrics.ThreadLocalMetricsRecorder;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public abstract class AbstractResponse implements Response {
	
	protected final StatusLine statusLine;
	
	protected final List<Header> headers;
	
	protected final byte[] entity;
	
	protected final String charset;

	protected Metrics metrics;
	
	public AbstractResponse(StatusLine statusLine, List<Header> headers, InputStream in) throws IOException {
		this.statusLine = statusLine;
		this.headers = headers;
		entity = statusLine.hasEntity() ? readEntity(in) : null;
		charset = Headers.getCharset(headers);
	}
	
	abstract protected byte[] readEntity(InputStream in) throws IOException;
	
	@Override
	public StatusLine getStatusLine() {
		return statusLine;
	}

	@Override
	public List<Header> getHeaders() {
		return headers;
	}

	@Override
	public byte[] getEntity() {
		return entity;
	}
	
	@Override
	public String getCharset() {
		return charset;
	}
	
	@Override
	public Metrics getMetrics() {
		return metrics;
	}
	
	@Override
	public void captureMetrics() {
		metrics = ThreadLocalMetricsRecorder.getInstance().captureMetrics();
	}
}
