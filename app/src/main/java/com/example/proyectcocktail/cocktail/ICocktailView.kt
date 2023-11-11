package com.example.proyectcocktail.cocktail

import android.view.View
import com.example.proyectcocktail.cocktailList.CocktailInfo
import com.example.proyectcocktail.model.database.CocktailIngredient

interface ICocktailView {
    fun gettingCocktailInfo(): CocktailInfo
    fun showName(name : String)
    fun showAlcohol(alcohol : String)
    fun showGlass(glass : String)
    fun showCategory(category : String)
    fun showScore(score : Int)
    fun showPreparation(preparation : String)
    fun showIngredients(ingredients : List<CocktailIngredient>)

    fun onScoreButtonPressed(view : View)
    fun onAddButtonPressed(view: View)
    fun showScoreDialog()
    fun showAddDialog()
}