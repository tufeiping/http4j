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

package com.google.code.http.impl;

import java.io.IOException;
import java.util.List;

import com.google.code.http.Charset;
import com.google.code.http.Header;
import com.google.code.http.Headers;
import com.google.code.http.Response;
import com.google.code.http.StatusLine;
import com.google.code.http.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public abstract class AbstractResponse implements Response {
	
	protected StatusLine statusLine;
	
	protected List<Header> headers;
	
	protected byte[] entity;
	
	protected String charset;
	
	public AbstractResponse(StatusLine statusLine, List<Header> headers, byte[] entitySource) throws IOException {
		this.statusLine = statusLine;
		this.headers = headers;
		entity = null == entitySource ? null : readEntity(entitySource);
		charset = Headers.getCharset(headers);
		if(!Charset.DEFAULT.equalsIgnoreCase(charset)) {
			entity = null == entity ? null : IOUtils.convertBytes(entity, charset);
		}
	}
	
	abstract protected byte[] readEntity(byte[] entitySource) throws IOException;
	
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
}
