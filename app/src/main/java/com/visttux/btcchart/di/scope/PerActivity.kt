package com.visttux.btcchart.di.scope

import javax.inject.Scope
import java.lang.annotation.Retention

import java.lang.annotation.RetentionPolicy.RUNTIME

@Scope
@Retention(RUNTIME)
annotation class PerActivity
