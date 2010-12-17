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

import com.google.code.http4j.Header;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public class CanonicalHeader implements Header {

	private static final long serialVersionUID = -6078020990085780510L;

	protected final String name;

	protected final String value;

	public CanonicalHeader(String name, String value) {
		this.name = toCanonical(name);
		this.value = value;
	}
	
	protected static String toCanonical(String headerName) {
		StringBuilder buffer = new StringBuilder(headerName.toLowerCase());
		toUpperCase(buffer, 0);
		int i = buffer.indexOf("-") + 1;
		if (i > 0 && i < headerName.length()) {
			toUpperCase(buffer, i);
		}
		return buffer.toString();
	}

	protected static void toUpperCase(StringBuilder builder, int i) {
		char c = builder.charAt(i);
		builder.replace(i, i + 1, String.valueOf(Character.toUpperCase(c)));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return new StringBuilder(name).append(':').append(value).toString();
	}
}
