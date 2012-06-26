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

import java.io.IOException;

import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;


/**
 * Maintains a list of folders as a path.
 * 
 * @author Jonathan Locke
 */
public class Path implements IResourceFinder
{
	private Folder folder;

	/**
	 * Constructor
	 * 
	 * @param folder
	 *            A single folder to add to the path
	 */
	public Path(final Folder folder)
	{
		if (!folder.exists())
		{
			throw new IllegalArgumentException("Folder " + folder + " does not exist");
		}
		this.folder = folder;
	}

	/**
	 * @see org.apache.wicket.util.file.IResourceFinder#find(Class, String)
	 */
	@Override
	public IResourceStream find(final Class<?> clazz, final String pathname)
	{
		final File file = new File(folder, pathname);

		if (file.exists())
		{
			return new FileResourceStream(file);
		}
		else
		{
			return null;
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		try
		{
			return "[folder = " + folder.getCanonicalPath() + "]";
		}
		catch (IOException e)
		{
			return "[folder]";
		}
	}
}
