package com.example.proyectcocktail.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CocktailDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(categories : List<Category>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIngredient(ingredient: List<Ingredient>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktail(cocktail : Cocktail)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCocktailIngredient(pairs : List<CocktailIngredient>)



    @Query("SELECT * FROM category" +
            " ORDER BY name")
    fun loadAllCategories(): List<Category>

    @Query("SELECT * FROM ingredient" +
            " ORDER BY name")
    fun loadAllIngredients(): List<Ingredient>

    @Query("SELECT * FROM cocktail" +
            " WHERE category == :category" +
            " ORDER BY name")
    fun loadAllCocktailsByCategory(category : String): List<Cocktail>

    @Query("SELECT id, category, name, alcohol, glass, score, preparation FROM cocktail" +
            " LEFT JOIN cocktailingredient on cocktail.id == cocktailingredient.cocktailid" +
            " WHERE cocktailingredient.ingredient == :ingredient" +
            " ORDER BY cocktail.name")
    fun loadAllCocktailsByIngredient(ingredient : String): List<Cocktail>

    @Query("SELECT * FROM cocktailingredient" +
            " WHERE cocktailid == :cocktail")
    fun loadAllIngredientsAndMeasures(cocktail : Int): List<CocktailIngredient>

}