package com.blackmirror.dongda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.LikeDomainBean
import com.blackmirror.dongda.utils.getSignedUrl
import com.facebook.drawee.view.SimpleDraweeView

class MyLikeListAdapter(protected var context: Context, private val bean: LikeDomainBean?) : RecyclerView.Adapter<MyLikeListAdapter.MyLikeListViewHolder>() {

    private var list: MutableList<LikeDomainBean.ServicesBean>? = null

    private var item: ((View, Int, String?) -> Unit)? = null
    private var like: ((View, Int, LikeDomainBean.ServicesBean) -> Unit)? = null

    fun setOnLikeListClickListener(it: ((View, Int, String?) -> Unit)? = null, li: ((View, Int, LikeDomainBean.ServicesBean) -> Unit)? = null) {
        item = it
        like = li
    }

    init {
        if (bean == null || bean.services == null) {
            list = mutableListOf()
        } else {
            list = bean.services
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyLikeListAdapter.MyLikeListViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_care_list, parent, false)
        return MyLikeListViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyLikeListAdapter.MyLikeListViewHolder, position: Int) {


        val servicesBean = list!![position]

        val url = getSignedUrl(servicesBean.service_image, (30 * 60).toLong())
        holder.sv_care_list_photo.setImageURI(url)

        if (servicesBean.is_collected) {
            holder.iv_care_list_like.setBackgroundResource(R.drawable.like_selected)
        } else {
            holder.iv_care_list_like.setBackgroundResource(R.drawable.home_art_like)
        }

        holder.tv_care_list_name.text = list!![position].service_tags!![0]

        val sb = StringBuilder()
        if (servicesBean.service_leaf!!.contains(context.getString(R.string.str_care))) {
            sb.append(servicesBean.brand_name)
                    .append(context.getString(R.string.str_de))
                    .append(servicesBean.service_leaf)
        } else {
            sb.append(servicesBean.brand_name)
                    .append(context.getString(R.string.str_de))
                    .append(servicesBean.service_leaf)
                    .append(servicesBean.category)
        }


        holder.tv_care_list_content.text = sb.toString()
        holder.tv_care_list_location.text = servicesBean.address!!.substring(0, servicesBean.address!!.indexOf(context.getString(R.string.str_qu)) + 1)

        initListener(holder, position, servicesBean)

    }

    override fun onBindViewHolder(holder: MyLikeListViewHolder, position: Int, payloads: List<Any>?) {

        if (payloads!!.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val isLike = payloads[0] as Boolean
            list!![position].is_collected = isLike
            if (isLike) {
                holder.iv_care_list_like.setBackgroundResource(R.drawable.like_selected)
            } else {
                holder.iv_care_list_like.setBackgroundResource(R.drawable.home_art_like)
            }
        }
    }


    private fun initListener(holder: MyLikeListViewHolder, position: Int, servicesBean: LikeDomainBean.ServicesBean) {
        holder.itemView.setOnClickListener {
            item?.invoke(holder.itemView, holder.adapterPosition, servicesBean.service_id)
        }
        holder.iv_care_list_like.setOnClickListener {
            like?.invoke(holder.iv_care_list_like, holder.adapterPosition, servicesBean)
        }
    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    class MyLikeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sv_care_list_photo: SimpleDraweeView
        var iv_care_list_like: ImageView
        var tv_care_list_name: TextView
        var tv_care_list_content: TextView
        var tv_care_list_location: TextView

        init {
            sv_care_list_photo = itemView.findViewById(R.id.sv_care_list_photo)
            iv_care_list_like = itemView.findViewById(R.id.iv_care_list_like)
            tv_care_list_name = itemView.findViewById(R.id.tv_care_list_name)
            tv_care_list_content = itemView.findViewById(R.id.tv_care_list_content)
            tv_care_list_location = itemView.findViewById(R.id.tv_care_list_location)

        }
    }

    fun removeItem(position: Int) {
        list!!.removeAt(position)
        notifyItemRemoved(position)
        if (position != list!!.size) {
            notifyItemRangeChanged(position, list!!.size - position)
        }
    }

}
