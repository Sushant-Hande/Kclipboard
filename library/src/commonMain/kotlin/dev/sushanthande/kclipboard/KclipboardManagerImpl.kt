package dev.sushanthande.kclipboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard


/**
 * A small [KclipboardManager] implementation backed by Jetpack Compose's
 * [Clipboard] obtained from the composition's [LocalClipboard].
 *
 * This class is private and intended to be created via [rememberKclipboardManager]
 * so that the Compose `Clipboard` instance is captured and cached for the
 * composition lifecycle. It delegates copy operations to the underlying
 * Compose [Clipboard].
 *
 * Note: clipboard contents may be sensitive. This implementation forwards the
 * provided text to the system clipboard but intentionally does not log or
 * persist clipboard data.
 *
 * @property clipboard The Compose [Clipboard] used to perform clipboard writes.
 */
internal class KclipboardManagerImpl(
    private val clipboard: Clipboard
) : KclipboardManager {
    /**
     * Copies [text] to the system clipboard using the Compose [Clipboard].
     *
     * This function is suspendable to allow callers to perform the operation
     * from coroutine contexts if needed; the implementation delegates the
     * write to [Clipboard.setClipEntry]. The operation is lightweight and
     * non-blocking.
     *
     * @param text Text to copy to the clipboard. Must not be logged or
     * persisted by callers or this implementation.
     */
    override suspend fun copy(text: String) {
        clipboard.setClipEntry(text.toClipEntry())
    }

    /**
     * Reads the current clipboard entry from the underlying Compose [Clipboard].
     *
     * @return The clipboard contents as a string representation of the current
     * clipboard entry.
     */
    override suspend fun getCopiedText() : String? {
        return clipboard.getClipEntry()?.toText()
    }
}

/**
 * Returns a remembered [KclipboardManager] bound to the current composition's `LocalClipboard`.
 *
 * This is the recommended place to initialize composable-backed properties: inside a
 * @Composable factory (often named `remember...`) so the instance is created and
 * cached for the composition lifecycle and doesn't leak composition-local objects.
 */
@Composable
fun rememberKclipboardManager(): KclipboardManager {
    val clipboard = LocalClipboard.current
    return remember { KclipboardManagerImpl(clipboard) }
}
