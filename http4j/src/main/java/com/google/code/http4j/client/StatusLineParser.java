package com.google.code.http4j.client;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface StatusLineParser {
	
	StatusLine parse(ByteBuffer buffer);
}
