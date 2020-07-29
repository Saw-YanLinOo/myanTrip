package com.yanlinoo.myantrip.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.yanlinoo.myantrip.Application
import com.yanlinoo.myantrip.api.Api
import com.yanlinoo.myantrip.api.ServiceGenerator
import com.yanlinoo.myantrip.model.PostItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlogRepository {
    val TAG = BlogRepository::class.java.simpleName
    var postList: List<PostItem>? = null

    fun getAllPost(): LiveData<List<PostItem>> {
        return Application.database!!.BlogDao().getAllPosts()

    }

    suspend fun Blog_ApiCallAndPutInDB() {
        val restApi = ServiceGenerator.buildService(Api::class.java)
        restApi.getAllPosts().enqueue(object : Callback<List<PostItem>> {

            override fun onFailure(call: Call<List<PostItem>>, t: Throwable) {
                Log.e(TAG,"OOPS!! something went wrong..")
            }

            override  fun onResponse(call: Call<List<PostItem>>, response: Response<List<PostItem>>) {

                Log.e(TAG,response!!.body().toString())
                when(response.code())
                {
                    200 ->{
                        Thread(Runnable {
                            //Application.database!!.wordDao().deleteAllCountries()
                            Application.database!!.BlogDao().insertAllPosts(response.body()!!)
                            Log.e(TAG, Application.database!!.BlogDao().getAllPosts().toString())
                        }).start()
                    }
                }
            }
        })
    }
}