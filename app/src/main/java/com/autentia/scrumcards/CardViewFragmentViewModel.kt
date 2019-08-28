package com.autentia.scrumcards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autentia.scrumcards.cardsmodel.CardsUtil

class CardViewFragmentViewModel : ViewModel() {
    // Create a LiveData with a String
    val itemList: MutableLiveData<ArrayList<CardsUtil.CardItem>> by lazy {
        MutableLiveData<ArrayList<CardsUtil.CardItem>> ()
    }

    val cardItem: MutableLiveData<CardsUtil.CardItem> by lazy {
        MutableLiveData<CardsUtil.CardItem>()
    }
}
