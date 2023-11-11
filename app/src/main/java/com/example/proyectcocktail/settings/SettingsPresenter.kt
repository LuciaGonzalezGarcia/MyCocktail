package com.example.proyectcocktail.settings

import android.util.Log
import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.proyectcocktail.model.Model
import com.example.proyectcocktail.model.database.Category
import com.example.proyectcocktail.model.database.Ingredient

class SettingsPresenter(val view: ISettingsView, val model : Model) {

    private var category : String? = null
    private var ingredient : String? = null
    private var localSearch : Boolean = false

    init{
        view.progresBarVisible = true
        view.cocktailVisible = false


        //GET LIST OF CATEGORIES AND INGREDIENTS
        model.getCategories(object : Response.Listener<List<Category>>{
            override fun onResponse(categories: List<Category>) {
                view.showCategories(categories)
                view.progresBarVisible = false
                view.cocktailVisible = true


            }

        }, object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                view.showErrors(error.toString())
            }

        })

        model.getIngredients(object : Response.Listener<List<Ingredient>>{
            override fun onResponse(ingredients: List<Ingredient>) {
                view.showIngredients(ingredients)
                view.progresBarVisible = false
                view.cocktailVisible = true

            }

        }, object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                view.showErrors(error.toString())
            }

        })
    }

    fun setChosenIngredient(ingredient : Ingredient){
        this.ingredient = ingredient.toString()
        view.disabledCategorySearch()
    }

    fun setChosenCategory(category : Category){
        this.category = category.toString()
        view.disabledIngredientSearch()
    }

    fun setChosenSearch(search : Boolean){
        localSearch = search
    }

    fun doCategorySearch(){
        view.goToCocktailListActivity(category, null, localSearch)
    }

    fun doIngredientSearch(){
        view.goToCocktailListActivity(null, ingredient, localSearch)
    }

}