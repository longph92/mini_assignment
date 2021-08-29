package com.example.dd_mini_assignment.screen.base

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseLifeCycleRecyclerAdapter<H : BaseLifeCycleViewHolder> constructor(
        private val data: ObservableArrayList<*>? = null,
        private val recyclerView: RecyclerView,
        private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<H>() {

    init {
        data?.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<*>>() {
            @SuppressLint("NotifyDataSetChanged")
            override fun onChanged(sender: ObservableList<*>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(
                    sender: ObservableList<*>,
                    positionStart: Int,
                    itemCount: Int
            ) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }

            override fun onItemRangeMoved(
                    sender: ObservableList<*>,
                    fromPosition: Int,
                    toPosition: Int,
                    itemCount: Int
            ) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeInserted(
                    sender: ObservableList<*>,
                    positionStart: Int,
                    itemCount: Int
            ) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeChanged(
                    sender: ObservableList<*>,
                    positionStart: Int,
                    itemCount: Int
            ) {
                notifyItemRangeChanged(positionStart, itemCount)
            }
        })

        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onDestroy() {
                recyclerView.let { parent ->
                    val childCount = parent.childCount
                    for (i in 0 until childCount) {
                        parent.getChildAt(i)?.let {
                            (parent.getChildViewHolder(it) as H).onStop()
                        }
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                recyclerView.run {
                    if (layoutManager is LinearLayoutManager) {
                        val first = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        val last = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        if (first in 0..last)
                            for (i in first..last) {
                                findViewHolderForAdapterPosition(i)?.let {
                                    (it as H).onStart()
                                }
                            }
                    }
                }
            }
        })
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onViewAttachedToWindow(holder: H) {
        super.onViewAttachedToWindow(holder)
        holder.onStart()
    }

    override fun onViewDetachedFromWindow(holder: H) {
        super.onViewDetachedFromWindow(holder)
        holder.onStop()
    }
}
