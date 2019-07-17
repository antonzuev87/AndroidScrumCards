package com.autentia.scrumcards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CardViewFragmentViewModel : ViewModel() {
    // Create a LiveData with a String
    val imageName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val bottomText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}
