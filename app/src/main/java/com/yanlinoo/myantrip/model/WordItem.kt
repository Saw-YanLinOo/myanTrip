package com.yanlinoo.myantrip.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class WordItem(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val category: String,
    val eng: String,
    val myn: String,
    val eng_file: String,
    val myn_file: String
)
