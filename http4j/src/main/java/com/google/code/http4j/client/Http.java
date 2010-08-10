package com.google.code.http4j.client;

public interface Http {
	
	String HEAD = "HEAD";
	String GET = "GET";
	String POST = "POST";
	
	String PROTOCOL_HTTP = "http";
	String PROTOCOL_HTTPS = "https";
	
	byte CR = '\r';//13
	byte LF = '\n';//10
	byte BLANK = ' ';//32
	char BLANK_CHAR = (char) BLANK;
	String CRLF = "\r\n";
	
	byte[] BLANK_END = new byte[]{BLANK};
	byte[] STATUS_LINE_END = new byte[]{LF};
	byte[] HEADERS_END = new byte[]{CR, LF, CR, LF};
	
	float DEFAULT_HTTP_VERSION = 1.1f;
	String DEFAULT_HTTP_VERSION_STRING = "HTTP/" + DEFAULT_HTTP_VERSION;
	
	String HEADER_NAME_HOST = "Host";
	String HEADER_NAME_USER_AGENT = "User-Agent";
	String DEFAULT_USER_AGENT = "http4j (" + System.getProperty("os.name") + ")";
}
