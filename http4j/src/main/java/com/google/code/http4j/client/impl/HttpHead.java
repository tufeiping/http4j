package com.google.code.http4j.client.impl;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HttpHead extends AbstractHttpRequest {

	private static final long serialVersionUID = -2339520317320114115L;
	
	public HttpHead(String url) throws MalformedURLException,
			UnknownHostException {
		super(url);
	}

	@Override
	protected String formatEntity() {
		return "";
	}

	@Override
	protected String formatRequestLine() {
		return new StringBuilder(HEAD).append(BLANK_CHAR).append(url.getFile()).append(BLANK_CHAR).append(DEFAULT_HTTP_VERSION_STRING).toString();
	}

}
