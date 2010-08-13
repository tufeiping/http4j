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

package com.google.code.http4j.client.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.code.http4j.client.HeadersParser;
import com.google.code.http4j.client.HttpHeader;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicHeadersParser implements HeadersParser {

	@Override
	public List<HttpHeader> parse(byte[] source) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(source)));
		String line = null;
		List<HttpHeader> headers = new ArrayList<HttpHeader>();
		String[] array = null;
		while((line = reader.readLine()) != null) {
			array = line.split("[ \t]*:[ \t]*", 2);
			headers.add(createHttpHeader(array[0], array[1]));
		}
		return headers;
	}

	protected HttpHeader createHttpHeader(String name, String value) {
		return new BasicHttpHeader(name, value);
	}
}
