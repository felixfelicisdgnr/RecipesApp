package com.doganur.recipesapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doganur.recipesapp.databinding.MealItemBinding
import com.doganur.recipesapp.pojo.MealsByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewModel>() {

    private var mealsList = ArrayList<MealsByCategory>()

    fun setMealsList(mealsList: List<MealsByCategory>) {
        this.mealsList = mealsList as ArrayList<MealsByCategory>

    }

    inner class CategoryMealsViewModel(val binding : MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {
        TODO("Not yet implemented")
    }


}