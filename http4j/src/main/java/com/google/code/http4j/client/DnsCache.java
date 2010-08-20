package com.google.code.http4j.client;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface DnsCache extends Serializable {
	
	InetAddress getInetAddress(String host, byte[] ip) throws UnknownHostException;
	
	InetAddress getInetAddress(String host) throws UnknownHostException;
}
