package com.yanlinoo.myantrip.ui.blog

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.yanlinoo.myantrip.R
import com.yanlinoo.myantrip.adapter.ProfileAdapter
import com.yanlinoo.myantrip.model.PostItem
import kotlinx.android.synthetic.main.context_profile.*

class ProfileActivity : AppCompatActivity() {
    private var blogViewModel: BlogViewModel = BlogViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = "Yan Lin Oo"
//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        if(isNetworkConnected(applicationContext))
        {
            blogViewModel.getPostsFromAPIAndStore()
        }
        else
        {
            Toast.makeText(applicationContext,"No internet found", Toast.LENGTH_LONG).show()
        }
        blogViewModel.getAllPostsList().observe(this,Observer<List<PostItem>>{ postList->
            setUpWordRecyclerView(postList)
        })
    }

    private fun setUpWordRecyclerView(postList: List<PostItem>) {
        profile_recyclerView.adapter = ProfileAdapter(applicationContext,postList)
        profile_recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}