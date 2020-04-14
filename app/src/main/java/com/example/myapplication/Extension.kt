package com.example.myapplication

import android.content.res.Resources
import kotlin.reflect.KProperty

fun Int.dp(): Float {
    return Resources.getSystem().displayMetrics.density * this + 0.5f
}

fun Float.dp(): Float {
    return Resources.getSystem().displayMetrics.density * this + 0.5f
}

fun Double.dp(): Float {
    return (Resources.getSystem().displayMetrics.density * this + 0.5f).toFloat()
}

fun Int.sp(): Float {
    return Resources.getSystem().displayMetrics.scaledDensity * this + 0.5f
}

fun Float.sp(): Float {
    return Resources.getSystem().displayMetrics.scaledDensity * this + 0.5f
}

fun Double.sp(): Float {
    return (Resources.getSystem().displayMetrics.scaledDensity * this + 0.5f).toFloat()
}

