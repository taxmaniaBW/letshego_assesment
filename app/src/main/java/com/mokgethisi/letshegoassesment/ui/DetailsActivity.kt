package com.mokgethisi.letshegoassesment.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mokgethisi.letshegoassesment.R
import com.mokgethisi.letshegoassesment.data.mostviewedresponse.Result

class DetailsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val title: TextView = findViewById(R.id.details_title)
        val  author: TextView = findViewById(R.id.details_author)
        val  abstract: TextView = findViewById(R.id.details_abstract)
        val  backdrop: ImageView = findViewById(R.id.backdrop);

        val  result: Result
        if(intent.hasExtra("newsItem")){
            result = intent.getParcelableExtra("newsItem")!!
            Log.e("", "onCreate: "+Gson().toJson(result) )
            title.text = result.title
            author.text = result.byline
            abstract.text = result.abstract
            if (result.media != null) {
                Glide.with(this).load(result.media[0].mediaMetadata[2].url).into(backdrop)
            }

        }
        else{
            Log.e("", "onCreate: RESULT NULL", )
        }
    }
}