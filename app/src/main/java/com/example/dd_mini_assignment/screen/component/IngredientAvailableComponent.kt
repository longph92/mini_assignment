package com.example.dd_mini_assignment.screen.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.dd_mini_assignment.R
import com.example.dd_mini_assignment.databinding.ComponentIngredientAvailableBinding
import com.example.dd_mini_assignment.domain.model.IngredientModel
import com.example.dd_mini_assignment.extensions.color

@BindingAdapter("app:bindingIngredientAvailable")
fun bindingIngredientAvailable(view: IngredientAvailableComponent, data: IngredientModel) {
    view.setIngredientData(data)
}

class IngredientAvailableComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = ComponentIngredientAvailableBinding.inflate(LayoutInflater.from(context), this, true)

    fun setIngredientData(data: IngredientModel) {
        when (data.quantity > 0) {
            true -> {
                binding.mainView.setBackgroundResource(R.color.orange)
                binding.quantity.text = data.quantity.toString()
                binding.quantity.setTextColor(context.color(R.color.orange))
            }
            else -> {
                binding.mainView.setBackgroundResource(R.color.background_gray)
                binding.quantity.text = "0"
                binding.quantity.setTextColor(context.color(R.color.black))
            }
        }
    }
}