package com.hugobezucha.qa.mydemoapp.tests

import com.hugobezucha.qa.mydemoapp.base.AppiumTestBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

// Scenario 4: adding a product updates the cart badge (visibility + count).
class CartTests : AppiumTestBase() {

    @Test
    @DisplayName("Adding a product to the cart shows a badge with count 1")
    fun addingProductToCartUpdatesHeaderBadge() {
        assertTrue(catalogPage.isDisplayed())
        assertTrue(!catalogPage.isCartBadgeVisible(), "Cart badge should be hidden before anything is added")

        val detailPage = catalogPage.openFirstProduct()
        // first alphabetically, and not the product with the quantity bug
        assertEquals("Sauce Labs Backpack", detailPage.productTitle())

        val catalogAfterAdd = detailPage.addToCart()

        assertTrue(catalogAfterAdd.isCartBadgeVisible(), "Cart badge should appear once an item is added")
        assertEquals("1", catalogAfterAdd.cartBadgeCount())
    }
}
