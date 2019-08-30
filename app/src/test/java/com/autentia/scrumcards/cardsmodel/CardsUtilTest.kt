package com.autentia.scrumcards.cardsmodel

import org.junit.Test
import org.junit.Assert.*

class CardsUtilTest {

    @Test
    fun getFibonacciItems() {
        assertNotNull(CardsUtil.fibonacciItems)
        assert(CardsUtil.fibonacciItems is ArrayList<CardsUtil.CardItem>)
    }

    @Test
    fun getTShirtSizesItems() {
        assertNotNull("Must not be null!", CardsUtil.TShirtSizesItems)
        assert(CardsUtil.TShirtSizesItems is ArrayList<CardsUtil.CardItem>)
    }

    @Test
    fun getContinueButtonItem() {
        assertNotNull("Must not be null", CardsUtil.continueButtonItem)
        assert(CardsUtil.continueButtonItem is CardsUtil.CardItem)
    }

    @Test
    fun getRestButtonItem() {
        assertNotNull("Must not be null", CardsUtil.restButtonItem)
        assert(CardsUtil.restButtonItem is CardsUtil.CardItem)
    }

    @Test
    fun getCardImageIdentifier() {

    }

    @Test
    fun getNextCardItem() {
    }

    @Test
    fun getPreviousCardItem() {
    }

    @Test
    fun getNextCardItemImageName() {
    }

    @Test
    fun getPreviousCardItemImageName() {
    }
}