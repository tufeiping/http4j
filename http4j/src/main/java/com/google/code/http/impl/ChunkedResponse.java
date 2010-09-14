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
import java.nio.ByteBuffer;
import java.util.List;

import com.google.code.http.AbstractResponse;
import com.google.code.http.HTTP;
import com.google.code.http.Header;
import com.google.code.http.StatusLine;
import com.google.code.http.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class ChunkedResponse extends AbstractResponse {

	public ChunkedResponse(StatusLine statusLine, List<Header> headers,
			byte[] entitySource) throws IOException {
		super(statusLine, headers, entitySource);
	}

	@Override
	protected byte[] readEntity(byte[] entitySource) throws IOException {
		ByteBuffer buffer = ByteBuffer.wrap(entitySource);
		byte[] entityData = readEntity(buffer);
		if(buffer.hasRemaining()) {
			readTrailerHeaders(buffer);
		}
		return entityData;
	}
	
	private byte[] readEntity(ByteBuffer buffer) {
		ByteBuffer entityBuffer = ByteBuffer.allocate(buffer.capacity());
		byte[] data;
		while((data = IOUtils.getNextChunk(buffer)) != null) {
			entityBuffer.put(data);
		}
		entityBuffer.flip();
		data = new byte[entityBuffer.limit()];
		System.arraycopy(entityBuffer.array(), 0, data, 0, data.length);
		return data;
	}

	private void readTrailerHeaders(ByteBuffer buffer) throws IOException {
		byte[] headerBytes = IOUtils.extractByEnd(buffer, HTTP.CR, HTTP.LF, HTTP.CR, HTTP.LF);
		List<Header> trailers = new HeadersParser().parse(headerBytes);
		headers.addAll(trailers);
	}
}
