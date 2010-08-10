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

	protected String getCanonicalName() {
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
	public String toCanonicalString() {
		return new StringBuilder(getCanonicalName()).append(":").append(value).toString();
	}
}
