package com.example.proyectcocktail.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient(@PrimaryKey val name: String)
{
    override fun toString(): String {
        return name
    }
}

