package com.google.code.http4j.client.impl;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import com.google.code.http4j.client.HttpHeader;
import com.google.code.http4j.client.HttpHost;
import com.google.code.http4j.client.HttpRequest;
import com.google.code.http4j.client.impl.utils.URLFormatter;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public abstract class AbstractHttpRequest extends AbstractHttpMessage implements
		HttpRequest {

	private static final long serialVersionUID = 7128951961154306746L;
	
	protected final URL url;
	
	protected final HttpHost host;

	public AbstractHttpRequest(String _url) throws MalformedURLException, UnknownHostException {
		super();
		url = URLFormatter.format(_url);
		host = new BasicHttpHost(url.getProtocol(), url.getHost(), url.getPort());
		addDefaultHeaders();
	}

	protected void addDefaultHeaders() {
		addHeader(HEADER_NAME_HOST, url.getAuthority());
		addHeader(HEADER_NAME_USER_AGENT, DEFAULT_USER_AGENT);
	}

	@Override
	public String getFormattedMessage() {
		StringBuilder message = new StringBuilder(formatRequestLine());
		message.append(CRLF).append(formatHeaders());
		message.append(CRLF).append(CRLF).append(formatEntity());
		return message.toString();
	}
	
	@Override
	public HttpHost getHost() {
		return host;
	}
	
	protected String formatHeaders() {
		StringBuilder message = new StringBuilder();
		for(HttpHeader header:headerMap.values()) {
			message.append(header.toCanonicalString()).append(CRLF);
		}
		return message.toString();
	}
	
	abstract protected String formatEntity();
	abstract protected String formatRequestLine();
}
