package com.hugobezucha.qa.mydemoapp.pages

import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

// Shared waiting/finding helpers. Explicit WebDriverWait everywhere, no
// Thread.sleep, so tests don't flake under load or waste time waiting.
abstract class BasePage(protected val driver: AndroidDriver) {

    private val wait = WebDriverWait(driver, Duration.ofSeconds(15))

    protected fun byId(resourceId: String): By =
        AppiumBy.id("com.saucelabs.mydemoapp.android:id/$resourceId")

    protected fun waitVisible(locator: By): WebElement =
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator))

    protected fun waitClickable(locator: By): WebElement =
        wait.until(ExpectedConditions.elementToBeClickable(locator))

    protected fun isVisible(locator: By): Boolean =
        try {
            waitVisible(locator).isDisplayed
        } catch (_: org.openqa.selenium.TimeoutException) {
            false
        }
}
