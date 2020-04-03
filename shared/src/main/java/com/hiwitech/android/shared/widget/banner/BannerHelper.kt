package com.hiwitech.android.shared.widget.banner

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.hiwitech.android.libs.internal.MainHandler.postDelayed
import com.hiwitech.android.mvvm.databinding.BindingCommand
import com.hiwitech.android.shared.ext.bindToSchedulers
import com.uber.autodispose.android.autoDispose
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class BannerHelper(
    val viewModel: ViewModel,
    private val items: ObservableArrayList<Any>
) {

    var state = SCROLL_STATE_IDLE

    var position = 1

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            state = newState
            val manager = (recyclerView.layoutManager as LinearLayoutManager)
            if (state == SCROLL_STATE_IDLE) {
                position = manager.findFirstVisibleItemPosition()
                if (position == 0) {
                    position = items.size - 2
                    manager.scrollToPositionWithOffset(position, 0)
                }
                if (position == items.size - 1) {
                    position = 1
                    manager.scrollToPositionWithOffset(1, 0)
                }
            }
        }
    }

    val recyclerCommand = BindingCommand<RecyclerView>(consumer = {
        this?.apply {
            onFlingListener = null
            PagerSnapHelper().attachToRecyclerView(this)
            this.addOnScrollListener(scrollListener)
            (this.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
            this.post {
                Flowable.interval(0, 2, TimeUnit.SECONDS)
                    .filter {
                        it != 0L
                    }
                    .bindToSchedulers()
                    .autoDispose(this)
                    .subscribe {
                        if (state == SCROLL_STATE_IDLE) {
                            this.smoothScrollToPosition(position++)
                        }
                    }
            }
        }
    })


    fun update(list: List<Any>) {
        items.clear()
        items.addAll(list)
        postDelayed(100) {
            items.add(list.size, list[0])
            items.add(0, list[list.size - 1])
            position = 1
        }
    }

}