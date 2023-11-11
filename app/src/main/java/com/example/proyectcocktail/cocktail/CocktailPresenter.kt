package com.example.proyectcocktail.cocktail

import com.android.volley.Response
import com.example.proyectcocktail.cocktailList.CocktailInfo
import com.example.proyectcocktail.model.Model
import com.example.proyectcocktail.model.database.Cocktail
import com.example.proyectcocktail.model.database.CocktailIngredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CocktailPresenter(val view: ICocktailView, val model : Model) {
    val cocktailInfo : CocktailInfo
    val myCocktail : Cocktail
    val myIngredientsList : List<CocktailIngredient>
    var myScore : Int = 0

    init {
        cocktailInfo = view.gettingCocktailInfo()
        myCocktail = cocktailInfo.cocktail
        myIngredientsList = cocktailInfo.ingredientsList

        showInfo()
    }


    fun showInfo(){
        view.showName(myCocktail.name!!)
        view.showAlcohol(myCocktail.alcohol!!)
        view.showGlass(myCocktail.glass!!)
        view.showCategory(myCocktail.category!!)
        view.showScore(myCocktail.score)
        view.showPreparation(myCocktail.preparation!!)
        view.showIngredients(myIngredientsList)
    }

    fun scoreCocktail(){
        view.showScoreDialog()
    }

    fun setScore(score : Int){
        myScore = score
    }

    fun addCocktail(){
        val newCocktail = Cocktail(myCocktail.id, myCocktail.category, myCocktail.name, myCocktail.alcohol, myCocktail.glass, myScore, myCocktail.preparation)
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                model.insertIngredients(myIngredientsList)
                model.insertCocktail(newCocktail)
                model.insertCocktailIngredients(myIngredientsList)
            }
        }
        view.showAddDialog()
    }


}