package com.yanlinoo.myantrip.persistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yanlinoo.myantrip.model.PostItem
import com.yanlinoo.myantrip.model.WordItem

@Dao
interface BlogDao {
    //Blog
    @Query("SELECT * FROM post_table")
    fun getAllPosts() : LiveData<List<PostItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPosts(postList: List<PostItem>)


}