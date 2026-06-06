package dev.sushanthande.kclipboard

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * iOS platform tests for the interop helpers. These run as part of the
 * iOS test target and exercise the iosMain `actual` implementations.
 */
class PlatformInteropIosTest {
    @Test
    fun iosStringToClipEntry_roundtrip() {
        val original = "Hello from iosTest"
        val entry = original.toClipEntry()
        val roundtripped = entry.toText()
        assertEquals(original, roundtripped)
    }
}

