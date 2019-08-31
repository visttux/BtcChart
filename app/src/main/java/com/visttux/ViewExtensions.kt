package com.visttux

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun View.getColor(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this.context, resId)
}