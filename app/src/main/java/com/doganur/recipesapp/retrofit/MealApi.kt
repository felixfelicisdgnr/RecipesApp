package com.doganur.recipesapp.retrofit

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

}