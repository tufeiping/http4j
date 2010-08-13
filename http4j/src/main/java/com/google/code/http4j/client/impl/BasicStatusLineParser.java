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

import com.google.code.http4j.client.StatusLine;
import com.google.code.http4j.client.StatusLineParser;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicStatusLineParser implements StatusLineParser {

	@Override
	public StatusLine parse(byte[] source) {
		String line = new String(source);
		String[] fields = line.split("[ ]+", 3);
		return createStatusLine(fields[0], Integer.parseInt(fields[1]), fields[2]);
	}

	protected StatusLine createStatusLine(String version, int statusCode,
			String reason) {
		return new BasicStatusLine(version, statusCode, reason);
	}
}
