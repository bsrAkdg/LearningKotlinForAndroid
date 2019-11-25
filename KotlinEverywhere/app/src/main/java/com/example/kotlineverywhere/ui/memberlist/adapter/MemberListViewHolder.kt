package com.example.kotlineverywhere.ui.memberlist.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlineverywhere.model.Member
import kotlinx.android.synthetic.main.fragment_member_detail.view.*
import kotlinx.android.synthetic.main.fragment_member_detail.view.textViewMemberName
import kotlinx.android.synthetic.main.fragment_member_detail.view.textViewMemberTitle
import kotlinx.android.synthetic.main.item_member.view.*

class MemberListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(memberModel: Member, onItemClickListener: ((memberModel: Member) -> Unit)?) {
        itemView.textViewMemberName.text = memberModel.name
        itemView.textViewMemberTitle.text = memberModel.title
        Glide
            .with(itemView.context)
            .load(memberModel.profileImage)
            .into(itemView.imageViewMemberPhoto)

        itemView.setOnClickListener {
            onItemClickListener?.invoke(memberModel)
        }
    }
}