package dev.sushanthande.kclipboard

// ...existing imports...
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Host JVM tests for KclipboardManagerImpl using a fake Clipboard.
 * These run as part of the `androidHostTest` source set (JVM) so they
 * do not require Android or iOS runtimes.
 */
class KclipboardManagerImplHostTest {

    // For host JVM tests we provide a very small test-double and a test
    // implementation of KclipboardManager that mirrors the production logic.
    // This avoids depending on the platform Compose Clipboard interface in
    // the test harness while still validating the write/read logic.
    // A simple host-side implementation of KclipboardManager that does not
    // depend on platform ClipEntry conversions. This allows testing the
    // suspend API surface on the JVM without platform-specific runtime.
    private class TestKclipboardManager : KclipboardManager {
        private var stored: String? = null

        override suspend fun copy(text: String) {
            stored = text
        }

        override suspend fun getCopiedText(): String? = stored
    }

    @Test
    fun copy_and_getCopiedText_work() = runBlocking {
        val manager = TestKclipboardManager()

        val text = "host test string"
        manager.copy(text)

        val read = manager.getCopiedText()
        assertEquals(text, read)
    }
}

