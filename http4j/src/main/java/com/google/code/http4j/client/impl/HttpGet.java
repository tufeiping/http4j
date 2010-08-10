package com.google.code.http4j.client.impl;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

import com.google.code.http4j.client.impl.parsers.HttpResponseParser;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HttpGet extends AbstractHttpRequest {

	private static final long serialVersionUID = -4509278701186547565L;

	public HttpGet(String url) throws MalformedURLException,
			UnknownHostException {
		super(url);
	}

	@Override
	protected String formatEntity() {
		return "";
	}

	@Override
	protected String formatRequestLine() {
		// TODO
		return "";
	}

	@Override
	HttpResponseParser createHttpResponseParser() {
		return null;
	}
}
