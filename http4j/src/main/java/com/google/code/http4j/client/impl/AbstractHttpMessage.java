package com.google.code.http4j.client.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.code.http4j.client.HttpHeader;
import com.google.code.http4j.client.HttpMessage;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public abstract class AbstractHttpMessage implements HttpMessage {

	private static final long serialVersionUID = 3025166196954976484L;
	
	protected Map<String, HttpHeader> headerMap;
	
	public AbstractHttpMessage() {
		this.headerMap = createHeaderMap();
	}

	public List<HttpHeader> getHeaders() {
		return new ArrayList<HttpHeader>(headerMap.values());
	}
	
	@Override
	public void addHeaders(Collection<HttpHeader> headers) {
		for(HttpHeader header:headers) {
			addHeader(header);
		}
	}
	
	@Override
	public void addHeaders(HttpHeader... headers) {
		for(HttpHeader header:headers) {
			addHeader(header);
		}
	}
	
	@Override
	public void addHeader(String name, String value) {
		addHeader(createHeader(name, value));
	}
	
	@Override
	public void addHeader(HttpHeader header) {
		headerMap.put(header.getCanonicalName(), header);
	}
	
	protected HttpHeader createHeader(String name, String value) {
		return new BasicHttpHeader(name, value);
	}
	
	protected Map<String, HttpHeader> createHeaderMap() {
		return new LinkedHashMap<String, HttpHeader>();
	}
}
