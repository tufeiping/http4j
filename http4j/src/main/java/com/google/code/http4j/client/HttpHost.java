package com.google.code.http4j.client;

import java.net.InetAddress;


/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface HttpHost extends Http {
	
	String getProtocol();
	
	String getHostName();
	
	int getPort();
	
	InetAddress getInetAddress();
}
