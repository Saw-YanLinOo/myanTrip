package com.yanlinoo.myantrip

import android.app.Application
import androidx.room.Room
import com.orhanobut.hawk.Hawk
import com.yanlinoo.myantrip.persistance.Database

class Application : Application() {
    companion object {
        var database: Database? = null
    }

    override fun onCreate() {
        super.onCreate()

        Hawk.init(applicationContext).build();

        database =  Room.databaseBuilder(applicationContext, Database::class.java, "languages")
            .fallbackToDestructiveMigration()
            .build()
    }

}