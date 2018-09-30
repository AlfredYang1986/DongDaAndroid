package com.blackmirror.dongda.adapter

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.HomeNowExpDomainBean
import com.blackmirror.dongda.utils.DensityUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

class HomeNowExperienceAdapter(protected var context: Context, internal var bean: HomeNowExpDomainBean) :
        RecyclerView.Adapter<HomeNowExperienceAdapter.NowExpViewHolder>() {

    private var item: ((View, Int) -> Unit)? = null

    fun setOnItemClickListener(l: ((View, Int) -> Unit)? = null) {
        item = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowExpViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_now_exp, parent, false)
        return NowExpViewHolder(view)
    }

    override fun onBindViewHolder(holder: NowExpViewHolder, position: Int) {

//        displayImage(OtherUtils.resourceIdToUri(context, list!![position]), holder.sv_experience_photo)

        //        holder.sv_featured.setImageURI();
        //添加点击事件
        holder.itemView.setOnClickListener {
            item?.invoke(holder.itemView, holder.layoutPosition)
        }

    }

    override fun getItemCount(): Int {
        return 6
    }

    private fun displayImage(uri: Uri, draweeView: SimpleDraweeView) {
        val options = ImageDecodeOptions.newBuilder().setBitmapConfig(Bitmap.Config.RGB_565).build()
        val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(ResizeOptions(DensityUtils.dp2px(168), DensityUtils.dp2px(263)))
                .setImageDecodeOptions(options)
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.controller)
                .build()
        draweeView.controller = controller
    }


    class NowExpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var sv_exp_brand_logo: SimpleDraweeView
         var tv_exp_project_name: TextView
         var tv_course_name: TextView
         var tv_age_range: TextView
         var tv_course_dec: TextView
         var tv_course_adv_1: TextView
         var tv_course_adv_2: TextView
         var tv_course_adv_3: TextView
         var tv_course_loc: TextView
         var iv_order_icon: ImageView


        init {
            sv_exp_brand_logo = itemView.findViewById(R.id.sv_exp_brand_logo)
            tv_exp_project_name = itemView.findViewById(R.id.tv_exp_project_name)
            tv_course_name = itemView.findViewById(R.id.tv_course_name)
            tv_age_range = itemView.findViewById(R.id.tv_age_range)
            tv_course_dec = itemView.findViewById(R.id.tv_course_dec)
            tv_course_adv_1 = itemView.findViewById(R.id.tv_course_adv_1)
            tv_course_adv_2 = itemView.findViewById(R.id.tv_course_adv_2)
            tv_course_adv_3 = itemView.findViewById(R.id.tv_course_adv_3)
            tv_course_loc = itemView.findViewById(R.id.tv_course_loc)
            iv_order_icon = itemView.findViewById(R.id.iv_order_icon)

        }
    }

}
