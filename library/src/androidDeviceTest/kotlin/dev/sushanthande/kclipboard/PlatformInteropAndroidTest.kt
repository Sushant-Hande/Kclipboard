package dev.sushanthande.kclipboard

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

/**
 * Android instrumentation test to validate the Android `actual` implementations
 * of the interop functions. This test is intended to run on an Android device
 * or emulator (androidDeviceTest).
 */
@RunWith(AndroidJUnit4::class)
class PlatformInteropAndroidTest {
    @Test
    fun androidStringToClipEntry_roundtrip() {
        val original = "Hello from androidDeviceTest"
        val entry = original.toClipEntry()
        val text = entry.toText()
        assertEquals(original, text)
    }
}

