/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.util.file;

import java.net.URL;

import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.string.Strings;

public class ClasspathResourceFinder implements IResourceFinder
{

	private final String prefix;

	public ClasspathResourceFinder(String prefix)
	{
		if (Strings.isEmpty(prefix))
		{
			this.prefix = "";
		}
		else if (prefix.endsWith("/"))
		{
			this.prefix = prefix;
		}
		else
		{
			this.prefix = prefix + "/";
		}
	}

	@Override
	public IResourceStream find(Class<?> clazz, String path)
	{
		String fullPath = prefix + path;
		IResourceStream resourceStream;
		if (clazz != null)
		{
			resourceStream = getResourceStream(clazz.getClassLoader(), fullPath);
			if (resourceStream != null)
			{
				return resourceStream;
			}
		}

		// use context classloader when no specific classloader is set
		// (package resources for instance)
		resourceStream = getResourceStream(Thread.currentThread().getContextClassLoader(), fullPath);
		if (resourceStream != null)
		{
			return resourceStream;
		}

		// use Wicket classloader when no specific classloader is set
		resourceStream = getResourceStream(getClass().getClassLoader(), fullPath);
		if (resourceStream != null)
		{
			return resourceStream;
		}

		return null;
	}

	private IResourceStream getResourceStream(ClassLoader classLoader, String path)
	{
		URL url = classLoader.getResource(path);
		if (url != null)
		{
			return new UrlResourceStream(url);
		}
		else
		{
			return null;
		}
	}
}
