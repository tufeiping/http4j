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

package com.google.code.http4j;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Deprecated
public interface HttpMessage extends Formattable, Serializable {
	
	List<HttpHeader> getHeaders();
	
	List<HttpHeader> getHeaders(String name);
	
	HttpHeader getHeader(String name);
	
	void setHeader(String name, String value);
	
	void setHeader(HttpHeader header);
	
	void addHeaders(Collection<HttpHeader> headers);
	
	void addHeaders(HttpHeader... headers);
	
	void addHeader(String name, String value);

	void addHeader(HttpHeader header);
}
