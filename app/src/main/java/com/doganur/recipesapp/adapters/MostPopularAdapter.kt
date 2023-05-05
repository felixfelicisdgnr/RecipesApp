package com.doganur.recipesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doganur.recipesapp.databinding.PopularItemsBinding
import com.doganur.recipesapp.pojo.MealsByCategory

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    //bu lambda CategoryMeals adlı bir parametre alacak ve işlem yapacak, her çağrıldığında birim döndürecek
    lateinit var onItemClick : ((MealsByCategory) -> Unit)

    private var mealsList = ArrayList<MealsByCategory>()


    fun setMeals(mealList: ArrayList<MealsByCategory>){
        this.mealsList = mealList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItems)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    inner class PopularMealViewHolder(var binding : PopularItemsBinding) : RecyclerView.ViewHolder(binding.root) {

    }


}