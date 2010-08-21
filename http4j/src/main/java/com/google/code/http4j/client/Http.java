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
	String COOKIE_SPLITTER = ";";

	String DEFAULT_HTTP_VERSION = "HTTP/1.1";
	String DEFAULT_USER_AGENT = "http4j (" + System.getProperty("os.name") + ")";
	String DEFAULT_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
	
	String HEADER_NAME_HOST = "Host";
	String HEADER_NAME_USER_AGENT = "User-Agent";
	String HEADER_NAME_CONTENT_LENGTH = "Content-Length";
	String HEADER_NAME_RESPONSE_COOKIE = "Set-Cookie";
	String HEADER_NAME_REQUEST_COOKIE = "Cookie";
	String HEADER_NAME_ACCEPT = "Accept";
	String HEADER_NAME_ACCEPT_CHARSET = "Accept-Charset";
	
	String ISO_8859_1 = "ISO-889-1";
	String DEFAULT_CHARSET = ISO_8859_1;
	String UTF_8 = "UTF-8";
	
}
