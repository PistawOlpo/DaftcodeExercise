package com.example.pistaw.daftcodeexercise

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.bar_data_item.view.*

class MyAdapter(private val bar: ArrayList<BarData>, private val clickListener: (BarData, MotionEvent) -> Boolean) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    // Provide a reference to the views for each data item
    class ViewHolder(private val mItemView: View) : RecyclerView.ViewHolder(mItemView) {

        fun bind(bar: BarData, clickListener: (BarData, MotionEvent) -> Boolean) {

            mItemView.bar_data_count.text = (bar.counter * 3).toString()

            if (bar.color == ColorBar.Blue) {
                mItemView.circle_view.setBackgroundResource(R.drawable.circle_blue)
                mItemView.bar_data_count.text = (bar.counter).toString()
            } else {
                mItemView.circle_view.setBackgroundResource(R.drawable.circle_red)
                mItemView.bar_data_count.text = (bar.counter * 3).toString()
            }
            itemView.setOnTouchListener({ _: View, m: MotionEvent -> clickListener(bar, m) })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.ViewHolder {
        // create a new view
        val barView = LayoutInflater.from(parent.context)
                .inflate(R.layout.bar_data_item, parent, false) as View
        // set the view's size, margins, padding's and layout parameters

        return ViewHolder(barView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from data set at this position
        // - replace the contents of the view with that element
        holder.bind(bar[position], clickListener)
    }


    // Return the size of your data set (invoked by the layout manager)
    override fun getItemCount() = bar.size
}