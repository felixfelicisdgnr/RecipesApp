package com.doganur.recipesapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.doganur.recipesapp.activites.CategoryMealsActivity
import com.doganur.recipesapp.activites.MealActivity
import com.doganur.recipesapp.adapters.CategoriesAdapter
import com.doganur.recipesapp.adapters.MostPopularAdapter
import com.doganur.recipesapp.databinding.FragmentHomeBinding
import com.doganur.recipesapp.pojo.MealsByCategory
import com.doganur.recipesapp.pojo.Meal
import com.doganur.recipesapp.videomodel.HomeViewModel



class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeMvvm : HomeViewModel
    private lateinit var randomMeal : Meal
    private lateinit var popularItemsAdapter : MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    companion object {
        const val MEAL_ID = "com.doganur.recipesapp.fragments.idMeal"
        const val MEAL_NAME = "com.doganur.recipesapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.doganur.recipesapp.fragments.thumbMeal"
        const val CATEGORY_NAME ="com.doganur.recipesapp.fragments.categoryName"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        popularItemsAdapter = MostPopularAdapter()

        preparePopularItemsRecyclerView()

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observerPopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()//bunu neden buraya taşıdın?
        homeMvvm.getCategories()
        observeCategoriesLiveData()

        onCategoryClick()



    }

    private fun onCategoryClick() {

        categoriesAdapter.onItemClick = {category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {

        categoriesAdapter = CategoriesAdapter()

        binding.rvViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer {categories ->
                categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->

            val intent = Intent(activity, MealActivity::class.java)

            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)

            startActivity(intent)
        }
    }
    private fun preparePopularItemsRecyclerView() {
        binding.rvMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observerPopularItemsLiveData() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularItemsAdapter.setMeals(mealList = mealList as ArrayList<MealsByCategory>)

        }
    }

    private fun onRandomMealClick() {
        binding.cardRandomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)

            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}