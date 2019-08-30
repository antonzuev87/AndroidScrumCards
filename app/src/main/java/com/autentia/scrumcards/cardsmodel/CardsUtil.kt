package com.autentia.scrumcards.cardsmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 */
object CardsUtil {

    /**
     * An array of sample (dummy) items.
     */
    val fibonacciItems: ArrayList<CardItem> = arrayListOf(CardItem("1", imageName = "0", bottomText = "zeroCardText"),
                                        CardItem("2", imageName = "0,5"),
                                        CardItem("3", imageName = "1"),
                                        CardItem("4", imageName = "2"),
                                        CardItem("5", imageName = "3"),
                                        CardItem("6", imageName = "5"),
                                        CardItem("7", imageName = "8"),
                                        CardItem("8", imageName = "13"),
                                        CardItem("9", imageName = "20"),
                                        CardItem("10", imageName = "40"),
                                        CardItem("11", imageName = "100"),
                                        CardItem("12", imageName = "∞", bottomText = "infinityCardText"))

    val TShirtSizesItems: ArrayList<CardItem> = arrayListOf(CardItem("1", imageName = "XXS"),
                                                CardItem("2", imageName = "XS"),
                                                CardItem("3", imageName = "S"),
                                                CardItem("4", imageName = "M"),
                                                CardItem("5", imageName = "L"),
                                                CardItem("6", imageName = "XL"),
                                                CardItem("7", imageName = "XXL"),
                                                CardItem("8", imageName = "∞", bottomText = "infinityCardText"))

    val continueButtonItem = CardItem("1", imageName = "card_too_much", bottomText = "continueCardText")
    val restButtonItem = CardItem("1", imageName = "card_rest", bottomText = "restCardText")

    fun getCardImageIdentifier(imageName: String): String {
        return when (imageName) {
            "0,5" -> "card_half"
            "∞" -> "ic_infinitycard"
            "card_rest" -> "ic_restcard"
            "card_too_much" -> "ic_too_big"
            else -> "card_${imageName.toLowerCase()}"
        }
    }

    fun getNextCardItem(cardItem: CardItem?, cardList: ArrayList<CardItem>?): CardItem? {
        var indexOfCardItem = cardList?.indexOf(cardItem)
        return if (indexOfCardItem!=-1 && indexOfCardItem!=null && cardList!=null
            && indexOfCardItem + 1 < cardList.size) {
            indexOfCardItem += 1
            cardList[indexOfCardItem]
        } else null
    }

    fun getPreviousCardItem(cardItem: CardItem?, cardList: ArrayList<CardItem>?): CardItem? {
        var indexOfCardItem = cardList?.indexOf(cardItem)
        return if (indexOfCardItem!=-1 && indexOfCardItem!= null && cardList!=null
            && indexOfCardItem > 0) {
            indexOfCardItem -= 1
            cardList[indexOfCardItem]
        } else null
    }


    fun getNextCardItemImageName(cardItem: CardItem?, cardList: ArrayList<CardItem>?): String? {
        var nextCardItem = getNextCardItem(cardItem,cardList)
        return if(nextCardItem != null) {
            getCardImageIdentifier(nextCardItem.imageName)
        } else null
    }

    fun getPreviousCardItemImageName(cardItem: CardItem?, cardList: ArrayList<CardItem>?): String? {
        var nextCardItem = getPreviousCardItem(cardItem,cardList)
        return if(nextCardItem != null) {
            getCardImageIdentifier(nextCardItem.imageName)
        } else null
    }

    /**
     *  Model class
     */
    @Parcelize data class CardItem(val id: String, val bottomText: String? = null, val imageName: String): Parcelable

}
