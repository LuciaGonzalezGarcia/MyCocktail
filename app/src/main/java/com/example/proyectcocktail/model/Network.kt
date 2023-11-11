package com.example.proyectcocktail.model

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proyectcocktail.model.database.Category
import com.example.proyectcocktail.model.database.Cocktail
import com.example.proyectcocktail.model.database.CocktailIngredient
import com.example.proyectcocktail.model.database.Ingredient
import org.json.JSONException
import org.json.JSONObject

private const val URL_CATEGORIES = "https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list"
private const val URL_INGREDIENTS = "https://www.thecocktaildb.com/api/json/v1/1/list.php?i=list"
private const val URL_COCKTAILSLIST = "https://www.thecocktaildb.com/api/json/v1/1/filter.php"
private const val URL_COCKTAIL = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i="
private const val LIST = "drinks"
private const val CATEGORY_LABEL = "strCategory"
private const val INGREDIENT_LABEL = "strIngredient1"

private const val COCKTAILID_LABEL = "idDrink"
private const val COCKTAILNAME_LABEL = "strDrink"
private const val COCKTAILCATEGORY_LABEL = "strCategory"
private const val COCKTAILALCOHOL_LABEL = "strAlcoholic"
private const val COCKTAILGLASS_LABEL = "strGlass"
private const val COCKTAILPREPARATION_LABEL = "strInstructions"
private const val COCKTAILINGREDIENT_LABEL = "strIngredient"
private const val COCKTAILMEASURE_LABEL = "strMeasure"

class Network private constructor(context : Context) {
    companion object : SingletonHolder<Network, Context>(::Network)

    val queue = Volley.newRequestQueue(context)

