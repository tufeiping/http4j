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

import java.net.URL;

import com.google.code.http.Method;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class Get extends AbstractRequest {

	private static final long serialVersionUID = -7662562240040943759L;

	public Get(URL url) {
		super(url);
	}

	@Override
	protected Method getMethod() {
		return Method.GET;
	}

	@Override
	protected CharSequence formatBody() {
		return "";
	}

	@Override
	protected CharSequence formatURI() {
		return new StringBuilder(path).append('?').append(query);
	}
}
