package com.vdprime.vdlib.utils.inject;
/**
 * Created by Burak on 14.09.2017.
 */
import android.support.annotation.IdRes;
import android.support.annotation.Keep;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Burak on 13.09.2017.
 * OnCheck( id ) void onCheckChange(boolean isChecked,CompoundButton button)
 */
@Keep @Target (METHOD) @Retention (RUNTIME) public @interface OnCheck
{
    @IdRes int value();
}
