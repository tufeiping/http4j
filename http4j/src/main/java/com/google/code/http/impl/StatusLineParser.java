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

import com.google.code.http.Parser;
import com.google.code.http.StatusLine;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class StatusLineParser implements Parser<StatusLine, byte[]> {

	@Override
	public StatusLine parse(byte[] s) {
		String line = new String(s);
		String[] fields = line.split("[ \t\r]+", 3);
		return new BasicStatusLine(fields[0], Integer.parseInt(fields[1]), fields[2]);
	}

	protected static class BasicStatusLine implements StatusLine {
		private static final long serialVersionUID = -5318592976726582472L;
		protected final String version;
		protected final int statusCode;
		protected final String reason;

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
		public boolean hasEntity() {
			return statusCode >= 200 
				&& statusCode != 204 
				&& statusCode != 205 
				&& statusCode != 304;
		}
	}
}
