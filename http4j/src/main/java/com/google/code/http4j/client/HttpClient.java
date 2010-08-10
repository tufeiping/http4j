package com.google.code.http4j.client;

import java.io.IOException;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface HttpClient {
	
	HttpResponse submit(HttpRequest request) throws IOException;
}
