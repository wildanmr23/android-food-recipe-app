package com.example.foodrecipeapp.views.home

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipeapp.adapter.MainCategoryAdapter
import com.example.foodrecipeapp.adapter.SubCategoryAdapter
import com.example.foodrecipeapp.database.RecipeDatabase
import com.example.foodrecipeapp.databinding.ActivityHomeBinding
import com.example.foodrecipeapp.entities.CategoryItems
import com.example.foodrecipeapp.entities.converter.MealsItems
import com.example.foodrecipeapp.utils.startActivity
import com.example.foodrecipeapp.views.base.BaseActivity
import com.example.foodrecipeapp.views.detail.DetailActivity
import kotlinx.coroutines.launch


class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    var arrMainCategory = ArrayList<CategoryItems>()
    var arrSubCategory = ArrayList<MealsItems>()
    var mainCategoryAdapter = MainCategoryAdapter()
    var subCategoryAdapter = SubCategoryAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getDataFromDb()

        mainCategoryAdapter.setClickListener(onClicked)
        subCategoryAdapter.setClickListener(onClickedSubItem)



    }


    private val onClicked = object : MainCategoryAdapter.onItemClickListener {
        override fun onClicked(categoryName: String) {
            getDataMealFromDb(categoryName)
        }

    }

    private val onClickedSubItem = object : SubCategoryAdapter.onItemClickListener {
        override fun onClicked(id: String) {
            val intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

    }

    private fun getDataMealFromDb(categoryName: String) {
        binding.tvCategory.text = "$categoryName Category"
        launch {
            this.let {
                var cat = RecipeDatabase.getDataBase(this@HomeActivity).recipeDao()
                    .getSpecificMealList(categoryName)
                arrSubCategory = cat as ArrayList<MealsItems>
                subCategoryAdapter.setData(arrSubCategory)
                binding.rvSubCategory.layoutManager =
                    LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                binding.rvSubCategory.adapter = subCategoryAdapter

            }
        }
    }

    private fun getDataFromDb() {
        launch {
            this.let {
                var cat = RecipeDatabase.getDataBase(this@HomeActivity).recipeDao().getAllCategory()
                arrMainCategory = cat as ArrayList<CategoryItems>
                arrMainCategory.reverse()

                getDataMealFromDb(arrMainCategory[0].strcategory)
                mainCategoryAdapter.setData(arrMainCategory)
                binding.rvMainCategory.layoutManager =
                    LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                binding.rvMainCategory.adapter = mainCategoryAdapter
            }
        }
    }

}

