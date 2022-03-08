package com.rommansabbir.photostore.utils

import android.content.Context
import android.util.AttributeSet

class CustomGridLayoutManager : SpeedyLinearLayoutManager {
    constructor(context: Context, spanCount: Int) : super(context, spanCount)
    constructor(
        context: Context,
        spanCount: Int,
        orientation: Int,
        reverseLayout: Boolean
    ) : super(
        context,
        spanCount,
        orientation,
        reverseLayout
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)
}