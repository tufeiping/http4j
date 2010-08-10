package com.google.code.http4j.client.impl.parsers;


import java.util.List;

import com.google.code.http4j.client.HttpHeader;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HeadersParser extends AbstractParser<List<HttpHeader>> {

	@Override
	protected List<HttpHeader> doParsing(byte[] bytes) {
		return null;
	}

	@Override
	protected int getCapacity() {
		return 1000;
	}

	@Override
	protected byte[] getEndExpression() {
		return null;
	}
}
