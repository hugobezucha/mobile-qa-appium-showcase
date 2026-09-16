package com.hugobezucha.qa.mydemoapp.pages

import io.appium.java_client.android.AndroidDriver

// Product detail screen. addToCart() has a seeded bug for "Sauce Labs Bolt
// T-Shirt" (forces quantity 10, see QuantityBugTests), so CartTests uses
// "Sauce Labs Backpack" instead to keep its happy-path test unrelated to it.
class ProductDetailPage(driver: AndroidDriver) : BasePage(driver) {

    private val productTitle = byId("productTV")
    private val addToCartButton = byId("cartBt")
    private val quantityStepper = byId("noTV")

    fun productTitle(): String = waitVisible(productTitle).text

    fun quantityShownOnStepper(): String = waitVisible(quantityStepper).text

    fun addToCart(): ProductCatalogPage {
        waitClickable(addToCartButton).click()
        return ProductCatalogPage(driver)
    }
}
