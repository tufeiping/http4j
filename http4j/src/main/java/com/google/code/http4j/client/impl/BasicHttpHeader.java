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

import com.google.code.http4j.client.HttpHeader;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicHttpHeader extends NameValuePair implements HttpHeader {

	protected String canonicalName;

	public BasicHttpHeader(String name, String value) {
		super(name, value);
	}

	@Override
	public String getCanonicalName() {
		return null == canonicalName ? calculateCanonicalName() : canonicalName;
	}

	protected String calculateCanonicalName() {
		StringBuilder buffer = new StringBuilder(name);
		toUpperCase(buffer, 0);
		int i = buffer.indexOf("-") + 1;
		if (i > 0 && i < name.length()) {
			toUpperCase(buffer, i);
		}
		canonicalName = buffer.toString();
		return canonicalName;
	}

	protected void toUpperCase(StringBuilder buffer, int i) {
		char begin = buffer.charAt(i);
		if (Character.isLowerCase(begin)) {
			buffer.replace(i, i + 1, String.valueOf(Character.toUpperCase(begin)));
		}
	}

	@Override
	public String format() {
		return new StringBuilder(getCanonicalName()).append(":").append(value).toString();
	}
}
