package com.google.code.http4j.client.impl.parsers;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Map.Entry;

import com.google.code.http4j.client.HttpHeader;
import com.google.code.http4j.client.impl.BasicHttpHeader;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HeadersParser extends AbstractParser<LinkedHashMap<String, HttpHeader>> {

	@Override
	protected LinkedHashMap<String, HttpHeader> doParsing(byte[] bytes) throws IOException {
		InputStream input = new ByteArrayInputStream(bytes);
		Properties properties = new Properties();
		properties.load(input);
		LinkedHashMap<String, HttpHeader> headerMap = new LinkedHashMap<String, HttpHeader>();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			HttpHeader header = createHeader(entry.getKey().toString(), entry.getValue().toString());
			headerMap.put(header.getCanonicalName(), header);
		}
		return headerMap;
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
