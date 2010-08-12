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

import com.google.code.http4j.client.Http;
import com.google.code.http4j.client.StatusLine;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicStatusLine implements StatusLine, Http {

	String version;
	int statusCode;
	String reason;

	public BasicStatusLine(String version, int responseCode, String reason) {
		this.version = version;
		this.statusCode = responseCode;
		this.reason = reason;
	}

	@Override
	public String getReason() {
		return reason;
	}

	@Override
	public int getStatusCode() {
		return statusCode;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public String format() {
		return new StringBuilder(version).append(BLANK_CHAR)
				.append(statusCode).append(BLANK_CHAR)
				.append(reason).toString();
	}
}
