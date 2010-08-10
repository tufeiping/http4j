package com.google.code.http4j.client.impl;


import java.io.IOException;
import java.io.InputStream;

import com.google.code.http4j.client.HttpHost;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface Connection {
	
	HttpHost getHost();
	
	void connect() throws IOException;

	void send(String formattedMessage) throws IOException;

	InputStream getInputStream() throws IOException;
	
	void close();

	boolean isClosed();
}
