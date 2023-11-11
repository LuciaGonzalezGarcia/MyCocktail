package com.example.proyectcocktail.model.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*

@Entity (foreignKeys = [ForeignKey(entity = Cocktail::class,
                        parentColumns = ["id"],
                        childColumns = ["cocktailId"]),
                        ForeignKey(entity = Ingredient::class,
                            parentColumns = ["name"],
                            childColumns = ["ingredient"])],
    indices = [Index(value = ["cocktailId"]), Index(value = ["ingredient"])],
    primaryKeys =["cocktailId", "ingredient"])
class CocktailIngredient(
    val cocktailId: Int,
    val ingredient: String,
    val measure: String
) : Parcelable

{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cocktailId)
        parcel.writeString(ingredient)
        parcel.writeString(measure)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CocktailIngredient> {
        override fun createFromParcel(parcel: Parcel): CocktailIngredient {
            return CocktailIngredient(parcel)
        }

        override fun newArray(size: Int): Array<CocktailIngredient?> {
            return arrayOfNulls(size)
        }
    }

}
