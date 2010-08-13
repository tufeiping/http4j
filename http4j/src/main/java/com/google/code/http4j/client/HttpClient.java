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

package com.google.code.http4j.client;

import java.io.IOException;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface HttpClient {

	/**
	 * Submit request and parse the response.
	 * @param request
	 * @return response
	 * @throws IOException
	 */
	HttpResponse submit(HttpRequest request) throws IOException;

	/**
	 * This execution is normally the same with {@link #submit(HttpRequest)},
	 * but would not parse the response. This will be usefull in stress or
	 * load test.
	 * 
	 * @param request
	 * @return bytes
	 * @throws IOException
	 */
	byte[] execute(HttpRequest request) throws IOException;

	HttpResponse head(String url) throws IOException;
	byte[] executeHead(String url) throws IOException;
}
