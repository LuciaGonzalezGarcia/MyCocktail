package com.example.proyectcocktail.cocktailList


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectcocktail.R
import com.example.proyectcocktail.model.database.Cocktail
import com.example.proyectcocktail.model.database.CocktailIngredient

class AdapterRV(val cocktails: List<Cocktail>, val cocktailsIngredients: List<List<CocktailIngredient>>, val onClickListener: (Int)-> Unit) : RecyclerView.Adapter<AdapterRV.ViewHolder>()  {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var myName: TextView = view.findViewById(R.id.CocktailNameID)
        var myCategory: TextView = view.findViewById(R.id.CategoryTextID)
        var myAlcohol: TextView = view.findViewById(R.id.AlcoholTextID)
        var myIngredients : TextView = view.findViewById(R.id.IngredientsTextID)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_recycler, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            myName.text = cocktails[position].name
            myCategory.text = cocktails[position].category
            myAlcohol.text = cocktails[position].alcohol
            var ingredientsText = ""
            for (i in 0 until cocktailsIngredients.size){
                if(cocktailsIngredients[i][0].cocktailId == cocktails[position].id){
                    for (j in 0 until cocktailsIngredients[i].size){
                        if (j != 0){
                            ingredientsText += ", "
                        }
                        ingredientsText += cocktailsIngredients[i][j].ingredient
                    }
                }
            }
            myIngredients.text = ingredientsText
            holder.itemView.setOnClickListener {
                onClickListener(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return cocktails.size
    }
}