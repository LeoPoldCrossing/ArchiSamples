package com.example.archimvp.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by LeoPoldCrossing on 2017/4/11.
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInfoUrl {
}
