package com.example.proyectcocktail.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.example.proyectcocktail.model.Model
import com.example.proyectcocktail.R
import com.example.proyectcocktail.cocktailList.CocktailListActivity
import com.example.proyectcocktail.model.database.Category
import com.example.proyectcocktail.model.database.Ingredient

class SettingsActivity : AppCompatActivity(), ISettingsView  {

    lateinit var categoryText : TextView
    lateinit var ingredientText : TextView
    lateinit var categories : Spinner
    lateinit var ingredients : AutoCompleteTextView

    lateinit var categorySearch : Button
    lateinit var ingredientSearch : Button

    lateinit var typeSearch: RadioGroup

    lateinit var progressBar: ProgressBar

    lateinit var presenter : SettingsPresenter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        categoryText = findViewById(R.id.category)
        ingredientText = findViewById(R.id.ingredient)
        categories = findViewById(R.id.categorySpinner)
        ingredients = findViewById(R.id.ingredientAutoText)
        categorySearch = findViewById(R.id.searchCategoryButton)
        ingredientSearch = findViewById(R.id.searchIngredientButton)
        typeSearch = findViewById(R.id.radioGroup)
        progressBar = findViewById(R.id.progressBarSettings)

        val model = Model(applicationContext)
        presenter = SettingsPresenter(this, model)


    }

    override var cocktailVisible: Boolean
        get() = categoryText.visibility == View.VISIBLE
        set(value) {
            val v = if (value) View.VISIBLE else View.GONE
            categoryText.visibility = v
            categories.visibility = v
            ingredientText.visibility = v
            ingredients.visibility = v
            categorySearch.visibility = v
            ingredientSearch.visibility = v
            typeSearch.visibility = v
        }
    override var progresBarVisible: Boolean
        get() = progressBar.visibility == View.VISIBLE
        set(value) {
            progressBar.visibility = if(value) View.VISIBLE else View.GONE
        }


    //SPINNER IMPLEMENTATION
    override fun showCategories(categoriesList : List<Category>) {

        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, categoriesList)
        categories.setAdapter(adapter)

        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val category : Category = categories.getItemAtPosition(p2) as Category
                presenter.setChosenCategory(category)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }.also { categories.onItemSelectedListener = it }

    }

    //AUTOCOMPLETETEXTVIEW IMPLEMENTATION
    override fun showIngredients(ingredientsList : List<Ingredient>) {

        val adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, ingredientsList)
        ingredients.setAdapter(adapter)

        ingredients.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val ingredient = p0.toString()
                ingredientsList.binarySearch { it.name.compareTo(ingredient) }.let {
                    if (it >= 0)
                        presenter.setChosenIngredient(ingredientsList[it])
                }
            }
        })

    }

    override fun showErrors(error : String) {
        val toast = Toast.makeText(this, error, Toast.LENGTH_LONG)
        toast.show()
    }

    override fun disabledCategorySearch() {
        categorySearch.isEnabled = false
        ingredientSearch.isEnabled = true
    }

    override fun disabledIngredientSearch() {
        categorySearch.isEnabled = true
        ingredientSearch.isEnabled = false
        ingredients.text = null
    }

    //SEARCH BUTTONS INTERACTION
    override fun onSearchCategoryButtonPressed(view: View) {
        presenter.doCategorySearch()
    }

    override fun onSearchIngredientButtonPressed(view: View) {
        presenter.doIngredientSearch()
    }

    //CHANGE ACTIVITY
    override fun goToCocktailListActivity(category: String?, ingredient: String?, localSearch : Boolean) {
        val intent =  Intent(this, CocktailListActivity::class.java).apply {
            putExtra(CocktailListActivity.CATEGORY, category)
            putExtra(CocktailListActivity.INGREDIENT, ingredient)
            putExtra(CocktailListActivity.SEARCH, localSearch)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    //RADIO BUTTONS INTERACTION
    override fun setLocalSearch(view: View) {
        presenter.setChosenSearch(true)
    }

    override fun setInetSearch(view: View) {
        presenter.setChosenSearch(false)
    }


}