package com.google.code.http4j.client;

import java.util.List;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface HttpResponse extends HttpMessage {
	
	StatusLine getStatusLine();
	
	List<HttpHeader> getHeaders();
	
	String getContent();
}
