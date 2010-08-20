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
import java.net.UnknownHostException;

import com.google.code.http4j.client.Http;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HttpPost extends AbstractHttpRequest {

	private static final long serialVersionUID = 9154676257393105137L;

	public HttpPost(String url) throws MalformedURLException,
			UnknownHostException {
		super(url);
	}
	
	@Override
	protected String formatHeaders() {
		String parameters = formatParameters();
		addHeader(Http.HEADER_NAME_CONTENT_LENGTH, String.valueOf(parameters.length()));
		return super.formatHeaders();
	}
	
	@Override
	protected String formatBody() {
		return formatParameters();
	}

	@Override
	protected String getName() {
		return Http.POST;
	}

	@Override
	protected String getUriString() {
		return getPath();
	}

	@Override
	public boolean hasResponseEntity() {
		return true;
	}
}
