package com.blackmirror.dongda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.HomepageDomainBean
import com.blackmirror.dongda.utils.getSignedUrl
import com.facebook.drawee.view.SimpleDraweeView

class HomeCareAdapter(protected var context: Context, private val bean: HomepageDomainBean.HomepageServicesBean) : RecyclerView.Adapter<HomeCareAdapter.HomeCareViewHolder>() {

    private var item: ((View, Int, String?) -> Unit)? = null

    fun setOnCareClickListener(it: ((View, Int, String?) -> Unit)? = null) {
        item = it
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCareViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_care, parent, false)
        return HomeCareViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeCareViewHolder, position: Int) {

        val servicesBean = this.bean.services!![position]

        val url = getSignedUrl(servicesBean.service_image, (30 * 60).toLong())
        holder.sv_care_photo.setImageURI(url)

        holder.tv_care_name.text = this.bean.services!![position].service_tags!![0]
        val sb = StringBuilder()
        sb.append(servicesBean.brand_name)
                .append(context.getString(R.string.str_de))
                .append(if (servicesBean.operation!!.contains(context.getString(R.string.low_age))) context.getString(R.string.low_age) else "")
                .append(servicesBean.service_leaf)
        holder.tv_care_detail.text = sb.toString()
        holder.tv_care_location.text = servicesBean.address!!.substring(0, servicesBean.address!!.indexOf(context.getString(R.string.str_qu)) + 1)
        initListener(holder, position, servicesBean)

    }

    private fun initListener(holder: HomeCareViewHolder, position: Int, servicesBean: HomepageDomainBean.HomepageServicesBean.ServicesBean) {
        holder.itemView.setOnClickListener {
            item?.invoke(holder.itemView, holder.adapterPosition, servicesBean.service_id)
        }
    }

    override fun getItemCount(): Int {
        return bean?.services?.size ?: 0
    }

    class HomeCareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sv_care_photo: SimpleDraweeView
        var tv_care_name: TextView
        var tv_care_detail: TextView
        var tv_care_location: TextView

        init {
            sv_care_photo = itemView.findViewById(R.id.sv_care_photo)
            tv_care_name = itemView.findViewById(R.id.tv_care_name)
            tv_care_detail = itemView.findViewById(R.id.tv_care_detail)
            tv_care_location = itemView.findViewById(R.id.tv_care_location)

        }
    }

    fun setRefreshData(list: MutableList<HomepageDomainBean.HomepageServicesBean.ServicesBean>?) {
        list?.apply {
            bean.services?.clear()
            bean.services?.addAll(this)
        }
        notifyDataSetChanged()
    }

}
