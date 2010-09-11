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

import com.google.code.http.HTTP;
import com.google.code.http.Header;
import com.google.code.http.Headers;
import com.google.code.http.Parser;
import com.google.code.http.Response;
import com.google.code.http.StatusLine;
import com.google.code.http.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public final class ResponseParser implements Parser<Response, byte[]> {

	@Override
	public Response parse(byte[] s) throws IOException {
		ByteBuffer buffer = ByteBuffer.wrap(s);
		StatusLine line = parseStatusLine(buffer);
		List<Header> headers = parseHeaders(buffer);
		byte[] entity = line.hasEntity() ? parseEntity(buffer) : null;
		return createResponse(line, headers, entity);
	}

	private Response createResponse(StatusLine line, List<Header> headers, byte[] entity) {
		String encoding = Headers.getValueByName(headers, Headers.TRANSFER_ENCODING);
		return Headers.CHUNKED.equals(encoding) ? new ChunkedResponse(line, headers, entity) : new IdentityResponse(line, headers, entity);
	}

	private byte[] parseEntity(ByteBuffer buffer) {
		int position = buffer.position();
		int length = buffer.limit() - position;
		byte[] result = new byte[length];
		System.arraycopy(buffer.array(), 0, result, 0, length);
		return result;
	}

	private List<Header> parseHeaders(ByteBuffer buffer) throws IOException {
		byte[] source = IOUtils.extractByEnd(buffer, HTTP.CR, HTTP.LF, HTTP.CR, HTTP.LF);
		Parser<List<Header>, byte[]> parser = new HeadersParser();
		return parser.parse(source);
	}

	private StatusLine parseStatusLine(ByteBuffer buffer) throws IOException {
		byte[] source = IOUtils.extractByEnd(buffer, HTTP.LF);
		Parser<StatusLine, byte[]> parser = new StatusLineParser();
		return parser.parse(source);
	}

}
