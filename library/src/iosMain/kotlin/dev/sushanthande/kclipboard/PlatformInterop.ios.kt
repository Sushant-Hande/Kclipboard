package dev.sushanthande.kclipboard

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry

@OptIn(ExperimentalComposeUiApi::class)
actual fun String.toClipEntry(): ClipEntry = ClipEntry.withPlainText(this)

@OptIn(ExperimentalComposeUiApi::class)
actual fun ClipEntry.toText(): String? {
    return if (hasPlainText()) getPlainText() else null
}