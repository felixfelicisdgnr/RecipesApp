package com.doganur.recipesapp.videomodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doganur.recipesapp.pojo.*
import com.doganur.recipesapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()

    fun getRandomMeal() {

        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {

            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {

                    val randomMeal : Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal

                    Log.d("TEST","meal id ${randomMeal.idMeal} name ${randomMeal.strMeal}")

                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.body() != null) {

                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.d("HomeFragment", t.message.toString())
            }

        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    response.body()?.let { categoryList ->
                        categoriesLiveData.postValue(categoryList.categories)
                    }
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                    Log.d("HomeViewModel", t.message.toString())
            }

        })
    }

    fun observeRandomMealLiveData() : LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemsLiveData() : LiveData<List<MealsByCategory>>{
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData() : LiveData<List<Category>>{
        return categoriesLiveData
    }
}