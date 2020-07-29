package com.yanlinoo.myantrip.persistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yanlinoo.myantrip.model.PostItem
import com.yanlinoo.myantrip.model.WordItem

@Dao
interface CommunicationDao {

    //Communication
    @Query("SELECT * FROM word_table")
    fun getAllWords() : LiveData<List<WordItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWords(countryList: List<WordItem>)

    @Query("DELETE FROM word_table")
    fun deleteAllWords()

    @Query("SELECT * FROM word_table WHERE category = :category")
    fun getWords(category:String):LiveData<List<WordItem>>

    @Query("SELECT * FROM word_table WHERE eng = :string || myn = :string")
    fun getSearchingWords(string:String):LiveData<List<WordItem>>



}