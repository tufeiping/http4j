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

package com.google.code.http4j.client;

public interface Http {
	
	String HEAD = "HEAD";
	String GET = "GET";
	String POST = "POST";
	
	String PROTOCOL_HTTP = "http";
	String PROTOCOL_HTTPS = "https";
	
	byte CR = '\r';//13
	byte LF = '\n';//10
	byte BLANK = ' ';//32
	char BLANK_CHAR = (char) BLANK;
	String CRLF = "\r\n";

	String DEFAULT_HTTP_VERSION = "HTTP/1.1";
	
	String HEADER_NAME_HOST = "Host";
	String HEADER_NAME_USER_AGENT = "User-Agent";
	String HEADER_NAME_CONTENT_LENGTH = "Content-Length";
	String DEFAULT_USER_AGENT = "http4j (" + System.getProperty("os.name") + ")";
}
