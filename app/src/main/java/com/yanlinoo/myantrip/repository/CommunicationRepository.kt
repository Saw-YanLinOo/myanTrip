package com.yanlinoo.myantrip.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.yanlinoo.myantrip.Application
import com.yanlinoo.myantrip.api.Api
import com.yanlinoo.myantrip.api.ServiceGenerator
import com.yanlinoo.myantrip.model.WordItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunicationRepository {
    val TAG = CommunicationRepository::class.java.simpleName
    var wordLists: List<WordItem>? = null

    fun getWords(): LiveData<List<WordItem>> {
        return Application.database!!.WordDao().getAllWords()

    }

    fun getCategoryWords(category: String):LiveData<List<WordItem>>{
        return Application.database!!.WordDao().getWords(category)
    }

    fun getSearchingWord(string: String):LiveData<List<WordItem>>{
        return Application.database!!.WordDao().getSearchingWords(string)
    }

    suspend fun Commu_ApiCallAndPutInDB() {
        val restApi = ServiceGenerator.buildService(Api::class.java)
        restApi.getAllWords().enqueue(object : Callback<List<WordItem>>{

            override fun onFailure(call: Call<List<WordItem>>, t: Throwable) {
                Log.e(TAG,"OOPS!! something went wrong..")
            }

            override  fun onResponse(call: Call<List<WordItem>>, response: Response<List<WordItem>>) {

                Log.e(TAG,response!!.body().toString())
                when(response.code())
                {
                    200 ->{
                        Thread(Runnable {
                            //Application.database!!.wordDao().deleteAllCountries()
                            Application.database!!.WordDao().insertAllWords(response.body()!!)
                            Log.e(TAG,Application.database!!.WordDao().getAllWords().toString())
                        }).start()
                    }
                }
            }
        })
    }
}