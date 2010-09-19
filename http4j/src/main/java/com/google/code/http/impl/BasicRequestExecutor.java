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

package com.google.code.http.impl;

import java.io.IOException;
import java.io.OutputStream;

import com.google.code.http.Connection;
import com.google.code.http.ConnectionCache;
import com.google.code.http.CookieCache;
import com.google.code.http.Request;
import com.google.code.http.RequestExecutor;
import com.google.code.http.Response;
import com.google.code.http.metrics.ThreadLocalMetricsRecorder;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicRequestExecutor implements RequestExecutor {
	
	protected final ConnectionCache connectionCache;

	protected final CookieCache cookieCache;

	protected final ResponseParser responseParser;

	public BasicRequestExecutor(ConnectionCache connectionCache, CookieCache cookieCache, ResponseParser responseParser) {
		this.connectionCache = connectionCache;
		this.cookieCache = cookieCache;
		this.responseParser = responseParser;
	}

	@Override
	public Response execute(Request request) throws InterruptedException, IOException {
		ThreadLocalMetricsRecorder.getInstance().reset();
		Connection connection = connectionCache.acquire(request.getHost());
		try {
			send(connection, request);
			Response response = retrieveResponse(connection);
			cookieCache.set(request.getURI(), response.getHeaders());
			return response;
		} catch (IOException e) {
			connection.close();
			throw e;
		}
	}

	protected Response retrieveResponse(Connection connection) throws IOException {
		Response response = responseParser.parse(connection.getInputStream());
		ThreadLocalMetricsRecorder.responseStopped();
		connection.setReusable(response.getStatusLine().keepAlive());
		connectionCache.release(connection);
		return response;
	}

	protected void send(Connection connection, Request request) throws IOException {
		addCookie(request);
		OutputStream out = connection.getOutputStream();
		out.write(request.toMessage());
		out.flush();
		ThreadLocalMetricsRecorder.getInstance().getRequestTimer().stop();
	}

	protected void addCookie(Request request) {
		String cookie = cookieCache.get(request.getURI());
		if (null != cookie) {
			request.setCookie(cookie);
		}
	}
}
