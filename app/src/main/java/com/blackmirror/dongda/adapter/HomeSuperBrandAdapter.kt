package com.blackmirror.dongda.adapter

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.HomeSuperBrandDomainBean
import com.blackmirror.dongda.utils.DensityUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

class HomeSuperBrandAdapter(protected var context: Context) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val TYPE_NORMAL = 100
    val TYPE_HEADER = 101

    var list = mutableListOf<HomeSuperBrandDomainBean>()

    private var item: ((View, Int) -> Unit)? = null

    fun setOnItemClickListener(l: ((View, Int) -> Unit)? = null) {
        item = l
    }

    override fun getItemViewType(position: Int) = when (list[position].view_type) {
        0 -> TYPE_NORMAL
        1 -> TYPE_HEADER
        else -> TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_NORMAL -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_super_brand_content, parent, false)
                BrandNormalViewHolder(view)
            }
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_super_brand_header, parent, false)
                BrandHeaderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_super_brand_content, parent, false)
                BrandNormalViewHolder(view)
            }
        }

        /*val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_super_brand_content, parent, false)
        return SuperBrandViewHolder(view)*/
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

//        displayImage(OtherUtils.resourceIdToUri(context, list!![position]), holder.sv_experience_photo)

        //        holder.sv_featured.setImageURI();
        //添加点击事件
        holder.itemView.setOnClickListener {
            item?.invoke(holder.itemView, holder.layoutPosition)
        }

    }

    override fun getItemCount(): Int {
        return list.size
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


    class BrandNormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sv_super_photo: SimpleDraweeView
        var iv_super_love: ImageView
        var tv_course_name: TextView
        var tv_age_range: TextView
        var tv_brand_course_name: TextView



        init {
            sv_super_photo = itemView.findViewById(R.id.sv_super_photo)
            iv_super_love = itemView.findViewById(R.id.iv_super_love)
            tv_course_name = itemView.findViewById(R.id.tv_course_name)
            tv_age_range = itemView.findViewById(R.id.tv_age_range)
            tv_brand_course_name = itemView.findViewById(R.id.tv_brand_course_name)


        }
    }


    class BrandHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sv_brand_logo: SimpleDraweeView
        var tv_course_loc: TextView
        var tv_course_dec: TextView

        init {
            sv_brand_logo = itemView.findViewById(R.id.sv_brand_logo)
            tv_course_loc = itemView.findViewById(R.id.tv_course_loc)
            tv_course_dec = itemView.findViewById(R.id.tv_course_dec)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {

            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (list[position].view_type == 0) {
                        return 1
                    } else {
                        return manager.spanCount
                    }
                }
            }

        }
    }

}
