package com.doganur.recipesapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.doganur.recipesapp.activites.MealActivity
import com.doganur.recipesapp.databinding.FragmentHomeBinding
import com.doganur.recipesapp.pojo.Meal
import com.doganur.recipesapp.videomodel.HomeViewModel



class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeMvvm : HomeViewModel
    private lateinit var randomMeal : Meal
    companion object {
        const val MEAL_ID = "com.doganur.recipesapp.fragments.idMeal"
        const val MEAL_NAME = "com.doganur.recipesapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.doganur.recipesapp.fragments.thumbMeal"
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
        homeMvvm.getRandomMeal()

        observerRandomMeal()
        onRandomMealClick()

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