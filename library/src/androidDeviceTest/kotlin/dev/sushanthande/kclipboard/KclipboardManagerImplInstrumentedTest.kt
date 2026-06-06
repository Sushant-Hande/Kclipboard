package dev.sushanthande.kclipboard

import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.NativeClipboard
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Instrumented test that instantiates the real `KclipboardManagerImpl` with a
 * test `Clipboard` implementation and verifies copy/getCopiedText behavior.
 *
 * This test compiles as part of `androidDeviceTest` and runs on an Android device/emulator.
 */
class KclipboardManagerImplInstrumentedTest {

    private class FakeClipboard : Clipboard {
        private var stored: ClipEntry? = null

        override suspend fun setClipEntry(clipEntry: ClipEntry?) {
            stored = clipEntry
        }

        override suspend fun getClipEntry(): ClipEntry? = stored

        override val nativeClipboard: NativeClipboard
            get() = TODO("Not used in tests")
    }

    @Test
    fun kclipboardManagerImpl_reads_and_writes_using_clipboard() {
        val fake = FakeClipboard()

        // instantiate the implementation under test and exercise suspend APIs
        val manager = KclipboardManagerImpl(fake)

        runBlocking {
            val text = "instrumented test string"
            manager.copy(text)
            val read = manager.getCopiedText()
            assertEquals(text, read)
        }
    }
}

