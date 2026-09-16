package com.hugobezucha.qa.mydemoapp.pages

import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver

// Product catalog screen, plus the header (part of activity_main.xml, shows
// on every screen). productRV is a RecyclerView reusing the same resource
// ids per row, so a specific product needs a UiAutomator instance() selector.
class ProductCatalogPage(driver: AndroidDriver) : BasePage(driver) {

    companion object {
        const val CATALOG_TITLE = "Products"
    }

    private val catalogTitle = byId("productTV")
    private val cartBadgeCount = byId("cartTV")
    private val cartBadgeContainer = byId("cartCircleRL")
    private val sortButton = byId("sortIV")

    // MainActivity.java: tapping sortIV opens sort_dialog.xml, whose options
    // are these ConstraintLayout container ids.
    private val priceAscendingOption = byId("priceAscCL")

    private val firstProductImage = AppiumBy.androidUIAutomator(
        "new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productIV\").instance(0)"
    )

    // Position 2 after sorting by price ascending: Onesie ($7.99), Bike Light
    // ($9.99), then Bolt T-Shirt ($15.99). See QuantityBugTests for why.
    private val thirdProductImage = AppiumBy.androidUIAutomator(
        "new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productIV\").instance(2)"
    )

    fun isDisplayed(): Boolean = try {
        waitVisible(catalogTitle).text == CATALOG_TITLE
    } catch (_: org.openqa.selenium.TimeoutException) {
        false
    }

    fun openFirstProduct(): ProductDetailPage {
        waitClickable(firstProductImage).click()
        return ProductDetailPage(driver)
    }

    fun openThirdProduct(): ProductDetailPage {
        waitClickable(thirdProductImage).click()
        return ProductDetailPage(driver)
    }

    fun sortByPriceAscending() {
        waitClickable(sortButton).click()
        waitClickable(priceAscendingOption).click()
    }

    fun isCartBadgeVisible(): Boolean = isVisible(cartBadgeContainer)

    fun cartBadgeCount(): String = waitVisible(cartBadgeCount).text
}
