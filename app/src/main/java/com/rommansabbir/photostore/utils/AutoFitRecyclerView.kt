package com.rommansabbir.photostore.utils

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.rommansabbir.photostore.base.executeBodyOrReturnNull
import kotlin.math.max

/*Ref: https://github.com/chiuki/android-recyclerview/blob/master/app/src/main/java/com/sqisland/android/recyclerview/AutofitRecyclerView.java*/
class AutoFitRecyclerView : RecyclerView {
    var manager: CustomGridLayoutManager? = null
    private var columnWidth = -1

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val attrsArray = intArrayOf(
                R.attr.columnWidth
            )
            val array: TypedArray = context.obtainStyledAttributes(attrs, attrsArray)
            columnWidth = array.getDimensionPixelSize(0, -1)
            array.recycle()
        }
        manager = CustomGridLayoutManager(getContext(), 3 /*Default*/)
        layoutManager = manager
    }

    /**
     * Update layout manager span count.
     * Set a new instance of [CustomGridLayoutManager] to the [AutoFitRecyclerView].
     * Notify client with the [detach] callback to detach the current adapter
     * and a set a new instance.
     *
     * @param value Span count.
     * @param detach Callback to detach the adapter.
     */
    fun updateSpanCount(value: Int, detach: () -> Unit) {
        executeBodyOrReturnNull {
            manager = CustomGridLayoutManager(context, value)
            layoutManager = manager
            detach.invoke()
        }
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (columnWidth > 0) {
            val spanCount = max(1, measuredWidth / columnWidth)
            manager!!.spanCount = spanCount
        }
    }
}