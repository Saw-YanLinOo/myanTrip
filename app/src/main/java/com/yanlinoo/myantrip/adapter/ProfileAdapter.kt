package com.yanlinoo.myantrip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.yanlinoo.myantrip.R
import com.yanlinoo.myantrip.model.PostItem
import kotlinx.android.synthetic.main.profile_item_recyclerview.view.*

class ProfileAdapter(_context: Context, _postist:List<PostItem>) : RecyclerView.Adapter<ProfileAdapter.BlogViewHolder>() {

    val context = _context
    val postList = _postist

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.profile_item_recyclerview,parent,false)
        return BlogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        holder.bind(context,postList[position])
    }

    class BlogViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(context: Context,postItem: PostItem){
            Glide.with(context)
                .load(postItem.post_image_1)
                .placeholder(R.drawable.image)
                .into(itemView.img_post_image)

            Glide.with(context) //1
                .load(postItem.profile)
                .placeholder(R.drawable.image)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .transform(CircleCrop()) //4
                .into(itemView.img_profiles)
            itemView.tv_username.text = postItem.username
        }
    }
}