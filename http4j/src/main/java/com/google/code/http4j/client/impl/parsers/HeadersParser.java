package com.google.code.http4j.client.impl.parsers;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.google.code.http4j.client.HttpHeader;
import com.google.code.http4j.client.impl.BasicHttpHeader;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HeadersParser extends AbstractParser<Map<String, HttpHeader>> {

	@Override
	protected Map<String, HttpHeader> doParsing(byte[] bytes) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
		String line = null;
		Map<String, HttpHeader> headerMap = new HashMap<String, HttpHeader>();
		while((line = reader.readLine()) != null) {
			addHeader(headerMap, parseHeader(line));
		}
		return headerMap;
	}
	
	protected void addHeader(Map<String, HttpHeader> headerMap, HttpHeader header) {
		if(null != header) {
			headerMap.put(header.getCanonicalName(), header);
		}
	}

	protected HttpHeader parseHeader(String line) {
		String[] strings = line.split(":");
		return createHeader(strings[0].trim(), strings[1].trim());
	}

	protected HttpHeader createHeader(String name, String value) {
		return new BasicHttpHeader(name, value);
	}

	@Override
	protected int getCapacity() {
		return 1000;
	}

	@Override
	protected byte[] getEndExpression() {
		return HEADERS_END;
	}
}
