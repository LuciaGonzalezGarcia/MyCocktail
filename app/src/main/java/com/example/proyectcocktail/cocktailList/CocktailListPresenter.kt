package com.example.proyectcocktail.cocktailList

import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.proyectcocktail.model.Model
import com.example.proyectcocktail.model.database.Cocktail
import com.example.proyectcocktail.model.database.CocktailIngredient

class CocktailListPresenter (val view: ICocktailListView, val model : Model) {

    private var category : String? = null
    private var ingredient : String? = null
    private var localSearch : Boolean = false
    private var cocktailsList = ArrayList<Cocktail>()
    private var cocktailsListIngredients = ArrayList<List<CocktailIngredient>>()

    init {
        category = view.gettingCategory()
        ingredient = view.gettingIngredient()
        localSearch = view.gettingSearch()

        view.progresBarVisible = true
        view.cocktailVisible = false

        if (category != null){    //IF WE DO DE SEARCH BY CATEGORY
            if (!localSearch){    //IF WE DO NETWORK SEARCH
                model.getCocktailByCategory(category!!, object : Response.Listener<List<Cocktail>>{
                    override fun onResponse(cocktails: List<Cocktail>) {
                        for (i in 0 until cocktails.size){

                            model.getCocktail(cocktails[i].id, object : Response.Listener<Cocktail>{
                                override fun onResponse(cocktail: Cocktail) {
                                    cocktailsList.add(cocktail)
                                    model.getCocktailIngredients(cocktails[i].id, object : Response.Listener<List<CocktailIngredient>>{
                                        override fun onResponse(cocktailIngredients: List<CocktailIngredient>) {
                                            cocktailsListIngredients.add(cocktailIngredients)
                                            if (cocktailsListIngredients.size == cocktails.size){
                                                view.progresBarVisible = false
                                                view.cocktailVisible = true
                                                view.showCocktails(cocktailsList, cocktailsListIngredients)
                                            }
                                        }
                                    }, object : Response.ErrorListener{
                                        override fun onErrorResponse(error: VolleyError?) {
                                            view.showErrors(error.toString())
                                        }
                                    })
                                }
                            }, object : Response.ErrorListener{
                                override fun onErrorResponse(error: VolleyError?) {
                                    view.showErrors(error.toString())
                                }
                            })
                        }
                    }
                }, object : Response.ErrorListener{
                    override fun onErrorResponse(error: VolleyError?) {
                        view.showErrors(error.toString())
                    }
                })
            }
            else{      //IF WE DO LOCAL SEARCH
                model.getCocktailByCategoryLocal(category!!, object : Response.Listener<List<Cocktail>>{
                    override fun onResponse(cocktails: List<Cocktail>) {
                        for (i in 0 until cocktails.size){
                            cocktailsList.add(cocktails[i])

                            model.getCocktailIngredientsLocal(cocktails[i].id, object : Response.Listener<List<CocktailIngredient>>{
                                override fun onResponse(cocktailIngredients: List<CocktailIngredient>) {
                                    cocktailsListIngredients.add(cocktailIngredients)
                                    if (cocktailsListIngredients.size == cocktails.size){
                                        view.showCocktails(cocktailsList, cocktailsListIngredients)
                                    }
                                }
                            })
                        }
                        view.progresBarVisible = false
                        view.cocktailVisible = true
                    }
                })
            }
        }
        else if (ingredient != null){     //IF WE DO DE SEARCH BY INGREDIENT
            if (!localSearch){            //IF WE DO NETWORK SEARCH
                model.getCocktailByIngredient(ingredient!!, object : Response.Listener<List<Cocktail>>{
                    override fun onResponse(cocktails: List<Cocktail>) {
                        for (i in 0 until cocktails.size){

                            model.getCocktail(cocktails[i].id, object : Response.Listener<Cocktail>{
                                override fun onResponse(cocktail: Cocktail) {
                                    cocktailsList.add(cocktail)
                                    model.getCocktailIngredients(cocktails[i].id, object : Response.Listener<List<CocktailIngredient>>{
                                        override fun onResponse(cocktailIngredients: List<CocktailIngredient>) {
                                            cocktailsListIngredients.add(cocktailIngredients)
                                            if (cocktailsListIngredients.size == cocktails.size){
                                                view.progresBarVisible = false
                                                view.cocktailVisible = true
                                                view.showCocktails(cocktailsList, cocktailsListIngredients)
                                            }
                                        }
                                    }, object : Response.ErrorListener{
                                        override fun onErrorResponse(error: VolleyError?) {
                                            view.showErrors(error.toString())
                                        }
                                    })
                                }
                            }, object : Response.ErrorListener{
                                override fun onErrorResponse(error: VolleyError?) {
                                    view.showErrors(error.toString())
                                }
                            })
                        }
                    }
                }, object : Response.ErrorListener{
                    override fun onErrorResponse(error: VolleyError?) {
                        view.showErrors(error.toString())
                    }

                })
            }
            else{       //IF WE DO LOCAL SEARCH
                model.getCocktailByIngredientLocal(ingredient!!, object : Response.Listener<List<Cocktail>>{
                    override fun onResponse(cocktails: List<Cocktail>) {
                        for (i in 0 until cocktails.size){
                            cocktailsList.add(cocktails[i])

                            model.getCocktailIngredientsLocal(cocktails[i].id, object : Response.Listener<List<CocktailIngredient>>{
                                override fun onResponse(cocktailIngredients: List<CocktailIngredient>) {
                                    cocktailsListIngredients.add(cocktailIngredients)
                                    if (cocktailsListIngredients.size == cocktails.size){
                                        view.showCocktails(cocktailsList, cocktailsListIngredients)
                                    }
                                }
                            })
                        }
                        view.progresBarVisible = false
                        view.cocktailVisible = true
                    }
                })
            }
        }
    }

    fun onCocktailSelected(position: Int){
        for (i in 0 until cocktailsListIngredients.size){
            if(cocktailsListIngredients[i][0].cocktailId == cocktailsList[position].id){
                val cocktailInfo = CocktailInfo(cocktailsList[position], cocktailsListIngredients[i])
                view.goToCocktailActivity(cocktailInfo)
            }
        }
    }
}