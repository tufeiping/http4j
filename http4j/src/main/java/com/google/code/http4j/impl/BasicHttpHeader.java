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

import com.google.code.http4j.HttpHeader;
import com.google.code.http4j.NameValuePair;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.toLowerCase().hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicHttpHeader other = (BasicHttpHeader) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder(getCanonicalName()).append(":").append(value).toString();
	}
	
	
}
