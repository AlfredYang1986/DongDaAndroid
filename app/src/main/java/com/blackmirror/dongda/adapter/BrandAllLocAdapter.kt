package com.blackmirror.dongda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.BrandAllLocDomainBean

class BrandAllLocAdapter(protected var context: Context, private val bean: BrandAllLocDomainBean?) : RecyclerView.Adapter<BrandAllLocAdapter.BrandAllLocHolder>() {


    private var item: ((View, Int, BrandAllLocDomainBean.LocationsBean) -> Unit)? = null

    fun setOnItemClickListener(l: ((View, Int, BrandAllLocDomainBean.LocationsBean) -> Unit)? = null) {
        this.item = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandAllLocHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_brand_all_loc, parent, false)
        return BrandAllLocHolder(view)
    }

    override fun onBindViewHolder(holder: BrandAllLocHolder, position: Int) {
        holder.tv_brand_loc.text = bean!!.locations!![position].address
        initListener(holder, position, bean.locations!![position])

    }

    private fun initListener(holder: BrandAllLocHolder, position: Int, bean: BrandAllLocDomainBean.LocationsBean) {

        holder.itemView.setOnClickListener {
            item?.invoke(holder.itemView, holder.adapterPosition, bean)
        }
    }

    override fun getItemCount(): Int {

        return bean?.locations?.size ?: 0

    }


    class BrandAllLocHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_brand_loc: TextView
        var tv_brand_loc_detail: TextView

        init {
            tv_brand_loc = itemView.findViewById(R.id.tv_brand_loc)
            tv_brand_loc_detail = itemView.findViewById(R.id.tv_brand_loc_detail)
        }
    }

}
