package com.autentia.scrumcards.CardsModel

import java.util.ArrayList

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object CardsContent {

    /**
     * An array of sample (dummy) items.
     */
    val fibonacciItems: ArrayList<CardItem> = arrayListOf(CardItem("1", imageName = "0"),
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
                                        CardItem("12", imageName = "∞"))

    val TShirtSizesItems: ArrayList<CardItem> = arrayListOf(CardItem("1", imageName = "XXS"),
                                                CardItem("2", imageName = "XS"),
                                                CardItem("3", imageName = "S"),
                                                CardItem("4", imageName = "M"),
                                                CardItem("5", imageName = "L"),
                                                CardItem("6", imageName = "XL"),
                                                CardItem("7", imageName = "XXL"),
                                                CardItem("8", imageName = "∞"))

    /**
     * A dummy item representing a piece of content.
     */
    data class CardItem(val id: String, val bottomText: String = "", val imageName: String) {
        override fun toString(): String = bottomText
    }
}
