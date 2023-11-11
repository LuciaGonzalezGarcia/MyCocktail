package com.example.proyectcocktail.cocktailList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectcocktail.R
import com.example.proyectcocktail.cocktail.CocktailActivity
import com.example.proyectcocktail.model.Model
import com.example.proyectcocktail.model.database.Cocktail
import com.example.proyectcocktail.model.database.CocktailIngredient

class CocktailListActivity : AppCompatActivity(), ICocktailListView {

    private var category : String? = null
    private var ingredient : String? = null
    private var localSearch : Boolean = false

    lateinit var cocktailsList: RecyclerView
    lateinit var progressBar: ProgressBar

    lateinit var presenter : CocktailListPresenter

    companion object {
        const val CATEGORY = "Category"
        const val INGREDIENT = "Ingredient"
        const val SEARCH = "Search"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_list)

        cocktailsList = findViewById(R.id.RecyclerViewID)
        cocktailsList.layoutManager = LinearLayoutManager(this)

        progressBar = findViewById(R.id.progressBarList)

        category = intent.getStringExtra(CATEGORY)
        ingredient = intent.getStringExtra(INGREDIENT)
        localSearch = intent.getBooleanExtra(SEARCH, false)

        val model = Model(applicationContext)
        presenter = CocktailListPresenter(this, model)
    }

    override var cocktailVisible: Boolean
        get() = cocktailsList.visibility == View.VISIBLE
        set(value) {
            cocktailsList.visibility = if (value) View.VISIBLE else View.GONE
        }
    override var progresBarVisible: Boolean
        get() = progressBar.visibility == View.VISIBLE
        set(value) {
            progressBar.visibility = if(value) View.VISIBLE else View.GONE
        }

    override fun gettingCategory(): String? {
        return category
    }

    override fun gettingIngredient(): String? {
        return ingredient
    }

    override fun gettingSearch(): Boolean {
        return localSearch
    }

    override fun showCocktails(cocktails: List<Cocktail>, cocktailsIngredients: List<List<CocktailIngredient>>) {
        cocktailsList.adapter = AdapterRV(cocktails , cocktailsIngredients){ position ->  presenter.onCocktailSelected(position) }
    }

    override fun showErrors(error : String) {
        val toast = Toast.makeText(this, error, Toast.LENGTH_LONG)
        toast.show()
    }

    //CHANGE ACTIVITY
    override fun goToCocktailActivity(cocktailInfo : CocktailInfo){
        val intent =  Intent(this, CocktailActivity::class.java).apply {
            putExtra(CocktailActivity.COCKTAILINFO, cocktailInfo)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}