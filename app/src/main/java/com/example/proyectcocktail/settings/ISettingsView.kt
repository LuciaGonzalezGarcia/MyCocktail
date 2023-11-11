package com.example.proyectcocktail.settings

import android.view.View
import com.example.proyectcocktail.model.database.Category
import com.example.proyectcocktail.model.database.Ingredient

interface ISettingsView {

    var cocktailVisible : Boolean
    var progresBarVisible : Boolean

    fun showCategories(categoriesList : List<Category>)
    fun showIngredients(ingredients : List<Ingredient>)
    fun showErrors(error : String)

    fun disabledCategorySearch()
    fun disabledIngredientSearch()

    fun onSearchCategoryButtonPressed(view: View)
    fun onSearchIngredientButtonPressed(view: View)
    fun goToCocktailListActivity(category : String?, ingredient : String?, localSearch : Boolean)

    fun setLocalSearch(view: View)
    fun setInetSearch(view: View)


}