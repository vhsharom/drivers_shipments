package com.example.platform_science_code_exercise.drivers.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.platform_science_code_exercise.drivers.model.Driver
import com.example.platform_science_code_exercise.R
import com.example.platform_science_code_exercise.drivers.model.Shipment
import java.io.BufferedReader
import java.io.InputStreamReader
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson

class DriversViewModel : ViewModel() {

    private val TAG = "DriversViewModel"

    // Create a live data for drivers array
    private var _drivers: MutableLiveData<List<Driver>> = MutableLiveData()
    val drivers: LiveData<List<Driver>>
        get() = _drivers

    private var _shipment: MutableLiveData<Shipment> = MutableLiveData()
    val shipment: LiveData<Shipment>
        get() = _shipment

    var driver: Driver? = null
    private var shipments: List<Shipment>? = null

    val assignments = mutableMapOf<String, String>()

    fun getDriversAndShipments(context: Context) {
        // parse a json file from raw/data.json
        val jsonFile = context.resources.openRawResource(R.raw.data)
        val jsonReader = BufferedReader(InputStreamReader(jsonFile))

        val type = object : TypeToken<Map<String, List<String>>>() {}.type
        val dataMap: Map<String, List<String>> = Gson().fromJson(jsonReader, type)

        val shipments: List<String>? = dataMap["shipments"]
        val drivers: List<String>? = dataMap["drivers"]

        val driverList: List<Driver> = drivers?.map { Driver(it) } ?: emptyList()
        val shipmentsList: List<Shipment> = shipments?.map { Shipment(it) } ?: emptyList()

        this.shipments = shipmentsList
        _drivers.value = driverList
    }

    private fun getVowelCount(str: String): Int {
        val vowels = listOf('a', 'e', 'i', 'o', 'u')
        return str.toLowerCase().count { it in vowels }
    }

    private fun getConsonantCount(str: String): Int {
        val vowels = listOf('a', 'e', 'i', 'o', 'u')
        return str.toLowerCase().count { it !in vowels && it.isLetter() }
    }

    private fun hasCommonFactors(n1: Int, n2: Int): Boolean {
        for (i in 2..minOf(n1, n2)) {
            if (n1 % i == 0 && n2 % i == 0) {
                return true
            }
        }
        return false
    }

    private fun getScore(driver: String, shipment: String): Double {
        val baseSS = if (shipment.length % 2 == 0) {
            getVowelCount(driver) * 1.5
        } else {
            getConsonantCount(driver) * 1.0
        }
        return if (hasCommonFactors(driver.length, shipment.length)) {
            baseSS * 1.5
        } else {
            baseSS
        }
    }

    fun assignShipments() {
        drivers.value?.let { drivers ->
            shipments?.let { shipments ->
                val sortedDrivers = drivers.map { driver ->
                    driver.name
                }.sortedByDescending {
                    getVowelCount(it) + getConsonantCount(it)
                }
                val sortedShipments = shipments.map { shipment ->
                    shipment.destination
                }.sortedByDescending {
                    it.length
                }.toMutableList()

                for (driver in sortedDrivers) {
                    var maxSS = Double.MIN_VALUE
                    var bestShipment: String? = null
                    for (shipment in sortedShipments) {
                        val SS = getScore(driver, shipment)
                        if (SS > maxSS) {
                            maxSS = SS
                            bestShipment = shipment
                        }
                    }
                    if (bestShipment != null) {
                        assignments[driver] = bestShipment
                        Log.d(TAG, "Driver: $driver, bestShipment: $bestShipment")
                        sortedShipments.remove(bestShipment)
                    }
                }
            }
        }
    }

    fun getShipment() {
        shipments?.let { shipments ->
            val shipment = shipments.find { it.destination == assignments[driver?.name] }
            shipment?.let {
                it.score = getScore(driver?.name ?: "", it.destination)
                _shipment.value = it
            }
        }
    }
}