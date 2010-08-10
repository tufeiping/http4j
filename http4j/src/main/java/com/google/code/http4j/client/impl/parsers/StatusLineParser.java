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
	protected StatusLine doParsing(byte[] source) throws IOException {
		String line = new String(source);
		String[] strings = line.split("[ '\t']+", 3);
		return createStatusLine(strings[0], Integer.parseInt(strings[1]), strings[2]);
	}
	
	protected StatusLine createStatusLine(String version, int responseCode, String reason) {
		return new BasicStatusLine(version, responseCode, reason);
	}
}
