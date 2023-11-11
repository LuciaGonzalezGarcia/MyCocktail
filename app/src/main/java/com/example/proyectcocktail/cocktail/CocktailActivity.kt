package com.example.proyectcocktail.cocktail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.proyectcocktail.R
import com.example.proyectcocktail.cocktailList.CocktailInfo
import com.example.proyectcocktail.model.Model
import com.example.proyectcocktail.model.database.CocktailIngredient

class CocktailActivity : AppCompatActivity(), ICocktailView, ScoreDialog.ScoreListener {

    lateinit var cocktailInfo : CocktailInfo
    lateinit var cocktailName : TextView
    lateinit var cocktailAlcohol : TextView
    lateinit var cocktailGlass : TextView
    lateinit var cocktailCategory : TextView
    lateinit var cocktailScore : TextView
    lateinit var cocktailPreparation : TextView
    lateinit var cocktailIngredients : TextView

    lateinit var addButton : Button


    lateinit var presenter : CocktailPresenter

    companion object {
        const val COCKTAILINFO = "CocktailInfo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail)

        cocktailName = findViewById(R.id.CocktailNameTextView)
        cocktailAlcohol = findViewById(R.id.AlcoholTextView)
        cocktailGlass = findViewById(R.id.ServedInTextView)
        cocktailCategory = findViewById(R.id.CategoryTextView)
        cocktailScore = findViewById(R.id.ScoreTextView)
        cocktailPreparation = findViewById(R.id.PreparationTextViewID)
        cocktailIngredients = findViewById(R.id.IngredientsTextView)

        addButton = findViewById(R.id.AddButton)

        cocktailInfo = intent.getParcelableExtra(COCKTAILINFO)!!

        val model = Model(applicationContext)
        presenter = CocktailPresenter(this, model)



    }

    override fun gettingCocktailInfo(): CocktailInfo {
        return cocktailInfo
    }


    override fun showName(name : String){
        cocktailName.text = name
    }

    override fun showAlcohol(alcohol: String) {
        cocktailAlcohol.text = alcohol
    }

    override fun showGlass(glass: String) {
        cocktailGlass.text = glass
    }

    override fun showCategory(category: String) {
        cocktailCategory.text = category
    }

    override fun showScore(score: Int) {
        val textScore = "$score out of 10"
        cocktailScore.text = textScore
    }

    override fun showPreparation(preparation: String) {
        cocktailPreparation.text = preparation
    }

    override fun showIngredients(ingredients: List<CocktailIngredient>) {
        var textIngredients = ""
        for (i in 0 until ingredients.size){
            if (i != 0){
                textIngredients += ", "
            }
            textIngredients += ingredients[i].measure
            textIngredients += ingredients[i].ingredient
        }
        cocktailIngredients.text = textIngredients
    }


    //BUTTONS INTERACTION
    override fun onScoreButtonPressed(view :View) {
        presenter.scoreCocktail()
    }

    override fun onAddButtonPressed(view: View) {
        presenter.addCocktail()
    }


    //DIALOGS
    override fun showScoreDialog() = ScoreDialog().show(supportFragmentManager, "ScoreDialog")

    override fun onScoreChosen(score: Float) {
        val scoreInt = score.toInt()
        val textScore = "$scoreInt out of 10"
        cocktailScore.text = textScore
        presenter.setScore(scoreInt)
        addButton.isEnabled = true
    }

    override fun showAddDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Database")
            setMessage("Cocktail and ingredients inserted in local database")
            setPositiveButton("OK") { _, _ ->
                finish()
            }
            show()
        }
    }

}