package com.vdprime.vdlib.utils.inject;
import android.support.annotation.IdRes;
import android.support.annotation.Keep;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Burak on 15.09.2017.
 */
@Keep @Target (METHOD) @Retention (RUNTIME) public @interface OnLongClick
{
    @IdRes int value();
}