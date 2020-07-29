package com.yanlinoo.myantrip.ui.communication

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yanlinoo.myantrip.R
import com.yanlinoo.myantrip.adapter.CommunicatinCategoryRecyclerViewAdapter
import com.yanlinoo.myantrip.adapter.WordRecyclerViewAdapter
import com.yanlinoo.myantrip.model.WordItem
import kotlinx.android.synthetic.main.fragment_communication.*
import androidx.lifecycle.MutableLiveData
import com.orhanobut.hawk.Hawk

class CommunicationFragment : Fragment() {

    private lateinit var communicationViewModel: CommunicationViewModel
    val languageList = listOf("myanmar","english")
    val language = MutableLiveData<String>()
    private lateinit var imageAnimation: AnimationDrawable

    init {
        language.value = Hawk.get("language","english")


    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        communicationViewModel =
                ViewModelProviders.of(this).get(CommunicationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_communication, container, false)
                val imageView = root.findViewById<ImageView>(R.id.img_view_animation)
            imageView.apply {
                setBackgroundResource(R.drawable.speak_animation_list)
                imageAnimation = background as AnimationDrawable

        }
        imageAnimation.start()

                if(isNetworkConnected(requireContext()))
                {
                    communicationViewModel.getWordsFromAPIAndStore()
                }
                else
                {
                    Toast.makeText(requireActivity(),"No internet found", Toast.LENGTH_LONG).show()
                }
            communicationViewModel.getAllWordsList().observe(viewLifecycleOwner,Observer<List<WordItem>>{wordList->
                Log.e(CommunicationFragment::class.java.simpleName,wordList.toString())
                //setUpCategoryRecyclerView(wordList)
                setUpWordRecyclerView(wordList)
            })
                val spinner:AppCompatSpinner = root.findViewById(R.id.spinner_language)

                val adapter = context?.let { ArrayAdapter(it,android.R.layout.simple_dropdown_item_1line,languageList) }
                spinner.adapter = adapter
                spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        language.value = Hawk.get("language","english")
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                        language.value = languageList[p2]
                        Hawk.put("language",languageList[p2])
                        (wordRecyclerView.adapter as WordRecyclerViewAdapter).notifyDataSetChanged()

                    }
                }

        return root
    }

    private fun setUpCategoryRecyclerView(menuList: List<WordItem>) {
        val communicatinCategoryRecyclerViewAdapter = CommunicatinCategoryRecyclerViewAdapter(requireContext(),menuList)
        categoryRecyclerView.adapter = communicatinCategoryRecyclerViewAdapter
        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        categoryRecyclerView.setHasFixedSize(true)
        communicatinCategoryRecyclerViewAdapter.buttonClick= { wordItem ->
            communicationViewModel.getWords(wordItem.category)
                .observe(viewLifecycleOwner, Observer<List<WordItem>> { wordList ->
                    Log.e(CommunicationFragment::class.java.simpleName, wordList.toString())
                    wordRecyclerView.adapter =
                        WordRecyclerViewAdapter(requireContext(), wordList as MutableList<WordItem>)
                })
        }
    }
    fun setUpWordRecyclerView(words : List<WordItem>)
    {
        val wordRecyclerViewAdapter = WordRecyclerViewAdapter(requireContext(), words as MutableList<WordItem>)
        wordRecyclerView.adapter = wordRecyclerViewAdapter
        wordRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        wordRecyclerView.addItemDecoration (DividerItemDecoration (context, (wordRecyclerView.layoutManager as LinearLayoutManager) .orientation))
        wordRecyclerView.setHasFixedSize(true)
        wordRecyclerViewAdapter.onClickListener={
                Toast.makeText(context,"Speakkkkkk",Toast.LENGTH_SHORT).show()
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}