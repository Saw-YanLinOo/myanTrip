package com.yanlinoo.myantrip.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class PostItem(

    @PrimaryKey
    val post_id: Int,
    val username: String,
    val profile: String,
    val description: String,
    val location: String,
    val post_image_1: String,
    val post_image_2: String,
    val post_image_3: String,
    val post_image_4: String,
    val post_image_5: String,
    val post_like: String,
    val post_view:String,
    val created_at: String,
    val updated_at: String

)