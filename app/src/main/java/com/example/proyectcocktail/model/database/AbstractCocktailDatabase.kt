package com.example.proyectcocktail.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectcocktail.model.Network
import com.example.proyectcocktail.model.SingletonHolder


@Database(entities=[
            Category::class,
            Ingredient::class,
            Cocktail::class,
            CocktailIngredient::class
], version = 1)


private abstract class AbstractCocktailDatabase: RoomDatabase() {
    abstract fun getDAO():CocktailDAO
}


class CocktailDatabase private constructor(context: Context) {

    val db = Room.databaseBuilder(context, AbstractCocktailDatabase::class.java, "database")
        .build()
        .getDAO()

    companion object : SingletonHolder<CocktailDatabase, Context>(::CocktailDatabase)

}