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

import com.google.code.http.Connection;
import com.google.code.http.ConnectionCache;
import com.google.code.http.CookieCache;
import com.google.code.http.Request;
import com.google.code.http.RequestExecutor;
import com.google.code.http.Response;
import com.google.code.http.impl.conn.ConnectionPool;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicRequestExecutor implements RequestExecutor {
	
	protected ConnectionCache connectionCache;

	protected CookieCache cookieCache;

	protected ResponseParser responseParser;

	public BasicRequestExecutor() {
		connectionCache = createConnectionCache();
		cookieCache = createCookieCache();
		responseParser = createResponseParser();
	}

	protected ResponseParser createResponseParser() {
		return new ResponseParser();
	}

	protected CookieCache createCookieCache() {
		return new CookieStoreAdapter();
	}

	protected ConnectionCache createConnectionCache() {
		return new ConnectionPool();
	}

	@Override
	public Response execute(Request request) throws InterruptedException, IOException {
		Connection connection = connectionCache.acquire(request.getHost());
		try {
			connection.write(prepareMessage(request));
			Response response = retrieveResponse(connection);
			cookieCache.set(request.getURI(), response.getHeaders());
			return response;
		} catch (IOException e) {
			connection.close();
			throw e;
		}
	}

	protected Response retrieveResponse(Connection connection) throws IOException {
		byte[] reply = connection.read();
		connectionCache.release(connection);
		Response response = responseParser.parse(reply);
		return response;
	}

	protected byte[] prepareMessage(Request request) {
		addCookie(request);
		String message = request.toMessage();
		return message.getBytes();
	}

	protected void addCookie(Request request) {
		String cookie = cookieCache.get(request.getURI());
		if (null != cookie) {
			request.setCookie(cookie);
		}
	}
}
