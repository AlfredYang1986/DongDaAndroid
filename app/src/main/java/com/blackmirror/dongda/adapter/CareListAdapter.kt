package com.blackmirror.dongda.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.CareMoreDomainBean
import com.blackmirror.dongda.utils.dp2px
import com.blackmirror.dongda.utils.getScreenWidthDp
import com.blackmirror.dongda.utils.getSignedUrl
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

class CareListAdapter(protected var context: Context, private val bean: CareMoreDomainBean) : RecyclerView.Adapter<CareListAdapter.CareListViewHolder>() {

    private var item: ((View, Int, String?) -> Unit)? = null
    private var like: ((View, Int, CareMoreDomainBean.ServicesBean) -> Unit)? = null

    fun setOnCareListClickListener(it: ((View, Int, String?) -> Unit)? = null, li: ((View, Int, CareMoreDomainBean.ServicesBean) -> Unit)? = null) {
        item = it
        like = li
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareListAdapter.CareListViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_care_list, parent, false)
        return CareListViewHolder(view)
    }


    override fun onBindViewHolder(holder: CareListAdapter.CareListViewHolder, position: Int) {

        val servicesBean = this.bean.services!![position]


        val url = getSignedUrl(servicesBean.service_image, (30 * 60).toLong())
        //        holder.sv_care_list_photo.setImageURI(url);


        displayImage(Uri.parse(url), holder.sv_care_list_photo)

        if (servicesBean.is_collected) {
            holder.iv_care_list_like.setBackgroundResource(R.drawable.like_selected)
        } else {
            holder.iv_care_list_like.setBackgroundResource(R.drawable.home_art_like)
        }
        holder.tv_care_list_name.text = servicesBean.service_tags!![0]
        val sb = StringBuilder()
        sb.append(servicesBean.brand_name)
                .append(context.getString(R.string.str_de))
                .append(if (servicesBean.operation!!.contains(context.getString(R.string.low_age))) context.getString(R.string.low_age) else "")
                .append(servicesBean.service_leaf)
        holder.tv_care_list_content.text = sb.toString()
        holder.tv_care_list_location.text = servicesBean.address!!.substring(0, servicesBean.address!!.indexOf(context.getString(R.string.str_qu)) + 1)
        initListener(holder, position, servicesBean)

    }

    override fun onBindViewHolder(holder: CareListViewHolder, position: Int, payloads: List<Any>?) {

        if (payloads!!.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val isLike = payloads[0] as Boolean
            bean.services!![position].is_collected = isLike
            if (isLike) {
                holder.iv_care_list_like.setBackgroundResource(R.drawable.like_selected)
            } else {
                holder.iv_care_list_like.setBackgroundResource(R.drawable.home_art_like)
            }
        }
    }

    private fun initListener(holder: CareListViewHolder, position: Int, servicesBean: CareMoreDomainBean.ServicesBean) {
        holder.itemView.setOnClickListener {
            item?.invoke(holder.itemView, holder.adapterPosition, servicesBean.service_id)
        }
        holder.iv_care_list_like.setOnClickListener {
            like?.invoke(holder.iv_care_list_like, holder.adapterPosition, servicesBean)
        }
    }


    override fun getItemCount(): Int {
        return bean?.services?.size?:0
    }

    fun setMoreData(moreList: MutableList<CareMoreDomainBean.ServicesBean>?) {
        moreList?.let {
            bean.services?.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun setRefreshData(moreList: List<CareMoreDomainBean.ServicesBean>?) {
        moreList?.apply {
            bean.services?.clear()
            bean.services?.addAll(this)
        }
        notifyDataSetChanged()
    }


    class CareListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

    private fun displayImage(uri: Uri, draweeView: SimpleDraweeView) {

        val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(ResizeOptions(dp2px(getScreenWidthDp() - 32), dp2px(212)))
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.controller)
                .build()
        draweeView.controller = controller
    }

}
