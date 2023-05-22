package com.example.platform_science_code_exercise.drivers.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.platform_science_code_exercise.databinding.FragmentDriverDetailBinding
import com.example.platform_science_code_exercise.drivers.viewmodel.DriversViewModel

class DriverDetailFragment : Fragment() {

    private val TAG = "DriverDetailFragment"
    private val viewModel: DriversViewModel by activityViewModels()

    // Binding views
    private var views: FragmentDriverDetailBinding? = null

    companion object {
        @JvmStatic
        fun newInstance() = DriverDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDriverDetailBinding.inflate(inflater, container, false).run {
            views = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        viewModel.getShipment()
    }

    private fun observeLiveData() {
        viewModel.shipment.observe(viewLifecycleOwner) { shipment ->
            Log.d(TAG, "observeLiveData: ${shipment.destination}")
            views?.run {
                tvDriver.text = "Driver: ${viewModel.driver?.name}"
                tvShipment.text = "Destination: ${shipment.destination}, Score: ${shipment.score}"
            }
        }
    }
}