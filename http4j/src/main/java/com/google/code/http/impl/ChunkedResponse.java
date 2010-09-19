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
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import com.google.code.http.HTTP;
import com.google.code.http.Header;
import com.google.code.http.StatusLine;

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
		ByteBuffer bf = ByteBuffer.allocate(2 << 13);
		byte[] data;
		while((data = IOUtils.getNextChunk(in)) != null) {
			bf = bf.hasRemaining() ? bf : IOUtils.extendBuffer(bf);
			bf.put(data);
		}
		bf.flip();
		data = new byte[bf.limit()];
		System.arraycopy(bf.array(), 0, data, 0, data.length);
		return data;
	}

	private void readTrailerHeaders(InputStream in) throws IOException {
		byte[] headerBytes = IOUtils.extractByEnd(in, HTTP.CR, HTTP.LF, HTTP.CR, HTTP.LF);
		List<Header> trailers = new HeadersParser().parse(headerBytes);
		headers.addAll(trailers);
	}
}
