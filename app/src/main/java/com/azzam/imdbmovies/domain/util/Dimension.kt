package com.azzam.imdbmovies.domain.util

import android.content.Context

object Dimension {

    fun dpFromPx(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    fun pxFromDp(context: Context, dp: Float): Float {
        return (dp * context.resources.displayMetrics.density)
    }
}