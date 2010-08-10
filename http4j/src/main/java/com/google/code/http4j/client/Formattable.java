package com.google.code.http4j.client;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface Formattable {
	/**
	 * Get the message of this request which will be sent from client to server
	 * 
	 * @return formatted data
	 */
	String format();
}
