package com.google.code.http4j.client.impl;

import com.google.code.http4j.client.Http;
import com.google.code.http4j.client.StatusLine;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicStatusLine implements StatusLine, Http {

	String version;
	int responseCode;
	String reason;

	public BasicStatusLine(String version, int responseCode, String reason) {
		this.version = version;
		this.responseCode = responseCode;
		this.reason = reason;
	}

	@Override
	public String getReason() {
		return reason;
	}

	@Override
	public int getResponseCode() {
		return responseCode;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public String format() {
		return new StringBuilder(version).append(BLANK_CHAR)
				.append(responseCode).append(BLANK_CHAR)
				.append(reason).toString();
	}
}
