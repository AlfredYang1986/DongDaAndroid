package com.blackmirror.dongda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.blackmirror.dongda.R
import com.blackmirror.dongda.model.SafeUiBean

class AddrDecInfoAdapter(protected var context: Context, private val list: List<SafeUiBean>?) : RecyclerView.Adapter<AddrDecInfoAdapter.AddrInfoViewHolder>() {
    private var listener: OnCareClickListener? = null


    fun setOnCareClickListener(listener: OnCareClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddrInfoViewHolder {
        //解决Recyclerview宽高失效的问题
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_addr_dec, parent, false)
        //        View view = View.inflate(parent.getContext(), R.layout.rv_item_addr_dec, null);
        return AddrInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddrInfoViewHolder, position: Int) {

        holder.iv_item_addr_photo.setBackgroundResource(list!![position].res_id)
        holder.tv_item_addr_dec.text = list[position].dec
        //        initListener(holder,position);

    }

    private fun initListener(holder: AddrInfoViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            if (listener != null) {
                val pos = holder.adapterPosition
                listener!!.onItemCareClick(holder.itemView, pos)
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    class AddrInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iv_item_addr_photo: ImageView
        var tv_item_addr_dec: TextView


        init {
            iv_item_addr_photo = itemView.findViewById(R.id.iv_item_addr_photo)
            tv_item_addr_dec = itemView.findViewById(R.id.tv_item_addr_dec)
        }
    }


    interface OnCareClickListener {
        fun onItemCareClick(view: View, position: Int)
    }

}
