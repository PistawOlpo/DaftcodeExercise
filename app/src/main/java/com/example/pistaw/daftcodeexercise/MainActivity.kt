package com.example.pistaw.daftcodeexercise

import android.app.Activity
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import java.util.*

class MainActivity : Activity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mGestureDetector: GestureDetectorCompat
    private lateinit var mBarItemClicked: BarData

    var barList = ArrayList<BarData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBars(10)
        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(barList, { barItem: BarData, motionEvent: MotionEvent -> barItemClicked(barItem, motionEvent) })

        recyclerView = findViewById<RecyclerView>(R.id.main_recycler_view).apply {
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter
            adapter = viewAdapter
        }
        mGestureDetector = GestureDetectorCompat(this, MyGestureListener())
    }

    private fun barItemClicked(barItem: BarData, motionEvent: MotionEvent): Boolean {
        mBarItemClicked = barItem
        mGestureDetector.onTouchEvent(motionEvent)
        return true
    }

    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val DEBUG_TAG = "Gestures"
        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            val previousBar: BarData

            if (mBarItemClicked.id == 0) {
                previousBar = barList[barList.lastIndex]
            } else {
                previousBar = barList[mBarItemClicked.id - 1]
            }

            mBarItemClicked.counter += previousBar.counter
            viewAdapter.notifyDataSetChanged()


            Log.d(DEBUG_TAG, "on Stingle Tap " + e.toString())
            return true
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {


            mBarItemClicked.counter = 0
            viewAdapter.notifyDataSetChanged()

            Log.d(DEBUG_TAG, "onDoubleTap: " + e.toString())
            return true
        }

    }

    private fun initBars(howMany: Int) {

        val rand = Random()
        var color: ColorBar

        for (i in 1..howMany) {
            if (rand.nextInt(2) == 1) color = ColorBar.Blue
            else color = ColorBar.Red

            barList.add(BarData(i - 1, rand.nextInt(10) + 1, color))

        }
    }
}

