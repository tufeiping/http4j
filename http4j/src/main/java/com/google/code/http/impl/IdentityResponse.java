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

import java.util.List;

import com.google.code.http.AbstractResponse;
import com.google.code.http.Header;
import com.google.code.http.StatusLine;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class IdentityResponse extends AbstractResponse {

	public IdentityResponse(StatusLine statusLine, List<Header> headers,
			byte[] entitySource) {
		super(statusLine, headers, entitySource);
	}

	@Override
	protected byte[] readEntity(byte[] entitySource) {
		return null;
	}

	
}
