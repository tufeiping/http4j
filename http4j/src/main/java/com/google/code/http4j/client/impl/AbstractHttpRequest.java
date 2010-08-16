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

package com.google.code.http4j.client.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import com.google.code.http4j.client.HttpHost;
import com.google.code.http4j.client.HttpParameter;
import com.google.code.http4j.client.HttpRequest;
import com.google.code.http4j.client.impl.utils.URLFormatter;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public abstract class AbstractHttpRequest extends AbstractHttpMessage implements
		HttpRequest {

	private static final long serialVersionUID = 7128951961154306746L;

	protected final URL url;

	protected final HttpHost host;

	protected List<HttpParameter> parameters;

	public AbstractHttpRequest(String _url) throws MalformedURLException,
			UnknownHostException {
		super();
		url = URLFormatter.format(_url);
		host = new BasicHttpHost(url.getProtocol(), url.getHost(),url.getPort());
		addDefaultHeaders();
		initParameters();
	}

	protected void addDefaultHeaders() {
		addHeader(HEADER_NAME_HOST, url.getAuthority());
		addHeader(HEADER_NAME_USER_AGENT, DEFAULT_USER_AGENT);
	}

	@Override
	public void addParameter(String name, String... values) {
		for (String value : values) {
			addParameters(createHttpParameter(name, value));
		}
	}

	@Override
	public void addParameters(HttpParameter... _parameters) {
		for (HttpParameter parameter : _parameters) {
			this.parameters.add(parameter);
		}
	}

	/**
	 * Default behavior, post request should override this method and
	 * {@link #formatBody()}
	 * 
	 * @return
	 */
	protected String calculateURI() {
		return new StringBuilder(url.getPath()).append("?").append(formatParameters()).toString();
	}
	
	protected String getPath() {
		String path = url.getPath();
		return path.length() > 0 ? path : "/";
	}

	protected HttpParameter createHttpParameter(String name, String value) {
		return new BasicHttpParameter(name, value);
	}

	@Override
	public String format() {
		StringBuilder message = new StringBuilder(formatRequestLine());
		message.append(CRLF).append(formatHeaders());
		message.append(CRLF).append(CRLF).append(formatBody());
		return message.toString();
	}

	abstract protected String formatBody();

	protected String formatParameters() {
		StringBuilder message = new StringBuilder();
		for (HttpParameter parameter : parameters) {
			message.append("&").append(parameter.format());
		}
		return message.substring(1);
	}

	protected String formatRequestLine() {
		return new StringBuilder(getName()).append(BLANK_CHAR).append(getURI())
				.append(BLANK_CHAR).append(DEFAULT_HTTP_VERSION).toString();
	}

	@Override
	public HttpHost getHost() {
		return host;
	}
	
	abstract String getName();

	abstract protected String getURI();

	@Override
	public boolean hasEntity() {
		return true;
	}

	protected void initParameters() {
		parameters = new LinkedList<HttpParameter>();
		String query = url.getQuery();
		if(null != query) {
			String[] nameValuePairs = query.split("&");
			for (String nameValuePair : nameValuePairs) {
				String[] nameAndValue = nameValuePair.split("=");
				addParameter(nameAndValue[0], nameAndValue[1]);
			}
		}
	}
}
