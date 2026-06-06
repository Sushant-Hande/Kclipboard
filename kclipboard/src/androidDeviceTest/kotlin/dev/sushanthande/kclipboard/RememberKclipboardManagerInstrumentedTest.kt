package dev.sushanthande.kclipboard

import androidx.activity.ComponentActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.NativeClipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.test.core.app.ActivityScenario
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RememberKclipboardManagerInstrumentedTest {
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
    fun rememberKclipboardManager_uses_localClipboard_when_provided() {
        val latch = CountDownLatch(1)
        var observed: String? = null

        ActivityScenario.launch(ComponentActivity::class.java).onActivity { activity ->
            val composeView = ComposeView(activity)
            activity.setContentView(composeView)

            val fake = FakeClipboard()

            composeView.setContent {
                CompositionLocalProvider(LocalClipboard provides fake) {
                    val manager = rememberKclipboardManager()
                    LaunchedEffect(Unit) {
                        manager.copy("from-composable")
                        observed = manager.getCopiedText()
                        latch.countDown()
                    }
                }
            }
        }

        assertTrue(latch.await(5, TimeUnit.SECONDS))
        assertEquals("from-composable", observed)
    }
}

