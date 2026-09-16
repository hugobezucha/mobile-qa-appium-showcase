package com.hugobezucha.qa.mydemoapp.tests

import com.hugobezucha.qa.mydemoapp.base.AppiumTestBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

// Scenario 6, a real bug: adding "Sauce Labs Bolt T-Shirt" always sets
// quantity 10. Opening it directly from the default catalog view crashes
// the app instead (see TC7 in the README), so this sorts by price first,
// which puts it at a safe position (3rd cheapest).
class QuantityBugTests : AppiumTestBase() {

    @Test
    @DisplayName("Adding Sauce Labs Bolt T-Shirt to the cart sets quantity to 10, not 1")
    fun addingBoltTShirtAppliesSeededQuantityBug() {
        catalogPage.sortByPriceAscending()

        val detailPage = catalogPage.openThirdProduct()
        assertEquals("Sauce Labs Bolt T-Shirt", detailPage.productTitle())
        assertEquals("1", detailPage.quantityShownOnStepper(), "Stepper should still show 1 before tapping Add to cart")

        val catalogAfterAdd = detailPage.addToCart()

        assertEquals("10", catalogAfterAdd.cartBadgeCount())
    }
}
