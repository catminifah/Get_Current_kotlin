package com.example.get_current_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder


//import can get id from xml
// /* build.gradle(app)*/
// apply plugin: 'kotlin-android-extensions'
// buildTypes {
//        release {
//            minifyEnabled true
//        }
//    }
// if 'kotlin-android-extensions' error
// /* build.gradle(Project)*/ downgrade
// id 'org.jetbrains.kotlin.android' version '1.7.10' apply false
import kotlinx.android.synthetic.main.activity_trackingpage.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val BASE_URL="https://api.covidtracking.com/v1/"
private const val TAG = "Trackingpage"
private const val ALL_STATES = "ALL (Nationwide)"
class Trackingpage : AppCompatActivity() {
    private lateinit var currentlyShownData: List<CovidData>
    private lateinit var adapter: CovidSparkAdapter
    private lateinit var perStateDailyData: Map<String, List<CovidData>>
    private lateinit var nationnalDailyData: List<CovidData>
    private lateinit var adapterr: ArrayAdapter<String>
//    lateinit var binding: ActivityTrackingpageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trackingpage)
//        binding = ActivityTrackingpageBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)


        //tvMetriLable=findViewById<TextView>(R.id.tvMetriLable)

        val gson=GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()
        val covidService = retrofit.create(CovidService::class.java)
        //Fetch the nationnal data
        covidService.getNationalData().enqueue(object :Callback<List<CovidData>>{
            override fun onResponse(
                call: Call<List<CovidData>>,
                response: Response<List<CovidData>>
            ) {
                Log.e(TAG,"onResponse $response")
                val nationalData = response.body()
                if (nationalData == null){
                    Log.e(TAG,"Did not receive a valid response body")
                    return
                }
                setupEventListeners()
                nationnalDailyData = nationalData.reversed()
                Log.i(TAG,"Update graph with national data")
                updateDisplayWithData(nationnalDailyData)
            }

            override fun onFailure(call: Call<List<CovidData>>, t: Throwable) {
                Log.e(TAG,"onFailure $t")
            }

        })

        //Fetch the state data
        covidService.getStatesData().enqueue(object :Callback<List<CovidData>>{
            override fun onResponse(
                call: Call<List<CovidData>>,
                response: Response<List<CovidData>>
            ) {
                Log.e(TAG,"onResponse $response")
                val statesData = response.body()
                if (statesData == null){
                    Log.e(TAG,"Did not receive a valid response body")
                    return
                }
                perStateDailyData = statesData.reversed().groupBy {it.state }
                Log.i(TAG,"Update spinner with state names")
                // Update spinner with state names
                updateSpinnerWithStateData(perStateDailyData.keys)
            }

            override fun onFailure(call: Call<List<CovidData>>, t: Throwable) {
                Log.e(TAG,"onFailure $t")
            }

        })
    }

    private fun updateSpinnerWithStateData(stateNames: Set<String>) {
        val stateAbbreviationList = stateNames.toMutableList()
        stateAbbreviationList.sort()
        stateAbbreviationList.add(0,ALL_STATES)

        // Add state list as data source for the spinner

//        spinnerSelect=findViewById(R.id.spinnerSelect)
//        adapterr = ArrayAdapter(applicationContext,
//            com.google.android.material.R.layout.abc_search_dropdown_item_icons_2line,stateAbbreviationList)
//        spinnerSelect.setAdapter(adapterr)
//        spinnerSelect.setOnItemClickListener { parent, _, position, _ ->
//            val selectedState = parent.getItemAtPosition(position) as String
//            val selectedData = perStateDailyData[selectedState] ?: nationnalDailyData
//            updateDisplayWithData(selectedData)
//        }
    }

    private fun setupEventListeners() {
        // Add a listener for the user scrubbing on the chart
        sparkView.isScrubEnabled = true
        sparkView.setScrubListener { itemData ->
            if(itemData is CovidData) {
                updateInfoForDate(itemData)
            }
        }
        // Resspond to radio button selected events
        radioGroupTimeSelection.setOnCheckedChangeListener{ _, checkedId ->
            adapter.daysAgo = when (checkedId) {
                R.id.radioButtonWeek -> TimeScale.WEEK
                R.id.radioButtonMonth -> TimeScale.MONTH
                else -> TimeScale.MAX
            }
            adapter.notifyDataSetChanged()
        }
        radioGroupMetricSelection.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonNegative -> updateDisplayMetric(Metric.NEGATIVE)
                R.id.radioButtonPositive -> updateDisplayMetric(Metric.POSITIVE)
                R.id.radioButtonDeath -> updateDisplayMetric(Metric.DEATH)
            }
        }
    }

    private fun updateDisplayMetric(metric: Metric) {
        // Update the color of the chart
        val colorRes = when (metric) {
            Metric.NEGATIVE -> R.color.colorNegative
            Metric.POSITIVE -> R.color.colorPositive
            Metric.DEATH -> R.color.colorDeath
        }
        @ColorInt val colorInt = ContextCompat.getColor(this, colorRes)
        sparkView.lineColor = colorInt

        // Update the metric on the adapter
        adapter.metric = metric
        adapter.notifyDataSetChanged()
        tvMetricLable.setTextColor(colorInt)

        // Reset number and date shown in the bottom text views
        updateInfoForDate(currentlyShownData.last())
    }

    private fun updateDisplayWithData(dailyData: List<CovidData>) {
        currentlyShownData = dailyData
        // Creste a new SparkAdepter with the data
        adapter = CovidSparkAdapter(dailyData)
        sparkView.adapter = adapter
        // Update radio buttons to select the positive cases and max time by default
        radioButtonPositive.isChecked = true
        radioButtonMax.isChecked = true
        // Display metric for the most recent date
        updateDisplayMetric(Metric.POSITIVE)
    }

    private fun updateInfoForDate(covidData: CovidData) {
        val numCases = when (adapter.metric){
            Metric.NEGATIVE -> covidData.negativeIncrease
            Metric.POSITIVE -> covidData.positiveIncrease
            Metric.DEATH -> covidData.deathIncrease
        }
        tvMetricLable.text = NumberFormat.getInstance().format(numCases)
        val outputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        tvDateLabel.text = outputDateFormat.format(covidData.dateChecked)
    }
}