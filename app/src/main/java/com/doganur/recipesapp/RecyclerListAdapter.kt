package com.doganur.recipesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_row.view.*

class RecyclerListAdapter(val mealList : ArrayList<String>, val idList : ArrayList<Int>) : RecyclerView.Adapter<RecyclerListAdapter.RecipesHolder>() {

    class RecipesHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        return RecipesHolder(view)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: RecipesHolder, position: Int) {
        holder.itemView.recycler_row_text.text = mealList[position]
        //recyclerView'a tıklanınca ne olacağını yazalım
        holder.itemView.setOnClickListener {
            //aksiyon oluşturacağız navigasyonla diğer tarafa geçeceğiz
            val action = ListFragmentDirections.actionListFragmentToDefinitionFragment("icomerecycler",idList[position])
            Navigation.findNavController(it).navigate(action)
        }
    }
}