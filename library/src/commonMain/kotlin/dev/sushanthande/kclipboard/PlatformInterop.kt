package dev.sushanthande.kclipboard

import androidx.compose.ui.platform.ClipEntry

expect fun String.toClipEntry(): ClipEntry

expect fun ClipEntry.toText(): String?