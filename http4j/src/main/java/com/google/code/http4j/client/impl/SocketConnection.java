package com.google.code.http4j.client.impl;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.http4j.client.Http;
import com.google.code.http4j.client.HttpHost;
import com.google.code.http4j.client.impl.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class SocketConnection implements Connection {
	
	private static final Logger logger = LoggerFactory.getLogger(SocketConnection.class);
	
	protected Socket socket;
	protected HttpHost host;
	
	public SocketConnection(HttpHost host) {
		socket = new Socket();
		this.host = host;
	}
	
	@Override
	public InputStream getInputStream() throws IOException {
		return socket.getInputStream();
	}

	@Override
	public void send(String formattedMessage) throws IOException {
		logger.debug("HTTP Request >>\r\n", formattedMessage);
		OutputStream out = socket.getOutputStream();
		out.write(formattedMessage.getBytes());
		out.flush();
	}

	@Override
	public void close() {
		IOUtils.close(socket);
	}

	@Override
	public void connect() throws IOException {
		InetSocketAddress address = getSocketAddress(host);
		socket.connect(address);
	}
	
	protected InetSocketAddress getSocketAddress(HttpHost host) throws UnknownHostException {
		int port = host.getPort();
		port = (port < 0) ? (host.getProtocol().equalsIgnoreCase(Http.PROTOCOL_HTTP) ? 80 : 443) : port;
		return new InetSocketAddress(host.getInetAddress(), port);
	}

	@Override
	public HttpHost getHost() {
		return host;
	}

	@Override
	public boolean isClosed() {
		return socket.isClosed();
	}
}
