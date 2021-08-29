package com.example.dd_mini_assignment.screen.base

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<H : RecyclerView.ViewHolder> constructor(private val data: ObservableArrayList<*>? = null) :
    RecyclerView.Adapter<H>() {

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
    }

    override fun getItemCount(): Int = data?.size ?: 0
}
