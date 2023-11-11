package com.example.proyectcocktail.cocktailList

import android.os.Parcel
import android.os.Parcelable
import com.example.proyectcocktail.model.database.Cocktail
import com.example.proyectcocktail.model.database.CocktailIngredient

class CocktailInfo(val cocktail: Cocktail, val ingredientsList : List<CocktailIngredient>) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Cocktail::class.java.classLoader)!!,
        parcel.createTypedArrayList(CocktailIngredient)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(cocktail, flags)
        parcel.writeTypedList(ingredientsList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CocktailInfo> {
        override fun createFromParcel(parcel: Parcel): CocktailInfo {
            return CocktailInfo(parcel)
        }

        override fun newArray(size: Int): Array<CocktailInfo?> {
            return arrayOfNulls(size)
        }
    }
}