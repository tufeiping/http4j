package com.google.code.http4j.client.impl;

import com.google.code.http4j.client.StatusLine;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicStatusLine implements StatusLine {

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

}
