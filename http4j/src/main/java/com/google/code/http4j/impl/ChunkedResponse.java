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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.google.code.http4j.HTTP;
import com.google.code.http4j.Header;
import com.google.code.http4j.StatusLine;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class ChunkedResponse extends AbstractResponse {

	public ChunkedResponse(StatusLine statusLine, List<Header> headers,
			InputStream in) throws IOException {
		super(statusLine, headers, in);
	}

	@Override
	protected byte[] readEntity(InputStream in) throws IOException {
		byte[] entityData = readBody(in);
		readTrailerHeaders(in);
		return entityData;
	}
	
	private byte[] readBody(InputStream in) throws IOException {
		ByteArrayOutputStream bf = new ByteArrayOutputStream();
		byte[] data;
		while((data = IOUtils.getNextChunk(in)) != null) {
			bf.write(data);
		}
		return bf.toByteArray();
	}

	private void readTrailerHeaders(InputStream in) throws IOException {
		byte[] headerBytes = IOUtils.extractByEnd(in, HTTP.CR, HTTP.LF, HTTP.CR, HTTP.LF);
		List<Header> trailers = new HeadersParser().parse(headerBytes);
		headers.addAll(trailers);
	}
}
