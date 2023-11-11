package com.example.proyectcocktail.cocktailList

import com.example.proyectcocktail.model.database.Cocktail
import com.example.proyectcocktail.model.database.CocktailIngredient

interface ICocktailListView {

    var cocktailVisible : Boolean
    var progresBarVisible : Boolean

    fun gettingCategory() : String?
    fun gettingIngredient() : String?
    fun gettingSearch(): Boolean

    fun showCocktails(cocktails : List<Cocktail>, cocktailsIngredients: List<List<CocktailIngredient>>)
    fun showErrors(error : String)
    fun goToCocktailActivity(cocktailInfo : CocktailInfo)
}