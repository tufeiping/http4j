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
import java.util.List;

import com.google.code.http.HTTP;
import com.google.code.http.Header;
import com.google.code.http.Headers;
import com.google.code.http.Parser;
import com.google.code.http.Response;
import com.google.code.http.StatusLine;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public final class ResponseParser implements Parser<Response, InputStream> {

	@Override
	public Response parse(InputStream in) throws IOException {
		StatusLine line = parseStatusLine(in);
		List<Header> headers = parseHeaders(in);
		return createResponse(line, headers, in);
	}

	private Response createResponse(StatusLine line, List<Header> headers, InputStream in) throws IOException {
		return Headers.isChunked(headers) ? new ChunkedResponse(line, headers, in) : new IdentityResponse(line, headers, in);
	}

	private List<Header> parseHeaders(InputStream in) throws IOException {
		byte[] source = IOUtils.extractByEnd(in, HTTP.CR, HTTP.LF, HTTP.CR, HTTP.LF);
		Parser<List<Header>, byte[]> parser = new HeadersParser();
		return parser.parse(source);
	}

	private StatusLine parseStatusLine(InputStream in) throws IOException {
		byte[] line = IOUtils.extractByEnd(in, HTTP.CR, HTTP.LF);
		Parser<StatusLine, byte[]> parser = new StatusLineParser();
		return parser.parse(line);
	}

}
