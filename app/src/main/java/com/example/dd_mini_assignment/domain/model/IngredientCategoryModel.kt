package com.example.dd_mini_assignment.domain.model

import androidx.databinding.BaseObservable
import com.google.gson.annotations.SerializedName

data class IngredientCategoryModel(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String
): BaseObservable() {
    var isSelected: Boolean = false
    var searchText = ""

    fun setItemSelected(boolean: Boolean) {
        if (isSelected != boolean) {
            isSelected = boolean
            notifyChange()
        }
    }
}