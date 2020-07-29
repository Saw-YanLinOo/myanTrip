package com.yanlinoo.myantrip.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yanlinoo.myantrip.R
import com.yanlinoo.myantrip.model.WordItem
import kotlinx.android.synthetic.main.communication_word_menu_category.view.*

class CommunicatinCategoryRecyclerViewAdapter(_context: Context, _menuList :List<WordItem>):RecyclerView.Adapter<CommunicatinCategoryRecyclerViewAdapter.MenuViewHolder>(){

    val context = _context
    var menuList = _menuList
    var buttonClick:((WordItem)->Unit)? = null

    fun setItems(words:List<WordItem>){
        this.menuList
        this.menuList = words
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.communication_word_menu_category,parent,false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuList[position])
//        holder.itemView.btn_categories.text = menuList[position].category
//        holder?.itemView?.btn_categories.setOnClickListener {
//            clickListener(menuList[position],position)
//        }
    }

    inner class MenuViewHolder(private val view:View):RecyclerView.ViewHolder(view){

        init {
            itemView.btn_categories.setOnClickListener{
                buttonClick?.invoke(menuList[adapterPosition])
            }
        }
        fun bind(words:WordItem){
            view.btn_categories.text = words.category

        }

    }
}
