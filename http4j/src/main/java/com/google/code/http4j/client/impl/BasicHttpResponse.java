package com.google.code.http4j.client.impl;

import com.google.code.http4j.client.HttpResponse;
import com.google.code.http4j.client.StatusLine;


/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicHttpResponse extends AbstractHttpMessage implements HttpResponse {

	private static final long serialVersionUID = 7490197120431297469L;
	
	protected StatusLine statusLine;
	
	protected String content;
	
	public BasicHttpResponse(StatusLine statusLine, String content) {
		this.statusLine = statusLine;
		this.content = content;
	}

	@Override
	public String getEntity() {
		return content;
	}

	@Override
	public StatusLine getStatusLine() {
		return statusLine;
	}
}
