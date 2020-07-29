package com.yanlinoo.myantrip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.smarteist.autoimageslider.SliderAnimations
import com.yanlinoo.myantrip.R
import com.yanlinoo.myantrip.model.PostItem
import kotlinx.android.synthetic.main.blog_item_recyclerviews.view.*
import kotlinx.android.synthetic.main.profile_item_recyclerview.view.*
import kotlinx.android.synthetic.main.profile_item_recyclerview.view.tv_username

class BlogAdapter(_context: Context, _postist:List<PostItem>) : RecyclerView.Adapter<BlogAdapter.BlogViewHolder>(){

    val context = _context
    val postList = _postist

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.blog_item_recyclerviews,parent,false)
        return BlogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        holder.bind(context,postList[position])
    }
    class BlogViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var  imageList = ArrayList<String>()

        fun bind(context: Context, postItem: PostItem){
            Glide.with(context) //1
                .load(postItem.profile)
                .placeholder(R.drawable.image)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .transform(CircleCrop()) //4
                .into(itemView.img_profile)
            itemView.tv_username.text = postItem.username
            itemView.tv_descripton.text = postItem.description
            itemView.tv_Like.text = postItem.post_like
            if (postItem.post_view != "")itemView.tv_viwer.text = postItem.post_view

            //imageList = listOf(postItem.post_image_1,postItem.post_image_2,postItem.post_image_3,postItem.post_image_4,postItem.post_image_5)
            if ( postItem.post_image_1 != "") imageList.add(postItem.post_image_1)
            if (postItem.post_image_2 != "") imageList.add(postItem.post_image_2)
            if ( postItem.post_image_3 != "") imageList.add(postItem.post_image_3)
            if ( postItem.post_image_4 != "") imageList.add(postItem.post_image_4)
            if ( postItem.post_image_5 != "") imageList.add(postItem.post_image_5)
            itemView.imageSlider.setSliderAdapter(ImageSliderAdapter(context,imageList))
            itemView.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            itemView.imageSlider.setScrollTimeInSec(1000);
        }
    }
}