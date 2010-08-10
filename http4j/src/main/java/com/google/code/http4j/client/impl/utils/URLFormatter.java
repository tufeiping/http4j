package com.google.code.http4j.client.impl.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class URLFormatter {
	
	private URLFormatter() {
	}
	
	/**
	 * Get standard URL by given string, ensure protocol and port are set. e.g.
	 * <li>www.google.com --&gt; http://www.google.com:80</li>
	 * <li>http://www.google.com --&gt; http://www.google.com:80</li>
	 * <li>https://www.google.com --&gt; https://www.google.com:443</li>
	 * <li>http://www.google.com/search?q=http4j --&gt; http://www.google.com:80/search?q=http4j</li>
	 * @param s
	 * @return url
	 * @throws MalformedURLException
	 */
	public static URL format(String s) throws MalformedURLException {
		s = ensureProtocol(s);
		URL url = ensurePort(s);
		return url;
	}
	
	public static URL setPort(URL url, int port) throws MalformedURLException {
		return new URL(buildURLString(url.getProtocol(), url.getHost(), port, url.getFile()));
	}
	
	public static String buildURLString(String protocol, String host, int port, String file) {
		StringBuilder s = new StringBuilder(protocol);
		s.append("://").append(host).append(":").append(port).append(file);
		return s.toString();
	}

	static String ensureProtocol(String s) {
		if (!s.contains("://")) {
			s = "http://" + s;
		}
		return s;
	}

	static URL ensurePort(String s) throws MalformedURLException {
		URL url = new URL(s);
		int port = url.getPort();
		if (port == -1) {
			port = url.getProtocol().length() == 4 ? 80 : 443;// http vs https
			url = setPort(url, port);
		}
		return url;
	}
}
