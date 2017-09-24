package com.vdprime.vdlib.utils.inject;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Burak on 23.09.2017.
 */
@Retention (RUNTIME) @Target (TYPE)
public @interface Model
{}
