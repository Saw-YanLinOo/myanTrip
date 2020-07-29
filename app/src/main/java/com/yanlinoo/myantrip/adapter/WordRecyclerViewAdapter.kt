package com.yanlinoo.myantrip.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yanlinoo.myantrip.R
import com.yanlinoo.myantrip.model.WordItem
import com.yanlinoo.myantrip.ui.communication.CommunicationActivity
import com.yanlinoo.myantrip.ui.communication.CommunicationFragment
import kotlinx.android.synthetic.main.activity_communication.view.*
import kotlinx.android.synthetic.main.communication_word_menu_category.view.*
import kotlinx.android.synthetic.main.word_item_recyclerview.view.*

class WordRecyclerViewAdapter(_context:Context, _wordList:MutableList<WordItem>) : RecyclerView.Adapter<WordRecyclerViewAdapter.WordViewHolder>(){

    val context = _context
    var wordList:MutableList<WordItem> = _wordList
    var onClickListener:((WordItem)->Unit)? = null

    fun setItems(words:MutableList<WordItem>){
        this.wordList.clear()
        this.wordList = words
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.word_item_recyclerview,parent,false)
        return WordViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(context,wordList[position])
    }

    inner class WordViewHolder(view: View) : RecyclerView.ViewHolder(view){
        init {
           itemView.Img_btn_speak.setOnClickListener(){
               onClickListener?.invoke(wordList[adapterPosition])
           }
        }
        fun bind(context: Context,word:WordItem){
            when(CommunicationFragment().language.value.toString()){
                "myanmar" -> {
                    itemView.parent_font.text = word.myn
                }
                "english" -> {
                itemView.parent_font.text = word.eng
                }
                else -> itemView.parent_font.text = word.myn
            }
//            itemView.parent_font.text = word.eng
//            itemView.child_font.text = word.myn
            itemView.setOnClickListener{

                var gson: Gson = GsonBuilder().create()
                val intent = Intent(context, CommunicationActivity::class.java)
                intent.putExtra("Word", gson.toJson(word))
                context.startActivity(intent)
            }
        }
    }

}