    fun getCategories(listener: Response.Listener<List<Category>>, errorListener: Response.ErrorListener) {
        val url = URL_CATEGORIES
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response -> processCategories(response, listener, errorListener) },
            { error -> errorListener.onErrorResponse(error) })
        queue.add(jsonObjectRequest)
    }

    private fun processCategories(response: JSONObject, listener: Response.Listener<List<Category>>, errorListener: Response.ErrorListener) {
        val categories = ArrayList<Category>()
        try{
            val categoryArray = response.getJSONArray(LIST)
            for (i in 0 until categoryArray.length()){
                val categoryObject : JSONObject = categoryArray[i] as JSONObject
                val name = categoryObject.getString(CATEGORY_LABEL)
                val category = Category(name)
                categories.add(category)
            }
        }catch (e : JSONException){
           errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing categories"))
        }
        categories.sortBy{it.name}
        listener.onResponse(categories)
    }




    fun getIngredients(listener: Response.Listener<List<Ingredient>>, errorListener: Response.ErrorListener) {
        val url = URL_INGREDIENTS
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response -> processIngredients(response, listener, errorListener) },
            { error -> errorListener.onErrorResponse(error) })
        queue.add(jsonObjectRequest)
    }

    private fun processIngredients(response: JSONObject, listener: Response.Listener<List<Ingredient>>, errorListener: Response.ErrorListener) {
        val ingredients = ArrayList<Ingredient>()
        try{
            val ingredientArray = response.getJSONArray(LIST)
            for (i in 0 until ingredientArray.length()){
                val ingredientObject : JSONObject = ingredientArray[i] as JSONObject
                val name = ingredientObject.getString(INGREDIENT_LABEL)
                val ingredient = Ingredient(name)
                ingredients.add(ingredient)
            }
        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing ingredients"))
        }
        ingredients.sortBy {it.name}
        listener.onResponse(ingredients)
    }


    fun getCocktailsByCategory(category : String, listener: Response.Listener<List<Cocktail>>, errorListener: Response.ErrorListener) {
        val url = "$URL_COCKTAILSLIST?c=$category"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response -> processCocktailsByCategory(response, listener, errorListener) },
            { error -> errorListener.onErrorResponse(error) })
        queue.add(jsonObjectRequest)
    }

    private fun processCocktailsByCategory(response: JSONObject, listener: Response.Listener<List<Cocktail>>, errorListener: Response.ErrorListener) {
        val cocktails = ArrayList<Cocktail>()
        try{
            val cocktailArray = response.getJSONArray(LIST)
            for (i in 0 until cocktailArray.length()){
                val cocktailObject : JSONObject = cocktailArray[i] as JSONObject
                val id = cocktailObject.getInt(COCKTAILID_LABEL)
                val cocktail = Cocktail(id,"category","name" + i.toString(),"alcohol","glass", 0, "preparation")
                cocktails.add(cocktail)

            }

        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing cocktails"))
        }
        cocktails.sortBy {it.name}
        listener.onResponse(cocktails)
    }


    fun getCocktailsByIngredient(ingredient : String, listener: Response.Listener<List<Cocktail>>, errorListener: Response.ErrorListener) {
        val url = "$URL_COCKTAILSLIST?i=$ingredient"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response -> processCocktailsByIngredient(response, listener, errorListener) },
            { error -> errorListener.onErrorResponse(error) })
        queue.add(jsonObjectRequest)
    }

    private fun processCocktailsByIngredient(response: JSONObject, listener: Response.Listener<List<Cocktail>>, errorListener: Response.ErrorListener) {
        val cocktails = ArrayList<Cocktail>()
        try{
            val cocktailArray = response.getJSONArray(LIST)
            for (i in 0 until cocktailArray.length()){
                val cocktailObject : JSONObject = cocktailArray[i] as JSONObject
                val id = cocktailObject.getInt(COCKTAILID_LABEL)
                val cocktail = Cocktail(id,"category","name" + i.toString(),"alcohol","glass", 0, "preparation")
                cocktails.add(cocktail)

            }

        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing cocktails"))
        }
        cocktails.sortBy {it.name}
        listener.onResponse(cocktails)
    }



    fun getCocktail(id : Int, listener: Response.Listener<Cocktail>, errorListener: Response.ErrorListener){
        val url = "$URL_COCKTAIL$id"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response -> processCocktail(response, listener, errorListener) },
            { error -> errorListener.onErrorResponse(error) })
        queue.add(jsonObjectRequest)
    }

    private fun processCocktail(response: JSONObject, listener: Response.Listener<Cocktail>, errorListener: Response.ErrorListener){
        var cocktail : Cocktail? = null
        try{
            val cocktailArray = response.getJSONArray(LIST)
            for (i in 0 until cocktailArray.length()){
                val cocktailObject : JSONObject = cocktailArray[i] as JSONObject
                val id = cocktailObject.getInt(COCKTAILID_LABEL)
                val category = cocktailObject.getString(COCKTAILCATEGORY_LABEL)
                val name = cocktailObject.getString(COCKTAILNAME_LABEL)
                val alcohol = cocktailObject.getString(COCKTAILALCOHOL_LABEL)
                val glass = cocktailObject.getString(COCKTAILGLASS_LABEL)
                val preparation = cocktailObject.getString(COCKTAILPREPARATION_LABEL)
                cocktail = Cocktail(id,category,name,alcohol,glass, 0, preparation)
            }
        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing cocktail"))
        }
        listener.onResponse(cocktail)
    }

    fun getCocktailIngredients(id : Int, listener: Response.Listener<List<CocktailIngredient>>, errorListener: Response.ErrorListener){
        val url = "$URL_COCKTAIL$id"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response -> processCocktailIngredients(response, listener, errorListener) },
            { error -> errorListener.onErrorResponse(error) })
        queue.add(jsonObjectRequest)
    }

    private fun processCocktailIngredients(response: JSONObject, listener: Response.Listener<List<CocktailIngredient>>, errorListener: Response.ErrorListener){
        val cocktailIngredients = ArrayList<CocktailIngredient>()
        try{
            val cocktailArray = response.getJSONArray(LIST)
            for (i in 0 until cocktailArray.length()){
                val cocktailObject : JSONObject = cocktailArray[i] as JSONObject
                for (j in 1 until 15){
                    var labelIn = "$COCKTAILINGREDIENT_LABEL$j"
                    var labelMe = "$COCKTAILMEASURE_LABEL$j"
                    if(cocktailObject.getString(labelIn) == "null"){
                        break
                    }
                    else{
                        val id = cocktailObject.getInt(COCKTAILID_LABEL)
                        val ingredient = cocktailObject.getString(labelIn)
                        val measure = cocktailObject.getString(labelMe)
                        val cocktailIngredient = CocktailIngredient(id,ingredient, measure)
                        cocktailIngredients.add(cocktailIngredient)
                    }
                }
            }
        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing cocktail's ingredients"))
        }
        listener.onResponse(cocktailIngredients)
    }



}