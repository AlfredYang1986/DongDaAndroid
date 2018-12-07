package com.blackmirror.dongda.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
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

/**
 * Created by Ruge on 2018-04-08 下午4:12
 */
class FeaturedDetailAdapter(protected var context: Context, private val bean: CareMoreDomainBean?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<CareMoreDomainBean.ServicesBean>? = null
    var title: String? = null
    var content: String? = null
    var bg_resId: Int = 0

    private var item: ((View, Int, String?) -> Unit)? = null
    private var like: ((View, Int, CareMoreDomainBean.ServicesBean) -> Unit)? = null

    fun setOnDetailListClickListener(it: ((View, Int, String?) -> Unit)? = null, li: ((View, Int, CareMoreDomainBean.ServicesBean) -> Unit)? = null) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        HEAD_TYPE -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_featured_detail_head, parent, false)
            HeadViewHolder(view)
        }
        NORMAL_TYPE -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_featured_detail_normal, parent, false)
            NormalViewHolder(view)
        }
        else -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_featured_detail_footer, parent, false)
            FootViewHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeadViewHolder) {
            holder.iv_featured_detail_bg.setBackgroundResource(bg_resId)
            holder.tv_featured_detail_content.text = title
            holder.tv_item_head_content.text = content
        }
        if (holder is NormalViewHolder) {
/* //设置圆角
            RoundingParams roundingParams = new RoundingParams();
            roundingParams.setCornersRadii(OtherUtils.dp2px(4), OtherUtils.dp2px(4), 0, 0);
            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
            GenericDraweeHierarchy hierarchy = builder.build();
            hierarchy.setRoundingParams(roundingParams);
            vh.sv_featured_detail_photo.setHierarchy(hierarchy);*/

            val servicesBean = list!![position - 1]


            val url = getSignedUrl(servicesBean.service_image, (30 * 60).toLong())
            //        holder.sv_care_list_photo.setImageURI(url);
            //            urlSet.add(getCacheUrl(url));


            displayImage(Uri.parse(url), holder.sv_featured_detail_photo)

            if (servicesBean.is_collected) {
                holder.iv_featured_detail_like.setBackgroundResource(R.drawable.like_selected)
            } else {
                holder.iv_featured_detail_like.setBackgroundResource(R.drawable.home_art_like)
            }

            if (servicesBean.service_type!!.contains(context.getString(R.string.str_care))) {
                holder.tv_featured_detail_type.text = servicesBean.service_leaf
            } else {
                val sb = StringBuilder()
                sb.append(servicesBean.service_type)
                        .append("·")
                        .append(servicesBean.service_leaf)
                        .append(servicesBean.category)
                holder.tv_featured_detail_type.text = sb.toString()
            }

            val sb = StringBuilder()
            sb.append(servicesBean.brand_name)
                    .append("\"")
                    .append(servicesBean.punchline)
                    .append("\"")
            holder.tv_featured_detail_content.text = sb.toString()
            holder.tv_featured_detail_location.text = servicesBean.address!!.substring(0, servicesBean.address!!.indexOf(context.getString(R.string.str_qu)) + 1)
            initListener(holder, position, servicesBean)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>?) {

        if (payloads!!.isEmpty()) {
            onBindViewHolder(holder, position)
        } else if (holder is NormalViewHolder) {
            val isLike = payloads[0] as Boolean
            list!![position - 1].is_collected = isLike
            if (isLike) {
                holder.iv_featured_detail_like.setBackgroundResource(R.drawable.like_selected)
            } else {
                holder.iv_featured_detail_like.setBackgroundResource(R.drawable.home_art_like)
            }
        }
    }

    private fun initListener(holder: NormalViewHolder, position: Int, servicesBean: CareMoreDomainBean.ServicesBean) {
        holder.itemView.setOnClickListener {
            item?.invoke(holder.itemView, holder.adapterPosition, servicesBean.service_id)
        }

        holder.fl_featured_detail_like.setOnClickListener {
            like?.invoke(holder.fl_featured_detail_like, holder.adapterPosition, servicesBean)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 1
    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> HEAD_TYPE
        list!!.size + 1 -> FOOT_TYPE
        else -> NORMAL_TYPE
    }


    private fun displayImage(uri: Uri, draweeView: SimpleDraweeView) {

        val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(ResizeOptions(dp2px(getScreenWidthDp() - 32), dp2px(211)))
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.controller)
                .build()
        draweeView.controller = controller
    }

    class HeadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iv_featured_detail_bg: ImageView
        var tv_featured_detail_content: TextView
        var tv_item_head_content: TextView

        init {
            iv_featured_detail_bg = itemView.findViewById(R.id.iv_featured_detail_bg)
            tv_featured_detail_content = itemView.findViewById(R.id.tv_featured_detail_content)
            tv_item_head_content = itemView.findViewById(R.id.tv_item_head_content)
        }
    }

    class NormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cv_root: CardView
        var sv_featured_detail_photo: SimpleDraweeView
        var iv_featured_detail_like: ImageView
        var fl_featured_detail_like: FrameLayout
        var tv_featured_detail_content: TextView
        var tv_featured_detail_type: TextView
        var tv_featured_detail_location: TextView

        init {
            cv_root = itemView.findViewById(R.id.cv_root)
            sv_featured_detail_photo = itemView.findViewById(R.id.sv_featured_detail_photo)
            fl_featured_detail_like = itemView.findViewById(R.id.fl_featured_detail_like)
            iv_featured_detail_like = itemView.findViewById(R.id.iv_featured_detail_like)
            tv_featured_detail_content = itemView.findViewById(R.id.tv_featured_detail_content)
            tv_featured_detail_type = itemView.findViewById(R.id.tv_featured_detail_type)
            tv_featured_detail_location = itemView.findViewById(R.id.tv_featured_detail_location)
        }
    }

    class FootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iv_featured_detail_end: ImageView

        init {
            iv_featured_detail_end = itemView.findViewById(R.id.iv_featured_detail_end)
        }
    }

    interface OnDetailListClickListener {
        fun onItemDetailListClick(view: View, position: Int, service_id: String?)

        fun onItemDetailLikeClick(view: View, position: Int, servicesBean: CareMoreDomainBean.ServicesBean)
    }

    companion object {

        private val HEAD_TYPE = 1
        private val NORMAL_TYPE = 2
        private val FOOT_TYPE = 3
    }

}
