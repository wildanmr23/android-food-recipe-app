package com.example.foodrecipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodrecipeapp.dao.RecipeDao
import com.example.foodrecipeapp.entities.Category
import com.example.foodrecipeapp.entities.CategoryItems
import com.example.foodrecipeapp.entities.Meal
import com.example.foodrecipeapp.entities.Recipes
import com.example.foodrecipeapp.entities.converter.CategoryListConverter
import com.example.foodrecipeapp.entities.converter.MealListConverter
import com.example.foodrecipeapp.entities.converter.MealsItems


@Database(
    entities = [
        Recipes::class,
        CategoryItems::class,
        Category::class,
        Meal::class,
        MealsItems::class
    ], version = 2, exportSchema = false
)
@TypeConverters(CategoryListConverter::class, MealListConverter::class)
abstract class RecipeDatabase : RoomDatabase() {

    companion object {

        var recipesDatabase: RecipeDatabase? = null

        @Synchronized
        fun getDataBase(context: Context): RecipeDatabase {
            if (recipesDatabase == null) {
                recipesDatabase = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    "recipe.db"
                ).fallbackToDestructiveMigration() .build()
            }
            return recipesDatabase!!
        }
    }

    abstract fun recipeDao(): RecipeDao

}