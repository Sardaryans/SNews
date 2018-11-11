package com.solo.snews.listeners

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.solo.snews.adapters.NewsAdapter
import android.support.v4.os.HandlerCompat.postDelayed



abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {
    private var mPreviousTotal = 0
    private var isLoading = false
    private var pastVisibleItems: Int = 0


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val manager = recyclerView.layoutManager as StaggeredGridLayoutManager

        val visibleItemCount = manager.childCount
        val totalItemCount = manager.itemCount
        val firstVisibleItems = manager.findFirstVisibleItemPositions(null)
        if (firstVisibleItems != null && firstVisibleItems.isNotEmpty()) {
            pastVisibleItems = firstVisibleItems[0]
        }

        if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading) {
            isLoading = true
            onLoadMore()
        } else {
            isLoading = false
        }
    }

    abstract fun onLoadMore()
}
