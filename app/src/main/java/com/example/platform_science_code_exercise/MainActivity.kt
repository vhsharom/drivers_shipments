package com.example.platform_science_code_exercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.platform_science_code_exercise.core.NavigationHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationHelper.loadDrivers(this)
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }
}