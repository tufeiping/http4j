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
import com.google.code.http4j.StatusLine;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class IdentityResponse extends AbstractResponse {

	public IdentityResponse(StatusLine statusLine, List<Header> headers,
			InputStream in) throws IOException {
		super(statusLine, headers, in);
	}

	@Override
	protected byte[] readEntity(InputStream in) throws IOException {
		int length = Headers.getContentLength(headers);
		return length > 0 ? readEntity(in, length) : new byte[0];
	}
	
	protected byte[] readEntity(InputStream in, int length) throws IOException {
		byte[] e = new byte[length];
		if(in.read(e) < length) {
			throw new IOException("EOF at unexpected position.");
		}
		return e;
	}
}
