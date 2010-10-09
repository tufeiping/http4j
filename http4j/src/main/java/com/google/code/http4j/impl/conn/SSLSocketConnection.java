package com.google.code.http4j.impl.conn;

import com.google.code.http4j.Host;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class SSLSocketConnection extends SocketConnection {

	public SSLSocketConnection(Host host) {
		this(host, 0);
	}

	public SSLSocketConnection(Host host, int timeout) {
		super(host, timeout);
	}
	
	// TODO
}
