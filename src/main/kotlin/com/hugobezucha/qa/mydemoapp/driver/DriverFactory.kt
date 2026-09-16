package com.hugobezucha.qa.mydemoapp.driver

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import java.io.File
import java.net.URL

object DriverFactory {

    private const val APP_PACKAGE = "com.saucelabs.mydemoapp.android"
    private const val APP_ACTIVITY = "com.saucelabs.mydemoapp.android.view.activities.SplashActivity"
    private const val DEVICE_NAME = "emulator-5554"

    // SplashActivity hands off to MainActivity right away, so wait on both
    private const val APP_WAIT_ACTIVITY = "$APP_ACTIVITY,com.saucelabs.mydemoapp.android.view.activities.MainActivity"

    fun createDriver(): AndroidDriver {
        val appiumServerUrl = System.getProperty("appium.serverUrl", "http://127.0.0.1:4723")
        val appPath = resolveAppPath()

        val options = UiAutomator2Options()
            .setDeviceName(DEVICE_NAME)
            // deviceName alone doesn't pin the session to a specific device;
            // udid does. Matters as soon as more than one is connected.
            .setUdid(DEVICE_NAME)
            .setApp(appPath.absolutePath)
            .setAppPackage(APP_PACKAGE)
            .setAppWaitActivity(APP_WAIT_ACTIVITY)
            .setAutoGrantPermissions(true)
            .setNewCommandTimeout(java.time.Duration.ofSeconds(120))

        return AndroidDriver(URL(appiumServerUrl), options)
    }

    private fun resolveAppPath(): File {
        val configuredPath = System.getProperty("app.path", "apks/mda-2.2.0-25.apk")
        val appFile = File(configuredPath)
        require(appFile.isFile()) {
            "APK not found at ${appFile.absolutePath}. Download it first (see README 'Prerequisites'), " +
                "or pass -Dapp.path=/full/path/to/app.apk"
        }
        return appFile
    }
}
