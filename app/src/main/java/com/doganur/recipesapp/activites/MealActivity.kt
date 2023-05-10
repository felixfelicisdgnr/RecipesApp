package com.doganur.recipesapp.activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.doganur.recipesapp.R
import com.doganur.recipesapp.databinding.ActivityMealBinding
import com.doganur.recipesapp.fragments.HomeFragment
import com.doganur.recipesapp.videomodel.MealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMealBinding

    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var youtubeLink : String

    private lateinit var mealMvvm : MealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformationFromIntent()

        setInformationInViews()

        loadingCase()

        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYoutubeImageClick()

    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this
        )  { value ->

            onResponseCase()

            val detailMealCategoryString = getString(
                R.string.detail_meal_category,
                value.strCategory
            )
            val detailMealAreaString = getString(
                R.string.detail_meal_area,
                value.strArea
            )

            binding.tvDetailCategories.text = detailMealCategoryString
            binding.tvDetailArea.text = detailMealAreaString
            binding.tvStepsInstructions.text = value.strInstructions

            youtubeLink = value.strYoutube.toString()

        }
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.progressBarMealActivity.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvDetailInstructions.visibility = View.INVISIBLE
        binding.tvDetailCategories.visibility = View.INVISIBLE
        binding.tvDetailArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE

    }

    private fun onResponseCase() {
        binding.progressBarMealActivity.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvDetailInstructions.visibility = View.VISIBLE
        binding.tvDetailCategories.visibility = View.VISIBLE
        binding.tvDetailArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}