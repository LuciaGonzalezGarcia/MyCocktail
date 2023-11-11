package com.example.proyectcocktail.model

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.proyectcocktail.model.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Model(context : Context) {

    private val network = Network.getInstance(context)
    private val cocktailDao = CocktailDatabase.getInstance(context).db


    fun getCategories(listener: Response.Listener<List<Category>>,
                      errorListener: Response.ErrorListener) =
        GlobalScope.launch(Dispatchers.Main) {

            val categories = withContext(Dispatchers.IO) {
                cocktailDao.loadAllCategories()
            }
            if (categories.isEmpty()) {
                network.getCategories({
                    GlobalScope.launch { cocktailDao.insertCategory(it) }
                    listener.onResponse(it)
                }, {
                    errorListener.onErrorResponse(it)
                })
            } else {
                listener.onResponse(categories)
            }
        }

    fun getIngredients(listener: Response.Listener<List<Ingredient>>,
                        errorListener: Response.ErrorListener) =
        GlobalScope.launch(Dispatchers.Main) {

            val ingredients = withContext(Dispatchers.IO) {
                cocktailDao.loadAllIngredients()
            }
            if (ingredients.isEmpty()) {
                network.getIngredients({
                    GlobalScope.launch { cocktailDao.insertIngredient(it) }
                    listener.onResponse(it)
                }, {
                    errorListener.onErrorResponse(it)
                })
            } else {
                listener.onResponse(ingredients)
            }
        }




    //NETWORK SEARCH
    fun getCocktailByCategory(category: String,listener: Response.Listener<List<Cocktail>>, errorListener: Response.ErrorListener){
        network.getCocktailsByCategory(category, listener, errorListener)
    }

    fun getCocktailByIngredient(ingredient: String, listener: Response.Listener<List<Cocktail>>, errorListener: Response.ErrorListener){
        network.getCocktailsByIngredient(ingredient, listener, errorListener)
    }

    fun getCocktail(id: Int,listener: Response.Listener<Cocktail>, errorListener: Response.ErrorListener){
        network.getCocktail(id, listener, errorListener)
    }
    fun getCocktailIngredients(id: Int,listener: Response.Listener<List<CocktailIngredient>>, errorListener: Response.ErrorListener){
        network.getCocktailIngredients(id, listener, errorListener)
    }




    //LOCAL SEARCH
    fun getCocktailByCategoryLocal(category : String, listener: Response.Listener<List<Cocktail>>) {
        GlobalScope.launch(Dispatchers.Main) {
            val cocktails = withContext(Dispatchers.IO) {
                cocktailDao.loadAllCocktailsByCategory(category)
            }
            listener.onResponse(cocktails)
        }
    }

    fun getCocktailByIngredientLocal(ingredient: String, listener: Response.Listener<List<Cocktail>>){
        GlobalScope.launch(Dispatchers.Main) {
            val cocktails = withContext(Dispatchers.IO) {
                cocktailDao.loadAllCocktailsByIngredient(ingredient)
            }
            listener.onResponse(cocktails)
        }
    }

    fun getCocktailIngredientsLocal(id : Int, listener: Response.Listener<List<CocktailIngredient>>){
        GlobalScope.launch(Dispatchers.Main) {
            val cocktailIngredients = withContext(Dispatchers.IO) {
                cocktailDao.loadAllIngredientsAndMeasures(id)
            }
            listener.onResponse(cocktailIngredients)
        }

    }



    //INSERTION IN THE DATABASE
    fun insertCocktail(cocktail : Cocktail){
        cocktailDao.insertCocktail(cocktail)

    }

    fun insertIngredients(ingredientsList : List<CocktailIngredient>){
        val ingredients = ArrayList<Ingredient>()
        for (i in 0 until ingredientsList.size){
            val ingredient = Ingredient(ingredientsList[i].ingredient)
            ingredients.add(ingredient)
        }

        cocktailDao.insertIngredient(ingredients)

    }

    fun insertCocktailIngredients(ingredientsList : List<CocktailIngredient>){
        cocktailDao.insertCocktailIngredient(ingredientsList)
    }


}