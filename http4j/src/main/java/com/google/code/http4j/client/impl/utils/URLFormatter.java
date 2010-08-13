/**
 * Copyright (C) 2010 Zhang, Guilin <guilin.zhang@hotmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.http4j.client.impl.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class URLFormatter {
	
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
		return new URL(s);
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
}
