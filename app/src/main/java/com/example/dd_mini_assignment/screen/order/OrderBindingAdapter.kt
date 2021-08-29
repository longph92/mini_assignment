package com.example.dd_mini_assignment.screen.order

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:bindingBadgeInComing")
fun bindingBadgeInComing(textView: TextView, count: Int) {
    when {
        count > 0 -> textView.text = String.format("+%d", count)
        else -> textView.text = "0"
    }
}