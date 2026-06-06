package dev.sushanthande.kclipboard

/**
 * Public, platform-agnostic clipboard API for consumers.
 * Implementations must not leak platform-specific types.
 */
interface KclipboardManager {
    /**
     * Copies the provided text to the system clipboard.
     * @param text Text to copy. Must not be null.
     * Note: this is a suspend function because some Compose clipboard APIs are suspend.
     */
    suspend fun copy(text: String)

    /**
     * Returns the current text from the system clipboard.
     * @return The copied text, or null if the clipboard is empty or unavailable.
     */
    suspend fun getCopiedText(): String?
}
