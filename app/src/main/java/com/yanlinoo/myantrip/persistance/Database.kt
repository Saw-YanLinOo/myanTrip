package com.yanlinoo.myantrip.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yanlinoo.myantrip.model.WordItem
import com.yanlinoo.myantrip.model.PostItem

@Database(entities = arrayOf(WordItem::class,PostItem::class), version = 3)
abstract class Database : RoomDatabase() {
    abstract fun WordDao() : CommunicationDao
    abstract fun BlogDao() : BlogDao

}