package com.google.code.http4j.client;

import com.google.code.http4j.client.impl.parsers.HttpResponseParser;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface HttpRequest extends HttpMessage {

	HttpHost getHost();
	
	/**
	 * Get the message of this request which will be sent from client to server
	 * 
	 * @return formatted data
	 */
	String getFormattedMessage();

	/**
	 * Get the HTTP response parser
	 * @return response parser
	 */
	HttpResponseParser getHttpResponseParser();
}
