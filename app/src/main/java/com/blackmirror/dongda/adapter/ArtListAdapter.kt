package com.blackmirror.dongda.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.CareMoreDomainBean
import com.blackmirror.dongda.utils.OSSUtils
import com.facebook.drawee.view.SimpleDraweeView

class ArtListAdapter(protected var context: Context, private val bean: CareMoreDomainBean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var totalCount: Int = 0

    private var item: ((View, Int, String?) -> Unit)? =null
    private var like: ((View, Int, CareMoreDomainBean.ServicesBean) -> Unit)? = null

    fun setOnArtListClickListener(it: (View, Int, String?) -> Unit={view,pos,id->},li:(View, Int, CareMoreDomainBean.ServicesBean) -> Unit={view,pos,sb->}) {
        item = it
        like = li
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == bean.services!!.size) TYPE_FOOTER else TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_NORMAL) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_art_list, parent, false)
            return ArtListViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_footer, parent, false)
            return ArtFooterViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ArtListViewHolder) {
            val servicesBean = this.bean.services!![position]

            val url = OSSUtils.getSignedUrl(servicesBean.service_image, (30 * 60).toLong())

            holder.sv_art_list_photo.setImageURI(url)
            if (servicesBean.is_collected) {
                holder.iv_item_art_like.setBackgroundResource(R.drawable.like_selected)
            } else {
                holder.iv_item_art_like.setBackgroundResource(R.drawable.home_art_like)
            }
            if (TextUtils.isEmpty(servicesBean.operation!![0])) {
                holder.tv_art_list_name.visibility = View.GONE
            } else {
                holder.tv_art_list_name.visibility = View.VISIBLE
                holder.tv_art_list_name.text = servicesBean.operation!![0]
            }
            val sb = StringBuilder()
            sb.append(servicesBean.brand_name)
                    .append(context.getString(R.string.str_de))
                    .append(if (servicesBean.operation!!.contains(context.getString(R.string.low_age))) context.getString(R.string.low_age) else "")
                    .append(servicesBean.service_leaf)
            holder.tv_art_list_content.text = sb.toString()
            holder.tv_art_list_location.text = servicesBean.address!!.substring(0, servicesBean.address!!.indexOf(context.getString(R.string.str_qu)) + 1)
            initListener(holder, position, servicesBean)
        } else if (holder is ArtFooterViewHolder) {
            /*LogUtils.d("totalCount "+totalCount+" size "+getItemCount());
            ArtFooterViewHolder vh= (ArtFooterViewHolder) holder;
            if (getItemCount()-1<totalCount){
                vh.cl_art_root.setVisibility(View.GONE);
            }else {
                vh.cl_art_root.setVisibility(View.VISIBLE);
                vh.cl_art_root.setPadding(0, OtherUtils.dp2px(48),0,OtherUtils.dp2px(44));
            }*/
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>?) {

        if (payloads!!.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val vh = holder as ArtListViewHolder
            val isLike = payloads[0] as Boolean
            bean.services!![position].is_collected = isLike
            if (isLike) {
                vh.iv_item_art_like.setBackgroundResource(R.drawable.like_selected)
            } else {
                vh.iv_item_art_like.setBackgroundResource(R.drawable.home_art_like)
            }
        }
    }

    private fun initListener(holder: ArtListViewHolder, position: Int, servicesBean: CareMoreDomainBean.ServicesBean) {
        holder.itemView.setOnClickListener {
            item?.invoke(holder.iv_item_art_like, holder.adapterPosition, servicesBean.service_id)
            /*if (listener != null) {
                val pos = holder.adapterPosition
                listener!!.onItemArtListClick(holder.itemView, pos, servicesBean.service_id)
            }*/
        }
        holder.iv_item_art_like.setOnClickListener {
            like?.invoke(holder.iv_item_art_like, holder.adapterPosition, servicesBean)
            /*if (listener != null) {
                val pos = holder.adapterPosition
                listener!!.onItemArtLikeClick(holder.iv_item_art_like, pos, servicesBean)
            }*/
        }
    }

    override fun getItemCount(): Int {
        return bean?.services?.size?:0
    }

    class ArtFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cl_art_root: ConstraintLayout

        init {
            cl_art_root = itemView.findViewById(R.id.cl_art_root)
        }
    }


    class ArtListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sv_art_list_photo: SimpleDraweeView
        var iv_item_art_like: ImageView
        var tv_art_list_name: TextView
        var tv_art_list_content: TextView
        var tv_art_list_location: TextView

        init {
            sv_art_list_photo = itemView.findViewById(R.id.sv_art_list_photo)
            iv_item_art_like = itemView.findViewById(R.id.iv_item_art_like)
            tv_art_list_name = itemView.findViewById(R.id.tv_art_list_name)
            tv_art_list_content = itemView.findViewById(R.id.tv_art_list_content)
            tv_art_list_location = itemView.findViewById(R.id.tv_art_list_location)

        }
    }

    fun setMoreData(moreList: MutableList<CareMoreDomainBean.ServicesBean>?) {
        moreList?.apply {
            bean.services?.addAll(this)
        }
        notifyDataSetChanged()
    }


    fun setRefreshData(list: MutableList<CareMoreDomainBean.ServicesBean>?) {

        bean.services?.let {
            it.clear()
            list
        }?.apply {
            bean.services?.addAll(this)
        }

        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView!!.layoutManager

        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (getItemViewType(position) == TYPE_FOOTER) {
                        layoutManager.spanCount
                    } else {
                        1
                    }
                }
            }
        }
    }

    companion object {
        private val TYPE_FOOTER = 100
        private val TYPE_NORMAL = 101
    }

}

