package com.example.foodrecipeapp.views.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.foodrecipeapp.database.RecipeDatabase
import com.example.foodrecipeapp.databinding.ActivitySplashBinding
import com.example.foodrecipeapp.entities.Category
import com.example.foodrecipeapp.entities.Meal
import com.example.foodrecipeapp.entities.converter.MealsItems
import com.example.foodrecipeapp.entities.retrofitclient.RetrofitClientInstance
import com.example.foodrecipeapp.`interface`.GetDataService
import com.example.foodrecipeapp.utils.startActivity
import com.example.foodrecipeapp.views.base.BaseActivity
import com.example.foodrecipeapp.views.home.HomeActivity
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(), EasyPermissions.RationaleCallbacks,
    EasyPermissions.PermissionCallbacks {

    private var READ_STORAGE_PERM = 123
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readStorageTask()

        binding.btnGetStarted.setOnClickListener {
            startActivity<HomeActivity>()
            finish()
        }
    }

    fun getCategories() {
        val service = RetrofitClientInstance.retrofitInstance.create(GetDataService::class.java)
        val call = service.getCategoryList()
        call.enqueue(object : Callback<Category> {

            override fun onResponse(
                call: Call<Category>,
                response: Response<Category>
            ) {

                for (arr in response.body()!!.categorieitems!!) {
                    getMeal(arr.strcategory)
                }
                insertDataIntoRoomDb(response.body())
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {

                Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun getMeal(categoryName: String) {
        val service = RetrofitClientInstance.retrofitInstance.create(GetDataService::class.java)
        val call = service.getMealList(categoryName)
        call.enqueue(object : Callback<Meal> {

            override fun onResponse(
                call: Call<Meal>,
                response: Response<Meal>
            ) {
                insertMealDataIntoRoomDb(categoryName, response.body())
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {

                Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun insertDataIntoRoomDb(category: Category?) {

        launch {
            this.let {
                for (arr in category?.categorieitems!!) {
                    RecipeDatabase.getDataBase(this@SplashActivity)
                        .recipeDao()
                        .insertCategory(arr)
                }
            }
        }

    }

    fun insertMealDataIntoRoomDb(categoryName: String, meal: Meal?) {

        launch {
            this.let {
                for (arr in meal!!.mealItems!!) {
                    var mealItemModel = MealsItems(
                        arr.id,
                        arr.idMeal,
                        categoryName,
                        arr.strMeal,
                        arr.strMealThumb
                    )
                    RecipeDatabase.getDataBase(this@SplashActivity)
                        .recipeDao()
                        .insertMeal(mealItemModel)
                    Log.d("mealData", arr.toString())
                }
                binding.btnGetStarted.visibility = View.VISIBLE
            }
        }

    }

    fun clearDataBase() {
        launch {
            this.let {
                RecipeDatabase.getDataBase(this@SplashActivity).recipeDao().clearDb()
            }
        }
    }

    private fun hasReadStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun readStorageTask() {
        Log.d("coba", "readStorageTask: ${hasReadStoragePermission()}")
        getCategories()
        clearDataBase()
        if (hasReadStoragePermission()) {
        } else {
            Log.d("coba", "readStorageTask: ${hasReadStoragePermission()}")
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your storage",
                READ_STORAGE_PERM,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }


}