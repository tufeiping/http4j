package com.google.code.http4j.client.impl;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

import com.google.code.http4j.client.impl.parsers.HttpHeadResponseParser;
import com.google.code.http4j.client.impl.parsers.HttpResponseParser;

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
	protected String formatBody() {
		return "";
	}

	@Override
	protected String formatRequestLine() {
		return new StringBuilder(HEAD).append(BLANK_CHAR).append(getURI()).append(BLANK_CHAR).append(DEFAULT_HTTP_VERSION).toString();
	}

	@Override
	HttpResponseParser createHttpResponseParser() {
		return new HttpHeadResponseParser();
	}
}
