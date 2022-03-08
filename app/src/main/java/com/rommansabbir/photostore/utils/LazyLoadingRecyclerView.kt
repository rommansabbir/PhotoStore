package com.rommansabbir.photostore.utils

import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView
import com.rommansabbir.photostore.base.handlerPostDelayed

class LazyLoadingRecyclerView private constructor() {
    private var isScrolling = false
    private var currentItems = 0
    private var totalItems: Int = 0
    private var scrollOutItems: Int = 0

    private lateinit var recyclerView: AutoFitRecyclerView
    private lateinit var layoutManager: SpeedyLinearLayoutManager

    private var loadMoreListener: Listener? = null

    companion object {
        fun getInstance(): LazyLoadingRecyclerView {
            return LazyLoadingRecyclerView()
        }

        private var HANDLER_DELAY: Long = 500

        /**
         * Set delay time for handler, must be in Millis
         *
         * @param value, Value to be set in [Long]
         */
        fun setHandlerDelayTime(value: Long) {
            HANDLER_DELAY = value
        }
    }


    fun registerScrollListener(recyclerView: AutoFitRecyclerView, listener: Listener) {
        this.recyclerView = recyclerView
        if (recyclerView.layoutManager == null) {
            throw RuntimeException("LazyLoadingRecyclerView: Layout manager can not be null")
        }
        this.layoutManager = recyclerView.layoutManager!! as CustomGridLayoutManager
        this.loadMoreListener = listener
        recyclerView.addOnScrollListener(rvScrollListener)
    }

    fun removeListener() {
        recyclerView.removeOnScrollListener(rvScrollListener)
        this.loadMoreListener = null
    }

    private val rvScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            handlerPostDelayed(HANDLER_DELAY) {
                currentItems = layoutManager.childCount
                totalItems = layoutManager.itemCount
                scrollOutItems = layoutManager.findFirstVisibleItemPosition()
                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false
                    loadMoreListener?.loadMore()
                }
            }
        }
    }

    interface Listener {
        fun loadMore()
    }
}