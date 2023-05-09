package com.doganur.recipesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doganur.recipesapp.databinding.CategoryItemBinding
import com.doganur.recipesapp.pojo.Category
import com.doganur.recipesapp.pojo.CategoryList

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categoriesList = ArrayList<Category>()

    var onItemClick : ((Category) -> Unit)? = null

    fun setCategoryList(categoriesList : List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyItemChanged(categoriesList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        //deneme
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvNameCategory.text = categoriesList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    inner class CategoryViewHolder(var binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}