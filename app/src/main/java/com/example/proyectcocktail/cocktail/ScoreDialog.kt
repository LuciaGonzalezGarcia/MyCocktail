package com.example.proyectcocktail.cocktail

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import android.view.View
import android.widget.EditText

import android.widget.RatingBar
import com.example.proyectcocktail.R
import java.lang.IllegalStateException


class ScoreDialog : DialogFragment(){

    private lateinit var scoreListener: ScoreListener
    private lateinit var ratingBar: RatingBar

    interface ScoreListener {
        fun onScoreChosen(score: Float)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        scoreListener = try {
            context as ScoreListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$context does not listen to scores")
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val activity: Activity = activity ?: throw IllegalStateException("Activity cannot be null")
        val view: View = activity.layoutInflater.inflate(R.layout.activity_rating_bar, null)
        with (view) {
            ratingBar = findViewById(R.id.ratingBar)
        }

        return AlertDialog.Builder(activity).run {
            setTitle("Grade this cocktail")
            setView(view)
            setPositiveButton("Ok"){ dialog, id -> scoreListener.onScoreChosen(ratingBar.rating)}

            setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            create()
        }
    }




}