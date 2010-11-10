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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.google.code.http4j.Charset;
import com.google.code.http4j.HTTP;
import com.google.code.http4j.Header;
import com.google.code.http4j.Headers;
import com.google.code.http4j.Message;
import com.google.code.http4j.Response;
import com.google.code.http4j.StatusLine;
import com.google.code.http4j.utils.IOUtils;
import com.google.code.http4j.utils.Metrics;

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
	
	protected String redirectLocation;

	public AbstractResponse(StatusLine statusLine, List<Header> headers,
			InputStream in) throws IOException {
		this.statusLine = statusLine;
		this.headers = headers;
		entity = statusLine.hasEntity() ? downloadEntity(in) : null;
		charset = determinCharset();
		log();
	}

	abstract protected byte[] readEntity(InputStream in) throws IOException;

	@Override
	public void output(OutputStream out) throws IOException {
		String contentType = Headers.getContentType(headers);
		Message message = getMessage(contentType);
		message.output(out);
	}

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
	public boolean isConnectionReusable() {
		String version = statusLine.getVersion();
		if(HTTP.HTTP_1_1.equalsIgnoreCase(version)) {
			if(!Headers.isChunked(headers) && Headers.getContentLength(headers) < 0) {
				return false;
			}
			String connection = Headers.getConnectionHeaderValue(headers);
			if(null != connection) {
				if("keep-alive".equalsIgnoreCase(connection)) {
					return true;
				}
				if("close".equalsIgnoreCase(connection)) {
					return false;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean needRedirect() {
		return statusLine.needRedirect() && null != getRedirectLocation();
	}
	
	@Override
	public String getRedirectLocation() {
		if(null == redirectLocation) {
			redirectLocation = Headers.getRedirectLocation(headers);
		}
		return redirectLocation;
	}

	@Override
	public Metrics getMetrics() {
		return metrics;
	}

	@Override
	public void setMetrics(Metrics metrics) {
		this.metrics = metrics;
	}
	
	protected Message getMessage(String contentType) {
		boolean isText = isText(contentType);
		return isText ? new TextMessage() : new BinaryMessage();
	}
	
	private boolean isText(String contentType) {
		return null != contentType && ((contentType.startsWith("text") || contentType.startsWith("application/x-javascript")));
	}
	
	private byte[] downloadEntity(InputStream in) throws IOException {
		byte[] original = readEntity(in);
		return Headers.isGzipped(headers) ? IOUtils.unGzip(original) : original;
	}

	private String determinCharset() {
		String encoding = Headers.getCharset(headers);
		encoding = null == encoding ? guessCharset() : encoding;
		return encoding.toUpperCase();
	}

	private String guessCharset() {
		if(null == entity || entity.length == 0) {
			return Charset.DEFAULT;
		}
		ByteArrayInputStream in = new ByteArrayInputStream(entity);
		try {
			IOUtils.extractByEnd(in, "charset=".getBytes());// skip
			byte[] charsets = IOUtils.extractByEnd(in, (byte) '"');
			return charsets.length == 0 ? Charset.DEFAULT : new String(charsets);
		} catch (IOException e) {
			return Charset.DEFAULT;
		}
	}
	
	private void log() {
		StringBuilder buf = new StringBuilder(statusLine.toString());
		buf.append(Headers.toString(headers)).append(HTTP.CRLF);
		HTTP.LOGGER.debug("Response:\r\n{}", buf.toString());
	}
	
	protected class TextMessage implements Message {
		@Override
		public void output(OutputStream out) throws IOException {
			String content = new String(entity, charset);
			out.write(content.getBytes());
			out.flush();
		}
	}
	
	protected class BinaryMessage implements Message {
		@Override
		public void output(OutputStream out) throws IOException {
			out.write(entity);
			out.flush();
		}
	}
}
