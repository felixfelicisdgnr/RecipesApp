package com.doganur.recipesapp.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.doganur.recipesapp.R
import com.doganur.recipesapp.databinding.ActivityCategoryMealsBinding
import com.doganur.recipesapp.fragments.HomeFragment
import com.doganur.recipesapp.videomodel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding : ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel : CategoryMealsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel ::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)


        categoryMealsViewModel.observeMealsLiveData().observe(this) { mealsList ->
            mealsList.forEach {
                Log.d("TESTDE", it.strMeal)
            }
        }
    }
}