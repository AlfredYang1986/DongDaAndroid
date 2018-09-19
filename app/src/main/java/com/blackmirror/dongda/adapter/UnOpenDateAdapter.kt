package com.blackmirror.dongda.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.blackmirror.dongda.R
import com.blackmirror.dongda.model.UnOpenDateBean

class UnOpenDateAdapter(protected var context: Context, private val bean: UnOpenDateBean?) : RecyclerView.Adapter<UnOpenDateAdapter.UnOpenViewHolder>() {

    private var item: ((View, Int, Boolean) -> Unit)? = null

    fun setOnDateClickListener(l: ((View, Int, Boolean) -> Unit)? = null) {
        item = l
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnOpenDateAdapter.UnOpenViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_un_open_date, parent, false)
        return UnOpenViewHolder(view)
    }


    override fun onBindViewHolder(holder: UnOpenDateAdapter.UnOpenViewHolder, position: Int) {

        if (bean!!.list!![position] == "今") {
            holder.tv_date.setTextColor(Color.parseColor("#FFEB5353"))
        }
        holder.tv_date.text = bean.list!![position]

        if (position > 6) {
            initListener(holder, position)
        }

    }


    private fun initListener(holder: UnOpenViewHolder, position: Int) {
        holder.tv_date.setOnClickListener {
            if (holder.view_line.visibility == View.GONE) {
                holder.view_line.visibility = View.VISIBLE
                holder.tv_date.setTextColor(Color.parseColor("#FFD9D9D9"))

                item?.invoke(holder.itemView, holder.adapterPosition - 7 - bean!!.firstWeek + 1, true)

            } else {
                holder.view_line.visibility = View.GONE
                if (bean!!.list!![position] == "今") {
                    holder.tv_date.setTextColor(Color.parseColor("#FFEB5353"))
                } else {
                    holder.tv_date.setTextColor(Color.parseColor("#FF404040"))
                }

                item?.invoke(holder.itemView, holder.adapterPosition - 7 - bean.firstWeek + 1, false)

            }
        }
    }


    override fun getItemCount(): Int {
        return bean?.list?.size ?: 0
    }

    class UnOpenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_date: TextView
        var view_line: View

        init {
            tv_date = itemView.findViewById(R.id.tv_date)
            view_line = itemView.findViewById(R.id.view_line)
        }
    }
}
