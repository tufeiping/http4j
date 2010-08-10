package com.google.code.http4j.client.impl.parsers;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface Parser <T> {
	
	T parse(InputStream in) throws IOException;
}
