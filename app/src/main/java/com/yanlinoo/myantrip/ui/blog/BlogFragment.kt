package com.yanlinoo.myantrip.ui.blog

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yanlinoo.myantrip.R
import com.yanlinoo.myantrip.adapter.BlogAdapter
import com.yanlinoo.myantrip.adapter.ProfileAdapter
import com.yanlinoo.myantrip.model.PostItem
import kotlinx.android.synthetic.main.fragment_porfle.*

class BlogFragment : Fragment() {

    private lateinit var blogViewModel: BlogViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        blogViewModel =
                ViewModelProviders.of(this).get(BlogViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_blog, container, false)

        if(isNetworkConnected(requireContext()))
        {
            blogViewModel.getPostsFromAPIAndStore()
        }
        else
        {
            Toast.makeText(requireActivity(),"No internet found", Toast.LENGTH_LONG).show()
        }
            blogViewModel.getAllPostsList().observe(viewLifecycleOwner,Observer<List<PostItem>>{postList->
                setUpWordRecyclerView(postList)
            })
        return root
    }

    fun setUpWordRecyclerView(postList:List<PostItem>) {
       Log.e(BlogFragment::class.java.simpleName,postList.toString())
        val blogRecyclerViewAdapter = BlogAdapter(requireContext(),postList)
        blog_recyclerView.adapter = blogRecyclerViewAdapter
        blog_recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}