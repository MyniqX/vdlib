package com.vdprime.vdlib.utils.inject;
import android.support.annotation.Keep;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Burak on 14.09.2017.
 */
@Keep
@Retention (RUNTIME) @Target (FIELD)
public @interface BindDB
{
}
