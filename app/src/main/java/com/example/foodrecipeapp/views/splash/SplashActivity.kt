package com.example.foodrecipeapp.views.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.databinding.ActivitySplashBinding
import com.example.foodrecipeapp.utils.startActivity
import com.example.foodrecipeapp.views.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetStarted.setOnClickListener {
            startActivity<HomeActivity>()
            finish()
        }
    }
}