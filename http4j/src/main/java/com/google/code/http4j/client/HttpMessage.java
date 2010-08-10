package com.google.code.http4j.client;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface HttpMessage extends Http, Serializable {
	
	public List<HttpHeader> getHeaders();
	
	void addHeaders(Collection<HttpHeader> headers);
	
	public void addHeaders(HttpHeader... headers);
	
	public void addHeader(String name, String value);

	void addHeader(HttpHeader header);
}
