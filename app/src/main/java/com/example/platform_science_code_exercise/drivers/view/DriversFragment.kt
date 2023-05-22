package com.example.platform_science_code_exercise.drivers.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.platform_science_code_exercise.drivers.viewmodel.DriversViewModel
import androidx.fragment.app.activityViewModels
import com.example.platform_science_code_exercise.core.NavigationHelper
import com.example.platform_science_code_exercise.core.OnItemClickListener
import com.example.platform_science_code_exercise.databinding.FragmentDriversBinding
import com.example.platform_science_code_exercise.drivers.DriversAdapter
import com.example.platform_science_code_exercise.drivers.model.Driver

class DriversFragment : Fragment(), OnItemClickListener {

    private val TAG = "DriversFragment"
    private val viewModel: DriversViewModel by activityViewModels()
    private var driversAdapter: DriversAdapter? = null

    // Binding views
    private var views: FragmentDriversBinding? = null

    companion object {
        @JvmStatic
        fun newInstance() = DriversFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentDriversBinding.inflate(inflater, container, false).run {
            views = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDriversAndShipments(requireContext())
        viewModel.assignShipments()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.drivers.observe(viewLifecycleOwner) { drivers ->
            driversAdapter = DriversAdapter()
            driversAdapter?.setOnItemClickListener(this)
            driversAdapter?.drivers = ArrayList(drivers)
            views?.rvDrivers?.adapter = driversAdapter
        }
    }

    override fun onItemClick(driver: Driver) {
        Log.d(TAG, "onItemClick: ${driver.name}");
        viewModel.driver = driver
        NavigationHelper.loadDriverDetail(requireActivity())
    }
}