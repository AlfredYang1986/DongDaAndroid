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
import com.blackmirror.dongda.kdomain.model.ModuleListDomainBean
import com.blackmirror.dongda.utils.dp2px
import com.blackmirror.dongda.utils.getScreenWidthDp
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

class ModuleListAdapter(protected var context: Context, private val bean: ModuleListDomainBean) :
        RecyclerView.Adapter<ModuleListAdapter.ModuleListViewHolder>() {

    private var item: ((View, Int, String?) -> Unit)? = null
    private var like: ((View, Int, CareMoreDomainBean.ServicesBean) -> Unit)? = null

    fun setOnCareListClickListener(it: ((View, Int, String?) -> Unit)? = null,
                                   li: ((View, Int, CareMoreDomainBean.ServicesBean) -> Unit)? = null) {
        item = it
        like = li
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleListAdapter.ModuleListViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_module_list, parent, false)
        return ModuleListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ModuleListAdapter.ModuleListViewHolder, position: Int) {



//        val url = OSSUtils.getSignedUrl(servicesBean.service_image, (30 * 60).toLong())
        //        holder.sv_care_list_photo.setImageURI(url);


//        displayImage(Uri.parse(url), holder.sv_care_list_photo)


//        initListener(holder, position, servicesBean)

    }

    override fun onBindViewHolder(holder: ModuleListViewHolder, position: Int, payloads: List<Any>?) {

        /*if (payloads!!.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val isLike = payloads[0] as Boolean
            bean.services!![position].is_collected = isLike
            if (isLike) {
                holder.iv_care_list_like.setBackgroundResource(R.drawable.like_selected)
            } else {
                holder.iv_care_list_like.setBackgroundResource(R.drawable.home_art_like)
            }
        }*/
    }

    /*private fun initListener(holder: ModuleListViewHolder, position: Int, servicesBean: CareMoreDomainBean.ServicesBean) {
        holder.itemView.setOnClickListener {
            item?.invoke(holder.itemView, holder.adapterPosition, servicesBean.service_id)
        }
        holder.iv_care_list_like.setOnClickListener {
            like?.invoke(holder.iv_care_list_like, holder.adapterPosition, servicesBean)
        }
    }*/


    override fun getItemCount(): Int {
        return 6
    }

    fun setMoreData(moreList: MutableList<CareMoreDomainBean.ServicesBean>?) {

        notifyDataSetChanged()
    }

    fun setRefreshData(moreList: List<CareMoreDomainBean.ServicesBean>?) {

        notifyDataSetChanged()
    }


    class ModuleListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sv_photo: SimpleDraweeView
        var iv_love: ImageView
        var tv_course_name: TextView
        var tv_point: TextView
        var tv_age_range: TextView
        var tv_age_unit: TextView
        var tv_course_title: TextView

        init {
            sv_photo = itemView.findViewById(R.id.sv_photo)
            iv_love = itemView.findViewById(R.id.iv_love)
            tv_course_name = itemView.findViewById(R.id.tv_course_name)
            tv_point = itemView.findViewById(R.id.tv_point)
            tv_age_range = itemView.findViewById(R.id.tv_age_range)
            tv_age_unit = itemView.findViewById(R.id.tv_age_unit)
            tv_course_title = itemView.findViewById(R.id.tv_course_title)

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
