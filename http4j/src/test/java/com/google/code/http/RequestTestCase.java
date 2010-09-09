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

package com.google.code.http;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.code.http.impl.AbstractRequest;
import com.google.code.http.impl.BasicHost;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public abstract class RequestTestCase {
	
	/*
	 * m should not consider default headers message
	 */
	protected void assertion(String url, String m) throws MalformedURLException, URISyntaxException {
		Request r = createRequest(url);
		Assert.assertEquals(r.toMessage(), m);
	}
	
	protected String getDefaultHeaderString() {
		return "User-Agent:" 
		+ AbstractRequest.DEFAULT_USER_AGENT
		+ "\r\nAccept:" + AbstractRequest.DEFAULT_ACCEPT
		+ "\r\n";
	}
	
	@Test
	public void getHost() throws MalformedURLException, URISyntaxException {
		Request request = createRequest("http://www.google.com");
		Assert.assertEquals(request.getHost(), new BasicHost("http", "www.google.com", -1));
	}
	
	abstract protected Request createRequest(String url) throws MalformedURLException, URISyntaxException;
}
