package dev.sushanthande.kclipboard

import android.content.ClipData
import androidx.compose.ui.platform.ClipEntry

actual fun String.toClipEntry(): ClipEntry = ClipEntry(ClipData.newPlainText(this, this))

actual fun ClipEntry.toText(): String? {
    val clipData = clipData
    return if (clipData.itemCount > 0) {
        clipData.getItemAt(0).text?.toString()
    } else {
        null
    }
}