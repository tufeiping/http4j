package com.google.code.http4j.client;

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
}
