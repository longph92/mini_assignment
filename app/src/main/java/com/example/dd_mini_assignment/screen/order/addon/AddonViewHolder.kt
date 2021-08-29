package com.example.dd_mini_assignment.screen.order.addon

import android.text.Html
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_mini_assignment.databinding.ItemAddonBinding
import com.example.dd_mini_assignment.domain.model.AddonModel

class AddonViewHolder(val binding: ItemAddonBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(addon: AddonModel) {
        binding.title.text = Html.fromHtml(
            String.format("<b>%d</b> %s", addon.quantity, addon.title),
            Html.FROM_HTML_MODE_LEGACY
        )
    }
}