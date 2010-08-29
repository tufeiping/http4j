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

import java.net.MalformedURLException;
import java.net.URL;

import com.google.code.http.Headers;
import com.google.code.http.Method;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class Post extends AbstractRequest {

	private static final long serialVersionUID = -9103163070992426384L;

	public Post(String url) throws MalformedURLException {
		super(url);
	}
	
	public Post(URL url) {
		super(url);
	}

	@Override
	protected Method getMethod() {
		return Method.POST;
	}

	@Override
	protected StringBuilder formatHeaders() {
		setHeader(Headers.CONTENT_LENGTH, String.valueOf(query.length()));
		return super.formatHeaders();
	}
	
	@Override
	protected CharSequence formatBody() {
		return query;
	}

	@Override
	protected CharSequence formatURI() {
		return path;
	}
}
