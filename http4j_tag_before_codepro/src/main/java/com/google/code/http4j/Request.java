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

import java.net.URI;


/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface Request extends Message {
	
	Host getHost();
	
	URI getURI();
	
	/**
	 * Add the parameter with specified name and values.
	 * name=values[0](&name=values[1] ... values[n])*
	 * 
	 * @param name
	 * @param values
	 */
	void addParameter(String name, String... values);

	void setCookie(String value);
	
	/**
	 * Set the header with specified name and value. Replace the header with same
	 * name if it exists.
	 * 
	 * @param name
	 * @param value
	 */
	void setHeader(String name, String value);
}
