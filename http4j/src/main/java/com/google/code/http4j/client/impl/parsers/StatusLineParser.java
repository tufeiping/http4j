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

package com.google.code.http4j.client.impl.parsers;


import java.io.IOException;

import com.google.code.http4j.client.StatusLine;
import com.google.code.http4j.client.impl.BasicStatusLine;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class StatusLineParser extends AbstractParser<StatusLine> {

	protected static final String PROTOCOL = "HTTP";
	protected static final float MINIMUM_VERSION = 1.0f;
	protected static final int MINIMUM_STATUS_CODE = 0;
	protected static final String MINIMUM_PROTOCOL_VERSION = PROTOCOL + "/" + MINIMUM_VERSION;
	protected static final String MINIMUM_LINE = MINIMUM_PROTOCOL_VERSION + BLANK + MINIMUM_STATUS_CODE + BLANK;
	
	@Override
	protected byte[] getEndExpression() {
		return STATUS_LINE_END;
	}
	
	@Override
	protected int getCapacity() {
		return 100;
	}

	@Override
	protected StatusLine parse(byte[] source) throws IOException {
		String line = new String(source);
		String[] strings = line.split("[ '\t']+", 3);
		return createStatusLine(strings[0], Integer.parseInt(strings[1]), strings[2]);
	}
	
	protected StatusLine createStatusLine(String version, int responseCode, String reason) {
		return new BasicStatusLine(version, responseCode, reason);
	}
}
