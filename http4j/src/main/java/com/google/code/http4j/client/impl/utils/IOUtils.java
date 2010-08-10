package com.google.code.http4j.client.impl.utils;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class IOUtils {

	private IOUtils() {
	}
	
	public static void close(Socket socket) {
		if(null != socket && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}

	public static void close(Closeable closeable) {
		if(null != closeable) {
			try {
				closeable.close();
			} catch (IOException e) {
			}
		}
	}
}
