package com.hugobezucha.qa.mydemoapp.base

import com.hugobezucha.qa.mydemoapp.driver.DriverFactory
import com.hugobezucha.qa.mydemoapp.pages.ProductCatalogPage
import io.appium.java_client.android.AndroidDriver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

// Fresh driver session per test (fresh app install too), so state from one
// test, like a leftover cart item, never carries over to the next.
abstract class AppiumTestBase {

    protected lateinit var driver: AndroidDriver
    protected lateinit var catalogPage: ProductCatalogPage

    @BeforeEach
    fun setUp() {
        driver = DriverFactory.createDriver()
        catalogPage = ProductCatalogPage(driver)
    }

    @AfterEach
    fun tearDown() {
        if (this::driver.isInitialized) {
            // If the app already crashed during the test, quit() can throw
            // too. Don't let that hide the test's own failure message.
            try {
                driver.quit()
            } catch (e: Exception) {
                println("driver.quit() failed during teardown: ${e.message}")
            }
        }
    }
}
