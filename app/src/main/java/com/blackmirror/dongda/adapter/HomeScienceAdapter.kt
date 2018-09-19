package com.blackmirror.dongda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.HomepageDomainBean
import com.blackmirror.dongda.utils.OSSUtils
import com.facebook.drawee.view.SimpleDraweeView

class HomeScienceAdapter(protected var context: Context, internal var bean: HomepageDomainBean.HomepageServicesBean) : RecyclerView.Adapter<HomeScienceAdapter.HomeScienceViewHolder>() {

    private var item: ((View, Int, String?) -> Unit)? = null
    private var like: ((View, Int, HomepageDomainBean.HomepageServicesBean.ServicesBean) -> Unit)? = null

    fun setOnItemClickListener(it: ((View, Int, String?) -> Unit)? = null, li: ((View, Int, HomepageDomainBean.HomepageServicesBean.ServicesBean) -> Unit)? = null) {
        item = it
        like = li
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScienceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_home_science, parent, false)
        return HomeScienceViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeScienceViewHolder, position: Int) {


        val servicesBean = this.bean.services!![position]

        val url = OSSUtils.getSignedUrl(servicesBean.service_image, (30 * 60).toLong())
        holder.sv_item_science_photo.setImageURI(url)


        if (servicesBean.is_collected) {
            holder.iv_item_science_like.setBackgroundResource(R.drawable.like_selected)
        } else {
            holder.iv_item_science_like.setBackgroundResource(R.drawable.home_art_like)
        }

        if (TextUtils.isEmpty(bean.services!![position].operation!![0])) {
            holder.tv_item_science_name.visibility = View.GONE
        } else {
            holder.tv_item_science_name.visibility = View.VISIBLE
            holder.tv_item_science_name.text = bean.services!![position].operation!![0]
        }

        val sb = StringBuilder()
        sb.append(servicesBean.brand_name)
                .append(context.getString(R.string.str_de))
                .append(servicesBean.service_leaf)
                .append(servicesBean.category)
        holder.tv_item_science_detail.text = sb.toString()
        holder.tv_item_science_location.text = servicesBean.address!!.substring(0, servicesBean.address!!.indexOf(context.getString(R.string.str_qu)) + 1)

        initListener(holder, position, servicesBean)

    }

    override fun onBindViewHolder(holder: HomeScienceViewHolder, position: Int, payloads: List<Any>?) {

        if (payloads!!.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val isLike = payloads[0] as Boolean
            bean.services!![position].is_collected = isLike
            if (isLike) {
                holder.iv_item_science_like.setBackgroundResource(R.drawable.like_selected)
            } else {
                holder.iv_item_science_like.setBackgroundResource(R.drawable.home_art_like)
            }
        }
    }

    private fun initListener(holder: HomeScienceViewHolder, position: Int, servicesBean: HomepageDomainBean.HomepageServicesBean.ServicesBean) {

        holder.itemView.setOnClickListener {
            item?.invoke(holder.iv_item_science_like, holder.layoutPosition, servicesBean.service_id)
        }

        holder.iv_item_science_like.setOnClickListener {
            like?.invoke(holder.iv_item_science_like, holder.layoutPosition, servicesBean)
        }
    }

    override fun getItemCount(): Int {

        return if (bean.services != null) {
            bean.services!!.size
        } else 0
    }

    fun setRefreshData(list: MutableList<HomepageDomainBean.HomepageServicesBean.ServicesBean>?) {
        list?.apply {
            bean.services?.clear()
            bean.services?.addAll(this)
        }
        notifyDataSetChanged()
    }

    class HomeScienceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sv_item_science_photo: SimpleDraweeView
        var iv_item_science_like: ImageView
        var tv_item_science_name: TextView
        var tv_item_science_detail: TextView
        var tv_item_science_location: TextView

        init {
            sv_item_science_photo = itemView.findViewById(R.id.sv_item_science_photo)
            iv_item_science_like = itemView.findViewById(R.id.iv_item_science_like)
            tv_item_science_name = itemView.findViewById(R.id.tv_item_science_name)
            tv_item_science_detail = itemView.findViewById(R.id.tv_item_science_detail)
            tv_item_science_location = itemView.findViewById(R.id.tv_item_science_location)

        }
    }
}
