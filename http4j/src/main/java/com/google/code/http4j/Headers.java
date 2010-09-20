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

package com.google.code.http4j;

import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public class Headers {

	public static final String HOST = "Host";
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String USER_AGENT = "User-Agent";
	public static final String ACCEPT = "Accept";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String REQUEST_COOKIE = "Cookie";
	public static final String RESPONSE_COOKIE = "Set-Cookie";
	public static final String TRANSFER_ENCODING = "Transfer-Encoding";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_ENCODING = "Content-Encoding";
	public static final String CONNECTION = "Connection";
	
	public static List<Header> filter(List<Header> headers, String name) {
		List<Header> list = new LinkedList<Header>();
		for (Header header : headers) {
			if (header.getName().equalsIgnoreCase(name)) {
				list.add(header);
			}
		}
		return list;
	}

	public static String[] getValuesByName(List<Header> headers, String name) {
		headers = filter(headers, name);
		String[] values = null;
		if (!headers.isEmpty()) {
			values = new String[headers.size()];
			for (int i = 0, len = values.length; i < len; i++) {
				values[i] = headers.get(i).getValue();
			}
		}
		return values;
	}
	
	public static String getValueByName(List<Header> headers, String name) {
		String[] values = getValuesByName(headers, name);
		return null == values ? null : values[0];
	}

	public static boolean isChunked(List<Header> headers) {
		String encoding = Headers.getValueByName(headers, TRANSFER_ENCODING);
		encoding = null == encoding ? "" : encoding.toLowerCase();
		return encoding.startsWith("chunked") ? true : false;
	}
	
	public static boolean isGzipped(List<Header> headers) {
		String encoding = Headers.getValueByName(headers, CONTENT_ENCODING);
		encoding = null == encoding ? "" : encoding.toLowerCase();
		return encoding.startsWith("gzip") ? true : false;
	}

	public static int getContentLength(List<Header> headers) {
		String length = getValueByName(headers, CONTENT_LENGTH);
		return null == length ? 0 : Integer.parseInt(length);
	}
	
	public static String getCharset(List<Header> headers) {
		String contentType = getValueByName(headers, CONTENT_TYPE);
		return null == contentType ? null : getCharset(contentType);
	}
	
	public static String getCharset(String contentType) {
		String pattern = "charset=";
		int index = contentType.indexOf(pattern);
		return index < 0 ? null : contentType.substring(index + pattern.length()).trim();
	}
}
