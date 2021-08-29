package com.example.dd_mini_assignment.screen.ingredient

import androidx.databinding.ObservableArrayList
import com.example.dd_mini_assignment.domain.model.IngredientCategoryModel
import com.example.dd_mini_assignment.domain.model.IngredientModel
import com.example.dd_mini_assignment.domain.usecase.GetIngredientCategoryUseCase
import com.example.dd_mini_assignment.domain.usecase.GetIngredientUseCase
import com.example.dd_mini_assignment.domain.usecase.SearchIngredientUseCase
import com.example.dd_mini_assignment.extensions.addAllNew
import com.example.dd_mini_assignment.extensions.safeDispose
import com.example.dd_mini_assignment.screen.base.BaseViewModel
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

class IngredientViewModel(
    private val getIngredientUseCase: GetIngredientUseCase,
    private val searchIngredientUseCase: SearchIngredientUseCase,
    private val getIngredientCategoryUseCase: GetIngredientCategoryUseCase
): BaseViewModel() {
    private var searchDisposable: Disposable? = null

    val ingredientList = ObservableArrayList<IngredientModel>()
    val categories = ObservableArrayList<IngredientCategoryModel>()

    fun getCategories() {
        getIngredientCategoryUseCase.execute(
            onSuccess = {
                categories.addAllNew(it)

                if (it.isNotEmpty()) {
                    getIngredientByCategory(it[0].id)
                }
            }
        )
    }

    fun onCategoryChanged(category: IngredientCategoryModel) {
        categories.forEach { cate ->
            if (cate.id == category.id) {
                cate.setItemSelected(!category.isSelected)
                getIngredientByCategory(cate.id)
            } else {
                cate.setItemSelected(false)
            }
        }
    }

    private fun getIngredientByCategory(categoryId: String) {
        getIngredientUseCase.categoryId(categoryId).execute(
            onSuccess = {
                ingredientList.addAllNew(it)
            }
        )
    }

    fun observeTextChanged(observable: @NonNull Observable<CharSequence>) {
        searchDisposable = observable.subscribe(
            ::onSearchAnItem,
            ::onSearchError
        )
    }

    private fun onSearchAnItem(text: CharSequence) {
        val categoryId = categories.find { it.isSelected }?.id ?: return
        searchIngredientUseCase.categoryId(categoryId).search(text.toString()).forceExecute(
            ::onSearchSuccess
        )
    }

    private fun onSearchError(throwable: Throwable) {
    }

    private fun onSearchSuccess(ingredients: List<IngredientModel>) {
        ingredientList.addAllNew(ingredients)
    }

    override fun onCleared() {
        super.onCleared()
        searchDisposable.safeDispose()
    }
}