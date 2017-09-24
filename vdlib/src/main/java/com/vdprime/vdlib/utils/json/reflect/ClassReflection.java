/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.vdprime.vdlib.utils.json.reflect;

import java.lang.reflect.Modifier;

/** Utilities for Class reflection.
 * @author nexsoftware */
public final class ClassReflection {

	/** Returns the Class object associated with the class or interface with the supplied string name. */
	static public Class forName (String name) throws ReflectionException
    {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			throw new ReflectionException("Class not found: " + name, e);
		}
	}

	
	/** Determines if the class or interface represented by first Class parameter is either the same as, or is a superclass or
	 * superinterface of, the class or interface represented by the second Class parameter. */
	static public boolean isAssignableFrom (Class c1, Class c2) {
		return c1.isAssignableFrom(c2);
	}

	/** Returns true if the class or interface represented by the supplied Class is a member class. */
	static public boolean isMemberClass (Class c) {
		return c.isMemberClass();
	}

	/** Returns true if the class or interface represented by the supplied Class is a static class. */
	static public boolean isStaticClass (Class c) {
		return Modifier.isStatic(c.getModifiers());
	}
	

	/** Creates a new instance of the class represented by the supplied Class. */
	static public <T> T newInstance (Class<T> c) throws ReflectionException
    {
		try {
			return c.newInstance();
		}
		catch (InstantiationException e) {
			throw new ReflectionException("Could not instantiate instance of class: " + c.getName(), e);
		}
		catch (IllegalAccessException e) {
			throw new ReflectionException("Could not instantiate instance of class: " + c.getName(), e);
		}
	}

	/** Returns a {@link Constructor} that represents the constructor for the supplied class which takes the supplied parameter
	 * types. */
	static public Constructor getDeclaredConstructor (Class c, Class... parameterTypes) throws
                                                                                        ReflectionException
    {
		try {
			return new Constructor(c.getDeclaredConstructor(parameterTypes));
		} catch (SecurityException e) {
			throw new ReflectionException("Security violation while getting constructor for class: " + c.getName(), e);
		} catch (NoSuchMethodException e) {
			throw new ReflectionException("Constructor not found for class: " + c.getName(), e);
		}
	}

	/** Returns an array of {@link Field} objects reflecting all the fields declared by the supplied class. */
	static public Field[] getDeclaredFields (Class c) {
		java.lang.reflect.Field[] fields = c.getDeclaredFields();
		Field[]                   result = new Field[fields.length];
		for (int i = 0, j = fields.length; i < j; i++) {
			result[i] = new Field(fields[i]);
		}
		return result;
	}
}
