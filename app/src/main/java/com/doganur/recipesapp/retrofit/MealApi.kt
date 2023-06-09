package com.doganur.recipesapp.retrofit

import com.doganur.recipesapp.pojo.CategoryList
import com.doganur.recipesapp.pojo.MealsByCategoryList
import com.doganur.recipesapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MealApi {

    //Api çağrısı
    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id : String) : Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName : String) : Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String) : Call<MealsByCategoryList>

}