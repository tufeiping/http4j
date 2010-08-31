package com.google.code.http.impl.conn;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import com.google.code.http.Connection;
import com.google.code.http.Host;
import com.google.code.http.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class SocketConnection extends AbstractConnection
		implements Connection {

	protected Socket socket;

	public SocketConnection(Host host) {
		super(host);
	}
	
	public SocketConnection(Host host, int timeout) {
		super(host, timeout);
	}

	@Override
	public void close() throws IOException {
		IOUtils.close(socket);
	}

	@Override
	public void connect() throws IOException {
		SocketAddress address = getSocketAddress(host);
		socket = createSocket();
		socket.connect(address, timeout);
	}
	
	protected Socket createSocket() {
		return new Socket();
	}

	protected SocketAddress getSocketAddress(Host host)
			throws UnknownHostException {
		int port = (host.getPort() < 0) ? host.getDefaultPort() : host.getPort();
		InetAddress address = getInetAddress(host);
		return new InetSocketAddress(address, port);
	}

	protected InetAddress getInetAddress(Host host) {
		// TODO get from DNS
		return null;
	}

	@Override
	public boolean isClosed() {
		return socket.isClosed();
	}

	@Override
	protected int read(ByteBuffer buffer) throws IOException {
		InputStream in = socket.getInputStream();
		int i = in.read(buffer.array());
		buffer.position(i);
		return i;
	}

	@Override
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	protected void flush() throws IOException {
		socket.getOutputStream().flush();
		// TODO record time
	}
	
	@Override
	protected void writeFirstByte(byte b) throws IOException {
		socket.getOutputStream().write(b);
		// TODO record time
	}

	@Override
	protected void write(byte[] m, int i, int j) throws IOException {
		socket.getOutputStream().write(m, i, j);
	}
}
