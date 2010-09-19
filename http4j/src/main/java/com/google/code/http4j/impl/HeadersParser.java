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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.google.code.http4j.Header;
import com.google.code.http4j.Parser;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HeadersParser implements Parser<List<Header>, byte[]> {
	
	@Override
	public List<Header> parse(byte[] source) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(source)));
		String line = null;
		List<Header> headers = new LinkedList<Header>();
		String[] array = null;
		while((line = reader.readLine()) != null) {
			array = line.split("[ \t]*:[ \t]*", 2);
			headers.add(createHeader(array[0], array[1]));
		}
		return headers;
	}

	protected Header createHeader(String name, String value) {
		return new CanonicalHeader(name, value);
	}
}
