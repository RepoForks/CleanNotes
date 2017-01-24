package co.zsmb.cleannotes.presentation.util

import android.support.annotation.IdRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

var RecyclerView.scrollPosition: Int
    get() {
        val manager = (layoutManager as LinearLayoutManager)
        return manager.findFirstVisibleItemPosition()
    }
    set(value) {
        if (value < adapter.itemCount) {
            scrollToPosition(value)
        }
    }

fun ViewGroup.inflate(@IdRes layout: Int): View
        = LayoutInflater.from(this.context).inflate(layout, this, false)
