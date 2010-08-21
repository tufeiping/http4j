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
import java.net.InetAddress;
import java.net.URISyntaxException;

/**
 * Execute method only submit the request and get the message back without parsing.
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface HttpClient {
	
	void cacheDns(String host, InetAddress address);
	
	HttpResponse head(String url) throws IOException, URISyntaxException;
	
	HttpResponse get(String url) throws IOException, URISyntaxException;
	HttpResponse get(String url, boolean parseEntity) throws IOException, URISyntaxException;
	
	HttpResponse post(String url) throws IOException, URISyntaxException;
	HttpResponse post(String url, boolean parseEntity) throws IOException, URISyntaxException;
}
