package com.example.proyectcocktail.model.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*

@Entity(
    foreignKeys = [ForeignKey(entity = Category::class,
                              parentColumns = ["name"],
                              childColumns = ["category"])],
    indices = [Index(value = ["category"])])

class Cocktail(@PrimaryKey val id: Int,
               @ColumnInfo(name="category") val category: String,
               val name: String,
               val alcohol: String,
               val glass: String,
               val score: Int = 0,
               val preparation: String
               ) : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(category)
        parcel.writeString(name)
        parcel.writeString(alcohol)
        parcel.writeString(glass)
        parcel.writeInt(score)
        parcel.writeString(preparation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cocktail> {
        override fun createFromParcel(parcel: Parcel): Cocktail {
            return Cocktail(parcel)
        }

        override fun newArray(size: Int): Array<Cocktail?> {
            return arrayOfNulls(size)
        }
    }

}
