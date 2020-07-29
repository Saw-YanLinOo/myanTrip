package com.yanlinoo.myantrip.ui.communication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanlinoo.myantrip.model.WordItem
import com.yanlinoo.myantrip.repository.CommunicationRepository
import kotlinx.coroutines.launch

class CommunicationViewModel : ViewModel() {

    lateinit var communicationRepository: CommunicationRepository

    init {
        communicationRepository = CommunicationRepository()
    }

    fun getAllWordsList(): LiveData<List<WordItem>>
    {
        return communicationRepository.getWords()
    }

    fun getWords(category:String):LiveData<List<WordItem>>
    {
        return communicationRepository.getCategoryWords(category)
    }

    fun getWordsFromAPIAndStore()
    {
        viewModelScope.launch{
            communicationRepository.Commu_ApiCallAndPutInDB()
        }
    }
}