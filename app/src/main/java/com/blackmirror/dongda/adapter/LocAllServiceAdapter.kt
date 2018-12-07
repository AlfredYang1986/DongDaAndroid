package com.blackmirror.dongda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.LocAllServiceDomainBean
import com.blackmirror.dongda.utils.getSignedUrl
import com.blackmirror.dongda.utils.logD
import com.facebook.drawee.view.SimpleDraweeView

class LocAllServiceAdapter(protected var context: Context, private val bean: LocAllServiceDomainBean?) : RecyclerView.Adapter<LocAllServiceAdapter.LocAllServiceHolder>() {

    private var item: ((View, Int, LocAllServiceDomainBean.ServicesBean) -> Unit)? = null


    fun setOnItemClickListener(l: ((View, Int, LocAllServiceDomainBean.ServicesBean) -> Unit)? = null) {
        item = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocAllServiceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_loc_all_service, parent, false)
        return LocAllServiceHolder(view)
    }

    override fun onBindViewHolder(holder: LocAllServiceHolder, position: Int) {
        val servicesBean = this.bean!!.services!![position]
        val sb = StringBuilder()
        if (servicesBean.service_tags != null && servicesBean.service_tags!!.size != 0 && !TextUtils.isEmpty(servicesBean.service_tags!![0])) {
            sb.append(servicesBean.service_tags!![0])
                    .append("的")
        }
        sb.append(servicesBean.service_leaf)
        holder.tv_service_loc.text = sb.toString()
        val url = getSignedUrl(this.bean.services!![position].service_image)
        logD(url)
        holder.sv_service_photo.setImageURI(url)
        initListener(holder, position, this.bean.services!![position])

    }

    private fun initListener(holder: LocAllServiceHolder, position: Int, bean: LocAllServiceDomainBean.ServicesBean) {

        holder.itemView.setOnClickListener {
            item?.invoke(holder.itemView, holder.adapterPosition, bean)
        }
    }

    override fun getItemCount(): Int {
        return bean?.services?.size ?: 0
    }


    class LocAllServiceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sv_service_photo: SimpleDraweeView
        var tv_service_loc: TextView

        init {
            sv_service_photo = itemView.findViewById(R.id.sv_service_photo)
            tv_service_loc = itemView.findViewById(R.id.tv_service_loc)
        }
    }
}
