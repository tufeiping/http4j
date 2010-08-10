package com.google.code.http4j.client.impl;

import com.google.code.http4j.client.HttpResponse;
import com.google.code.http4j.client.StatusLine;


/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicHttpResponse extends AbstractHttpMessage implements HttpResponse {

	private static final long serialVersionUID = 7490197120431297469L;
	
	protected StatusLine statusLine;
	
	protected String entity;
	
	public BasicHttpResponse(StatusLine statusLine, String entity) {
		this.statusLine = statusLine;
		this.entity = entity;
	}

	@Override
	public String getEntity() {
		return entity;
	}

	@Override
	public StatusLine getStatusLine() {
		return statusLine;
	}

	@Override
	public String format() {
		StringBuilder message = new StringBuilder(formatStatusLine());
		message.append(CRLF).append(formatHeaders());
		message.append(CRLF).append(CRLF).append(formatEntity());
		return message.toString();
	}

	protected String formatEntity() {
		return entity;
	}

	protected String formatStatusLine() {
		return statusLine.format();
	}

	@Override
	public String toString() {
		return format();
	}
}
