package com.example.platform_science_code_exercise.core

import androidx.fragment.app.FragmentActivity
import com.example.platform_science_code_exercise.R
import com.example.platform_science_code_exercise.drivers.view.DriverDetailFragment
import com.example.platform_science_code_exercise.drivers.view.DriversFragment

object NavigationHelper {

    fun loadDrivers(activity: FragmentActivity?) {
        activity?.supportFragmentManager?.run {
            beginTransaction()
                .replace(R.id.main_container, DriversFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    fun loadDriverDetail(activity: FragmentActivity?) {
        activity?.supportFragmentManager?.run {
            beginTransaction()
                .replace(R.id.main_container, DriverDetailFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }
}